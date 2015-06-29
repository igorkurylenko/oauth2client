package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.OAuth2ProviderResponse;

public class DefaultAuthorizationResponseFactory implements AuthorizationResponseFactory {
    @Override
    public OAuth2ProviderResponse create(String uriFragment) {
        return Utils.uriFragmentToOAuth2ProviderResponse(uriFragment);
    }
}
