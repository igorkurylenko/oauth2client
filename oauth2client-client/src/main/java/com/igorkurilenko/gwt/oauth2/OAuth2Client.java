package com.igorkurilenko.gwt.oauth2;

public interface OAuth2Client {

    void retrieveAccessToken(OAuth2Request request, OAuth2RequestCallback callback);

    void refreshAccessToken(OAuth2Request request, OAuth2RequestCallback callback);

}
