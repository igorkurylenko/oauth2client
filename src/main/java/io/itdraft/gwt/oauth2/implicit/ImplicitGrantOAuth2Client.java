package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static io.itdraft.gwt.oauth2.implicit.Utils.isEmpty;

public class ImplicitGrantOAuth2Client extends OAuth2Client {
    private static Map<String, InstanceTuple> INSTANCES = new HashMap<>();
    protected AuthorizationResponseFactory responseFactory =
            new DefaultAuthorizationResponseFactory();
    private final OAuth2ClientConfig config;
    private LinkedList<AccessTokenCommand> commandQueue = new LinkedList<>();

    public static OAuth2Client create(String clientId,
                                      String redirectUri,
                                      String authorizationEndpointUrl,
                                      Set<String> scopes) {
        return create(clientId, redirectUri, authorizationEndpointUrl, scopes,
                OAuth2ClientConfig.DEFAULT_JS_CALLBACK_FN_NAME);
    }

    public static OAuth2Client create(String clientId,
                                      String redirectUri,
                                      String authorizationEndpointUrl,
                                      Set<String> scopes,
                                      String jsCallbackFunctionName) {
        if (isEmpty(authorizationEndpointUrl)) {
            throw new IllegalArgumentException(
                    "authorizationEndpointUrl must be not null or empty"
            );
        }

        if (isEmpty(clientId)) {
            throw new IllegalArgumentException("clientId must be not null or empty");
        }

        return create(new OAuth2ClientConfig(clientId, redirectUri,
                authorizationEndpointUrl, scopes, jsCallbackFunctionName));
    }

    public static OAuth2Client create(OAuth2ClientConfig config) {
        cleanUpPreviousIfExists(config);

        ImplicitGrantOAuth2Client originalInstance = new ImplicitGrantOAuth2Client(config);
        OAuth2Client decoratedInstance = new WithAutoRefresh(
                new WithStorage(originalInstance));

        INSTANCES.put(config.getClientId(),
                new InstanceTuple(decoratedInstance, originalInstance));

        return decoratedInstance;
    }

    public static void cleanUpPreviousIfExists(OAuth2ClientConfig config) {
        InstanceTuple tuple = INSTANCES.remove(config.getClientId());

        if (tuple != null) {
            tuple.originalInstance.unbindJsCallbackFunction();
        }
    }

    public static OAuth2Client get(String clientId) {
        InstanceTuple instanceTuple = INSTANCES.get(clientId);

        return instanceTuple == null ? null : instanceTuple.decoratedInstance;
    }

    private ImplicitGrantOAuth2Client(OAuth2ClientConfig config) {
        this.config = config;
        bindJsCallbackFunction(config.getJsCallbackFunctionName());
    }

    public void unbindJsCallbackFunction() {
        unbindJsCallbackFunction(config.getJsCallbackFunctionName());
        commandQueue.clear();
    }

    private native void unbindJsCallbackFunction(String jsCallbackFunctionName) /*-{
        $wnd.OAuth2Client = $wnd.OAuth2Client || {};

        delete $wnd.OAuth2Client[jsCallbackFunctionName];
    }-*/;

    public void bindJsCallbackFunction() {
        bindJsCallbackFunction(config.getJsCallbackFunctionName());
    }

    private native void bindJsCallbackFunction(String jsCallbackFunctionName) /*-{
        $wnd.OAuth2Client = $wnd.OAuth2Client || {};

        var thiz = this;

        $wnd.OAuth2Client[jsCallbackFunctionName] = $entry(function (hash) {
            thiz.@io.itdraft.gwt.oauth2.implicit.ImplicitGrantOAuth2Client::oAuth2Callback(Ljava/lang/String;)(hash);
        });
    }-*/;

    public OAuth2ClientConfig getConfig() {
        return config;
    }

    protected void doRequestAccessToken(final AccessTokenCallback callback) {
        RetrieveAccessTokenCommand command = new RetrieveAccessTokenCommand(
                config, new RetrieveAccessTokenCallbackWrapper(callback));

        enqueueRetrieveCommand(command);
    }

    public void refreshAccessToken(final AccessTokenCallback callback) {
        RefreshAccessTokenCommand command = new RefreshAccessTokenCommand(
                config, new RefreshAccessTokenCallbackWrapper(callback));

        enqueueRefreshCommand(command);
    }

