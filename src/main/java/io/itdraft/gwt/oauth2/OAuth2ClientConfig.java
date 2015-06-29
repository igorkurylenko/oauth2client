package io.itdraft.gwt.oauth2;

import java.util.Objects;
import java.util.Set;

public class OAuth2ClientConfig {
    public static final String DEFAULT_JS_CALLBACK_FN_NAME = "oauth2callback";
    private final Set<String> scopes;
    private final String clientId;
    private final String redirectUri;
    private final String authEndpointUrl;
    private final String jsCallbackFunctionName;

    public OAuth2ClientConfig(String clientId,
                              String redirectUri,
                              String authEndpointUrl,
                              Set<String> scopes) {
        this(authEndpointUrl, clientId, redirectUri, scopes, DEFAULT_JS_CALLBACK_FN_NAME);
    }

    public OAuth2ClientConfig(String clientId,
                              String redirectUri,
                              String authEndpointUrl,
                              Set<String> scopes,
                              String jsCallbackFunctionName) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.authEndpointUrl = authEndpointUrl;
        this.jsCallbackFunctionName = jsCallbackFunctionName;
        this.scopes = scopes;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public String getAuthEndpointUrl() {
        return this.authEndpointUrl;
    }

    public String getJsCallbackFunctionName() {
        return this.jsCallbackFunctionName;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuth2ClientConfig that = (OAuth2ClientConfig) o;
        return Objects.equals(scopes, that.scopes) &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(redirectUri, that.redirectUri) &&
                Objects.equals(authEndpointUrl, that.authEndpointUrl) &&
                Objects.equals(jsCallbackFunctionName, that.jsCallbackFunctionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scopes, clientId, redirectUri, authEndpointUrl, jsCallbackFunctionName);
    }
}
