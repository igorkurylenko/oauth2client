package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AuthorizationRequest;

public interface AuthorizationUrlFactory {

    String buildUrl(AuthorizationRequest request);

}
