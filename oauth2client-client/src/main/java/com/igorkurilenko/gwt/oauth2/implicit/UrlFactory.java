package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2Request;

public interface UrlFactory {

    String buildUrl(OAuth2Request request);

}
