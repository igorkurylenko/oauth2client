package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AuthorizationResponse;

public interface OAuth2ResponseFactory {

    AuthorizationResponse create(String uriFragment);

}
