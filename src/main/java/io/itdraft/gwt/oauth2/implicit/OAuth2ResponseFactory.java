package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.OAuth2Response;

public interface OAuth2ResponseFactory {

    OAuth2Response create(String uriFragment);

}
