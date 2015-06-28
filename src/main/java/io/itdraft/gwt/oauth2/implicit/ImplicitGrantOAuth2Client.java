package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImplicitGrantOAuth2Client extends OAuth2Client {

    private static Map<OAuth2ClientConfig, OAuth2Client> INSTANCES = new HashMap<>();
    private final OAuth2ClientConfig config;
    private FlowExecutor flowExecutor;
    private AccessTokenCallback callback;

    public static OAuth2Client getInstance(String clientId, String redirectUri,
                                           String authEndpointUrl) {
        return getInstance(new OAuth2ClientConfig(clientId, redirectUri, authEndpointUrl));
    }

    public static OAuth2Client getInstance(String clientId, String redirectUri,
                                          String authEndpointUrl, String jsCallbackFunctionName) {
        return getInstance(new OAuth2ClientConfig(clientId, redirectUri,
                authEndpointUrl, jsCallbackFunctionName));
    }

    public static OAuth2Client getInstance(OAuth2ClientConfig config) {
        OAuth2Client result = INSTANCES.get(config);

        if (result == null) {
            result = new CachedOAuth2Client(new ImplicitGrantOAuth2Client(config));
            INSTANCES.put(config, result);
        }

        return result;
    }

    private ImplicitGrantOAuth2Client(OAuth2ClientConfig config) {
        this.config = config;
        bindJsCallbackFunction(config.getJsCallbackFunctionName());
    }

    private native void bindJsCallbackFunction(String jsCallbackFunctionName) /*-{
        $wnd.OAuth2Client = $wnd.OAuth2Client || {};

        var thiz = this;

        $wnd.OAuth2Client[jsCallbackFunctionName] = $entry(function (hash) {
            thiz.@io.itdraft.gwt.oauth2.implicit.ImplicitGrantOAuth2Client::callback(Ljava/lang/String;)(hash);
        });
    }-*/;

    @Override
    public OAuth2ClientConfig getConfig() {
        return config;
    }

    protected void doRetrieveAccessToken(Set<String> scopes,
                                         AccessTokenCallback callback) {
        if (this.callback != null) {
            throw new IllegalStateException("Access token is being retrieved");
        }

        this.callback = callback;

        if (flowExecutor == null) {
            flowExecutor = createDefaultFlowExecutor();
        }

        AccessTokenRequest request = AccessTokenRequest.create(
                config.getAuthEndpointUrl(),
                config.getClientId(),
                config.getRedirectUri(),
                scopes);

        flowExecutor.run(request);
    }

    public static FlowExecutor createDefaultFlowExecutor() {
        return new ThroughPopupWindowExecutor();
    }

    private void callback(String uriFragment) {
        if (callback == null) {
            throw new IllegalStateException("OAuth2 request was not sent");
        }

        flowExecutor.finish(uriFragment, callback);

        callback = null;
    }
}
