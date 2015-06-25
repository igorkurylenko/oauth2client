package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AuthorizationResponse;

public class DefaultAuthorizationResponseFactory implements AuthorizationResponseFactory {
    @Override
    public AuthorizationResponse create(String uriFragment) {
        return Utils.uriFragmentToOAuth2Response(uriFragment);
    }
}
