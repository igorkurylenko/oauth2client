package io.itdraft.gwt.oauth2;

import java.util.Objects;

public class OAuth2ClientConfig {
    private static final String JS_CALLBACK_FUNCTION_NAME = "oauth2callback";
    private String clientId;
    private String redirectUri;
    private String authEndpointUrl;
    private String jsCallbackFunctionName;

    public OAuth2ClientConfig(String clientId, String redirectUri, String authEndpointUrl) {
        this(authEndpointUrl, clientId, redirectUri, JS_CALLBACK_FUNCTION_NAME);
    }

    public OAuth2ClientConfig(String clientId, String redirectUri,
                              String authEndpointUrl, String jsCallbackFunctionName) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.authEndpointUrl = authEndpointUrl;
        this.jsCallbackFunctionName = jsCallbackFunctionName;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId){
        this.clientId = clientId;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getAuthEndpointUrl() {
        return this.authEndpointUrl;
    }

    public void setAuthEndpointUrl(String authEndpointUrl) {
        this.authEndpointUrl = authEndpointUrl;
    }

    public String getJsCallbackFunctionName() {
        return this.jsCallbackFunctionName;
    }

    public void setJsCallbackFunctionName(String jsCallbackFunctionName) {
        this.jsCallbackFunctionName = jsCallbackFunctionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuth2ClientConfig that = (OAuth2ClientConfig) o;
        return Objects.equals(clientId, that.clientId) &&
                Objects.equals(redirectUri, that.redirectUri) &&
                Objects.equals(authEndpointUrl, that.authEndpointUrl) &&
                Objects.equals(jsCallbackFunctionName, that.jsCallbackFunctionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, redirectUri, authEndpointUrl, jsCallbackFunctionName);
    }
}
