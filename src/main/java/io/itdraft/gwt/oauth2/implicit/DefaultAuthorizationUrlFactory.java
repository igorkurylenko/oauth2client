package io.itdraft.gwt.oauth2.implicit;

import java.util.Set;

public class DefaultAuthorizationUrlFactory implements AuthorizationUrlFactory {

    public String buildUrl(String authorizationEndpointUrl,
                           String clientId,
                           String redirectUri,
                           Set<String> scopes,
                           String state) {
        return Utils.buildRetrieveAccessTokenUrl(
                authorizationEndpointUrl, clientId, redirectUri, scopes, state);
    }
}

