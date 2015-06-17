package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2RequestCallback;
import com.igorkurilenko.gwt.oauth2.OAuth2Response;

public interface FlowFinalizer {

    void finish(String uriFragment, OAuth2RequestCallback callback);

}
