package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2Request;

public class DefaultAuthUrlFactory implements AuthUrlFactory {

    @Override
    public String buildUrl(OAuth2Request request) {
        return Utils.buildRetrieveAccessTokenUrl(request);
    }
}

