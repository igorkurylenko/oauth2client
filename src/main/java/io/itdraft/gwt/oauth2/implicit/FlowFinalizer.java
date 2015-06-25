package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AuthorizationRequestCallback;

public interface FlowFinalizer {

    void finish(String uriFragment, AuthorizationRequestCallback callback);

}
