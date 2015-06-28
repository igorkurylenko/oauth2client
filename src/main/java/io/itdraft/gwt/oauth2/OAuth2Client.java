package io.itdraft.gwt.oauth2;

public interface OAuth2Client {

    void retrieveAccessToken(AccessTokenRequest request, AccessTokenCallback callback);

}
