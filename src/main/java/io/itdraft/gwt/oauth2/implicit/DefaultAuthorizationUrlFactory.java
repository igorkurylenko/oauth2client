package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AuthorizationRequest;

public class DefaultAuthorizationUrlFactory implements AuthorizationUrlFactory {

    public String buildUrl(AuthorizationRequest request) {
        return Utils.buildRetrieveAccessTokenUrl(request);
    }
}

