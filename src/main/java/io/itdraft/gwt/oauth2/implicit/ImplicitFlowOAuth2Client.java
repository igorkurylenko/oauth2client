package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.OAuth2Client;
import io.itdraft.gwt.oauth2.AuthorizationRequest;
import io.itdraft.gwt.oauth2.OAuth2RequestCallback;

import java.util.HashMap;
import java.util.Map;

public class ImplicitFlowOAuth2Client implements OAuth2Client {

    private static Map<String, ImplicitFlowOAuth2Client> INSTANCES = new HashMap<>();
    private static final String JS_CALLBACK_FUNCTION_NAME = "oauth2callback";
    private FlowExecutor flowExecutor;
    private OAuth2RequestCallback callback;

    private ImplicitFlowOAuth2Client() {
    }

    public static OAuth2Client getInstance() {
        return getInstance(JS_CALLBACK_FUNCTION_NAME);
    }

    public static OAuth2Client getInstance(String jsCallbackFunctionName) {
        ImplicitFlowOAuth2Client result = INSTANCES.get(jsCallbackFunctionName);

        if (result == null) {
            result = newImplicitFlowOAuth2Client(jsCallbackFunctionName);
            INSTANCES.put(jsCallbackFunctionName, result);
        }

        return result;
    }


    private static ImplicitFlowOAuth2Client newImplicitFlowOAuth2Client(
            String jsCallbackFunctionName) {
        ImplicitFlowOAuth2Client result = new ImplicitFlowOAuth2Client();

        result.bindJsCallbackFunction(jsCallbackFunctionName);

        return result;
    }

    private native void bindJsCallbackFunction(String jsCallbackFunctionName) /*-{
        $wnd.OAuth2Client = $wnd.OAuth2Client || {};

        var thiz = this;

        $wnd.OAuth2Client[jsCallbackFunctionName] = $entry(function (hash) {
            thiz.@io.itdraft.gwt.oauth2.implicit.ImplicitFlowOAuth2Client::onRedirected(Ljava/lang/String;)(hash);
        });
    }-*/;

    @Override
    public void retrieveAccessToken(AuthorizationRequest request,
                                    OAuth2RequestCallback callback) {
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

    public ImplicitFlowOAuth2Client setFlowExecutor(FlowExecutor flowExecutor) {
        if (isInProgress(flowExecutor)) {
            throw new IllegalStateException("Flow execution is in progress");
        }

        this.flowExecutor = flowExecutor;

        return this;
    }

    private boolean isInProgress(FlowExecutor flowExecutor) {
        return flowExecutor != null && flowExecutor.isInProgress();
    }

    @Override
    public void refreshAccessToken(AuthorizationRequest request,
                                   OAuth2RequestCallback callback) {
        throw new UnsupportedOperationException(
                "Implicit grant flow does not support access token refreshing ");
    }

    private void onRedirected(String uriFragment) {
        if (callback == null) {
            throw new IllegalStateException("OAuth2 request was not sent");
        }

        flowExecutor.finish(uriFragment, callback);

        callback = null;
    }
}
