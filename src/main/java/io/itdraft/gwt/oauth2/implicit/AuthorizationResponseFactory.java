package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AuthorizationResponse;

public interface AuthorizationResponseFactory {

    AuthorizationResponse create(String uriFragment);

}
