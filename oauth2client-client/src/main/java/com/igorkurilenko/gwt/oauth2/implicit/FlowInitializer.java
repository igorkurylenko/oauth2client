package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2Request;
import com.igorkurilenko.gwt.oauth2.OAuth2RequestCallback;

public interface FlowInitializer {

    void run(OAuth2Request request);

}
