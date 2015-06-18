package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.OAuth2RequestCallback;

public interface FlowFinalizer {

    void finish(String uriFragment, OAuth2RequestCallback callback);

}
