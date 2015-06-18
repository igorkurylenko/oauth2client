package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.OAuth2Request;

public interface FlowInitiator {

    void run(OAuth2Request request);

}
