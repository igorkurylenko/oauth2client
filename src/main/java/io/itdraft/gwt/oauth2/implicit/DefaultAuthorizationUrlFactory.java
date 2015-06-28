package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AccessTokenRequest;

public class DefaultAuthorizationUrlFactory implements AuthorizationUrlFactory {

    public String buildUrl(AccessTokenRequest request) {
        return Utils.buildRetrieveAccessTokenUrl(request);
    }
}

