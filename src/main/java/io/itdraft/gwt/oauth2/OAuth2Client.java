package io.itdraft.gwt.oauth2;

public interface OAuth2Client {

    void retrieveAccessToken(AuthorizationRequest request, OAuth2RequestCallback callback);

    void refreshAccessToken(AuthorizationRequest request, OAuth2RequestCallback callback);

}
