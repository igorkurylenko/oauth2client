package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2Request;
import com.igorkurilenko.gwt.oauth2.OAuth2RequestCallback;

public interface FlowInitializer {

    void initFlow(OAuth2Request request, OAuth2RequestCallback callback);

}
