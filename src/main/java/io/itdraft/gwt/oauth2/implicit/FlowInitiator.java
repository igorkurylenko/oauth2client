package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AuthorizationRequest;

public interface FlowInitiator {

    void run(AuthorizationRequest request);

}
