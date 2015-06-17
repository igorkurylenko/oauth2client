package com.igorkurilenko.gwt.oauth2.implicit;

import com.google.gwt.core.client.JsArrayString;
import com.igorkurilenko.gwt.oauth2.OAuth2Request;


class Utils {

    public static String SCOPES_DELIMITER = " ";

    private Utils() {
    }

    public static String buildRetrieveAccessTokenUrl(OAuth2Request request) {
        String authEndpointUrl = request.getAuthEndpointUrl();
        String clientId = request.getClientId();
        String redirectUri = request.getRedirectUri();
        String state = request.getState() + "";
        JsArrayString scopes = request.getScopes();
        String scopesAsString = scopesToString(scopes);
        String clientIdEncoded = encodeURIComponent(clientId);
        String redirectUriEncoded = encodeURIComponent(redirectUri);
        String scopesEncoded = encodeURIComponent(scopesAsString);
        String stateEncoded = encodeURIComponent(state);
        String firstSeparator = authEndpointUrl.contains("?") ? "&" : "?";

        return new StringBuilder(authEndpointUrl)
                .append(firstSeparator)
                .append("response_type=token")
                .append("&client_id=").append(clientIdEncoded)
                .append("&scope=").append(scopesEncoded)
                .append("&state=").append(stateEncoded)
                .append("&redirect_uri=").append(redirectUriEncoded)
                .toString();
    }

    public static String scopesToString(JsArrayString scopes) {
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < scopes.length(); i++) {
            String scope = scopes.get(i);
            resultBuilder.append(scope);
            resultBuilder.append(SCOPES_DELIMITER);
        }

        return resultBuilder.toString().trim();
    }

    public static native String encodeURIComponent(String s) /*-{
        return encodeURIComponent(s);
    }-*/;
}
