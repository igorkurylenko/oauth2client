package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.OAuth2Client;
import io.itdraft.gwt.oauth2.AccessTokenRequest;
import io.itdraft.gwt.oauth2.AccessTokenCallback;

import java.util.HashMap;
import java.util.Map;

public class ImplicitGrantOAuth2Client implements OAuth2Client {

    private static Map<String, ImplicitGrantOAuth2Client> INSTANCES = new HashMap<>();
    private static final String JS_CALLBACK_FUNCTION_NAME = "oauth2callback";
    private FlowExecutor flowExecutor;
    private AccessTokenCallback callback;

    private ImplicitGrantOAuth2Client() {
    }

    public static OAuth2Client getInstance() {
        return getInstance(JS_CALLBACK_FUNCTION_NAME);
    }

    public static OAuth2Client getInstance(String jsCallbackFunctionName) {
        ImplicitGrantOAuth2Client result = INSTANCES.get(jsCallbackFunctionName);

        if (result == null) {
            result = newImplicitFlowOAuth2Client(jsCallbackFunctionName);
            INSTANCES.put(jsCallbackFunctionName, result);
        }

        return result;
    }


    private static ImplicitGrantOAuth2Client newImplicitFlowOAuth2Client(
            String jsCallbackFunctionName) {
        ImplicitGrantOAuth2Client result = new ImplicitGrantOAuth2Client();

        result.bindJsCallbackFunction(jsCallbackFunctionName);

        return result;
    }

    private native void bindJsCallbackFunction(String jsCallbackFunctionName) /*-{
        $wnd.OAuth2Client = $wnd.OAuth2Client || {};

        var thiz = this;

        $wnd.OAuth2Client[jsCallbackFunctionName] = $entry(function (hash) {
            thiz.@io.itdraft.gwt.oauth2.implicit.ImplicitGrantOAuth2Client::callback(Ljava/lang/String;)(hash);
        });
    }-*/;

    @Override
    public void retrieveAccessToken(AccessTokenRequest request,
                                    AccessTokenCallback callback) {
        if (request == null) {
            throw new IllegalArgumentException("OAuth2 request must be not null");
        }

        if (callback == null) {
            throw new IllegalArgumentException("Auth2 request callback must be not null");
        }

        if (this.callback != null) {
            throw new IllegalStateException("Access token is being retrieved");
        }

        this.callback = callback;

        if (flowExecutor == null) {
            flowExecutor = createDefaultFlowExecutor();
        }

        flowExecutor.run(request);
    }

    public static FlowExecutor createDefaultFlowExecutor() {
        return new ThroughPopupWindowExecutor();
    }

    public ImplicitGrantOAuth2Client setFlowExecutor(FlowExecutor flowExecutor) {
        if (isInProgress(flowExecutor)) {
            throw new IllegalStateException("Flow execution is in progress");
        }

        this.flowExecutor = flowExecutor;

        return this;
    }

    private boolean isInProgress(FlowExecutor flowExecutor) {
        return flowExecutor != null && flowExecutor.isInProgress();
    }

    private void refreshAccessToken(AccessTokenRequest request,
                                   AccessTokenCallback callback) {
        throw new UnsupportedOperationException(
                "Not implemented yet.");
    }

    private void callback(String uriFragment) {
        if (callback == null) {
            throw new IllegalStateException("OAuth2 request was not sent");
        }

        flowExecutor.finish(uriFragment, callback);

        callback = null;
    }
}
