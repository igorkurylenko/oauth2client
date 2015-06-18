package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.OAuth2Request;

public class DefaultAuthUrlFactory implements AuthUrlFactory {

    public String buildUrl(OAuth2Request request) {
        return Utils.buildRetrieveAccessTokenUrl(request);
    }
}