    private void enqueueRetrieveCommand(RetrieveAccessTokenCommand command) {
        if (isThereRetrieveCommandInQueue()) {
            commandQueue.addLast(command);
            ensurePopupWindowFocus();

        } else {
            commandQueue.add(command);
            command.execute();
        }
    }

    private void ensurePopupWindowFocus() {
        // first RetrieveAccessTokenCommand is initialized process
        // of getting the access token through a popup window
        for (AccessTokenCommand next : commandQueue) {
            if (next instanceof RetrieveAccessTokenCommand) {
                ((RetrieveAccessTokenCommand) next).focusPopupWindow();
                return;
            }
        }
    }

    private void enqueueRefreshCommand(RefreshAccessTokenCommand command) {
        if (!commandQueue.isEmpty()) {
            commandQueue.addLast(command);

        } else {
            commandQueue.add(command);
            command.execute();
        }
    }

    private boolean isThereRetrieveCommandInQueue() {
        for (AccessTokenCommand command : commandQueue) {
            if (command instanceof RetrieveAccessTokenCommand) {
                return true;
            }
        }

        return false;
    }

    private void oAuth2Callback(String uriFragment) {
        if (commandQueue.isEmpty()) {
            return;
        }

        OAuth2ProviderResponse response = responseFactory.create(uriFragment);
        String state = response.getState();

        if (isEmpty(state)) {
            onFailureOnAllQueue(FailureReason.RESPONSE_WITHOUT_STATE_PARAMETER);
            return;
        }

        AccessTokenCommand command = getCommandByStateParam(state);

        if (command == null) {
            onFailureOnAllQueue(FailureReason.STATE_PARAMETER_MISMATCH);
            return;
        }

        command.processOAuth2ProviderResponse(response);
    }

    private AccessTokenCommand getCommandByStateParam(String state) {
        AccessTokenCommand result = null;
        for (AccessTokenCommand command : commandQueue) {
            if (state.equals(command.getState())) {
                result = command;
            }
        }

        return result;
    }

    private abstract class AccessTokenCallbackWrapper extends AccessTokenCallback {
        final AccessTokenCallback originalCallback;

        protected AccessTokenCallbackWrapper(AccessTokenCallback originalCallback) {
            this.originalCallback = originalCallback;
        }
    }

    private class RetrieveAccessTokenCallbackWrapper extends AccessTokenCallbackWrapper {

        private RetrieveAccessTokenCallbackWrapper(AccessTokenCallback originalCallback) {
            super(originalCallback);
        }

        protected void doOnFailure(final FailureReason reason) {
            onFailureOnAllQueue(reason);
        }

        protected void doOnSuccess(final AccessToken token) {
            onSuccessOnAllQueue(token);
        }
    }

    private class RefreshAccessTokenCallbackWrapper extends AccessTokenCallbackWrapper {
        private RefreshAccessTokenCallbackWrapper(AccessTokenCallback originalCallback) {
            super(originalCallback);
        }

        protected void doOnFailure(final FailureReason reason) {
            if (!isThereRetrieveCommandInQueue()) {
                onFailureOnAllQueue(reason);
            }
        }

        protected void doOnSuccess(AccessToken token) {
            if (!isThereRetrieveCommandInQueue()) {
                onSuccessOnAllQueue(token);
            }
        }
    }

    private void onSuccessOnAllQueue(AccessToken token) {
        while (!commandQueue.isEmpty()) {
            AccessTokenCommand command = commandQueue.pollFirst();
            AccessTokenCallbackWrapper callbackWrapper =
                    (AccessTokenCallbackWrapper) command.getAccessTokenCallback();
            callbackWrapper.originalCallback.deferredOnSuccess(token);
        }
    }

    private void onFailureOnAllQueue(FailureReason reason) {
        while (!commandQueue.isEmpty()) {
            AccessTokenCommand command = commandQueue.pollFirst();
            AccessTokenCallbackWrapper callbackWrapper =
                    (AccessTokenCallbackWrapper) command.getAccessTokenCallback();
            callbackWrapper.originalCallback.deferredOnFailure(reason);
        }
    }

    static class InstanceTuple {
        final OAuth2Client decoratedInstance;
        final ImplicitGrantOAuth2Client originalInstance;

        InstanceTuple(OAuth2Client decoratedInstance, ImplicitGrantOAuth2Client originalInstance) {
            this.decoratedInstance = decoratedInstance;
            this.originalInstance = originalInstance;
        }
    }
}
