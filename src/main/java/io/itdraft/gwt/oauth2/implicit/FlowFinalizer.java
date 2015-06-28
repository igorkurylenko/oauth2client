package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AccessTokenCallback;

public interface FlowFinalizer {

    void finish(String uriFragment, AccessTokenCallback callback);

}
