package io.itdraft.gwt.oauth2.implicit;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import io.itdraft.gwt.oauth2.AccessTokenRequest;
import io.itdraft.gwt.oauth2.AuthorizationResponse;

import java.util.HashMap;
import java.util.Map;


class Utils {

    public static String SCOPES_DELIMITER = " ";

    private Utils() {
    }

    public static String buildRetrieveAccessTokenUrl(AccessTokenRequest request) {
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

    public static native String decodeURIComponent(String s) /*-{
        return decodeURIComponent(s);
    }-*/;

    public static AuthorizationResponse uriFragmentToOAuth2Response(String hash) {
        String query = getQuery(hash);

        Map<String, String> params = parseUriFragment(query);

        AuthorizationResponse authResponse = JavaScriptObject.createObject().cast();
        authResponse.setAccessToken(params.get("access_token"));
        authResponse.setExpiresIn(params.get("expires_in"));
        authResponse.setState(params.get("state"));
        authResponse.setError(params.get("error"));
        authResponse.setErrorDescription(params.get("error_description"));
        authResponse.setErrorUri(params.get("error_uri"));

        return authResponse;
    }

    private static Map<String, String> parseUriFragment(String uriFragment) {
        Map<String, String> params = new HashMap<String, String>();

        RegExp regExp = RegExp.compile("([^&=]+)=([^&]*)", "gm");

        for (MatchResult matchResult = regExp.exec(uriFragment); matchResult != null;
             matchResult = regExp.exec(uriFragment)) {

            String paramName = decodeURIComponent(matchResult.getGroup(1));
            String paramValue = decodeURIComponent(matchResult.getGroup(2));

            params.put(paramName, paramValue);
        }

        return params;
    }

    private static String getQuery(String uriFragment) {
        if (uriFragment.length() > 0 && uriFragment.charAt(0) == '#') {
            return uriFragment.substring(1);
        }

        return uriFragment;
    }
}
