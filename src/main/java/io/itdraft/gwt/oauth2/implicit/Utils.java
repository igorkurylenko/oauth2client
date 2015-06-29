package io.itdraft.gwt.oauth2.implicit;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import io.itdraft.gwt.oauth2.OAuth2ProviderResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


class Utils {

    public static String SCOPES_DELIMITER = " ";
    public static String EMPTY_STRING = "";

    private Utils() {
    }

    public static String buildRetrieveAccessTokenUrl(String authorizationEndpointUrl,
                                                     String clientId,
                                                     String redirectUri,
                                                     Set<String> scopes,
                                                     String state) {
        StringBuilder urlBuilder = new StringBuilder(authorizationEndpointUrl);

        String clientIdEncoded = encodeURIComponent(clientId);
        String firstSeparator = authorizationEndpointUrl.contains("?") ? "&" : "?";

        urlBuilder.append(firstSeparator)
                .append("response_type=token")
                .append("&client_id=").append(clientIdEncoded);

        String scopesAsString = scopesToString(scopes);
        if (!isEmpty(scopesAsString)) {
            String scopesEncoded = encodeURIComponent(scopesAsString);
            urlBuilder.append("&scope=").append(scopesEncoded);
        }

        if (!isEmpty(redirectUri)) {
            String redirectUriEncoded = encodeURIComponent(redirectUri);
            urlBuilder.append("&redirect_uri=").append(redirectUriEncoded);
        }

        if (!isEmpty(state)) {
            String stateEncoded = encodeURIComponent(state);
            urlBuilder.append("&state=").append(stateEncoded);
        }

        return urlBuilder.toString();
    }

    public static String scopesToString(Set<String> scopes) {
        if (scopes == null) {
            return null;
        }

        StringBuilder resultBuilder = new StringBuilder();

        for (String scope : scopes) {
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

    public static OAuth2ProviderResponse uriFragmentToOAuth2ProviderResponse(String hash) {
        String query = getQuery(hash);

        Map<String, String> params = parseUriFragment(query);

        return new OAuth2ProviderResponse(
                params.get("access_token"),
                params.get("expires_in"),
                params.get("state"),
                params.get("error"),
                params.get("error_description"),
                params.get("error_uri")
        );
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

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
