package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.OAuth2ProviderResponse;

public interface AuthorizationResponseFactory {

    OAuth2ProviderResponse create(String uriFragment);

}
