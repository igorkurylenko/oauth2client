package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2Request;
import com.igorkurilenko.gwt.oauth2.OAuth2RequestCallback;
import com.igorkurilenko.gwt.oauth2.OAuth2Response;
import com.igorkurilenko.gwt.oauth2.OAuth2Client;

import java.util.HashMap;
import java.util.Map;

public class ImplicitFlowOAuth2Client implements OAuth2Client {

    private static Map<String, ImplicitFlowOAuth2Client> INSTANCES = new HashMap<>();
    private FlowExecutor flowExecutor;
    private OAuth2RequestCallback callback;
    private String state;

    private ImplicitFlowOAuth2Client() {
    }

    public static OAuth2Client create(String jsCallbackFunctionName) {
        ensureJsUtilsExported();

        ImplicitFlowOAuth2Client result = INSTANCES.get(jsCallbackFunctionName);

        if (result == null) {
            result = newImplicitFlowOAuth2Client(jsCallbackFunctionName);
            INSTANCES.put(jsCallbackFunctionName, result);
        }

        return result;
    }

    private static void ensureJsUtilsExported() {
        // todo: export oauth2 js utils only once
    }

    private static ImplicitFlowOAuth2Client newImplicitFlowOAuth2Client(
            String jsCallbackFunctionName) {
        ImplicitFlowOAuth2Client result = new ImplicitFlowOAuth2Client();

        result.bindJsCallbackFunction(jsCallbackFunctionName);

        return result;
    }

    private native void bindJsCallbackFunction(String jsCallbackFunctionName) /*-{
        if (!$wnd.oauth2client) {
            $wnd.oauth2client = {}
        }

        var zis = this;

        $wnd.oauth2client[jsCallbackFunctionName] = $entry(function (authResponse) {
            zis.@com.igorkurilenko.gwt.oauth2.implicit.ImplicitFlowOAuth2Client::onResponse(*)(authResponse)
        });
    }-*/;

    @Override
    public void retrieveAccessToken(OAuth2Request request,
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
        this.state = request.getState();

        if (flowExecutor == null) {
            flowExecutor = createDefaultFlowExecutor();
        }

        flowExecutor.initFlow(request, callback);
    }

    private FlowExecutor createDefaultFlowExecutor() {
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
    public void refreshAccessToken(OAuth2Request request,
                                   OAuth2RequestCallback callback) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private void onResponse(OAuth2Response response) {
        if (callback == null) {
            throw new IllegalStateException("OAuth2 request was not sent");
        }

        flowExecutor.finalizeFlow(response);

        String stateFomResponse = response.getState();

        if (stateFomResponse == null) {
            callback.onFailure(new Throwable("OAuth2 response 'state' parameter was not provided"));

        } else if (stateFomResponse.equals(state)) {
            callback.onSuccess(response);

        } else {
            callback.onFailure(new Throwable("Request and response 'state' parameters mismatch"));
        }

        callback = null;
    }
}
