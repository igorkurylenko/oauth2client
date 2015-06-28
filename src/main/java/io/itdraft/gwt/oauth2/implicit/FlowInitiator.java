package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AccessTokenRequest;

public interface FlowInitiator {

    void run(AccessTokenRequest request);

}
