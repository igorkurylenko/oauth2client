package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.OAuth2Request;

public interface AuthUrlFactory {

    String buildUrl(OAuth2Request request);

}
