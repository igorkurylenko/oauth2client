package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2Response;

public interface FlowFinalizer {

    void finalizeFlow(OAuth2Response response);

}
