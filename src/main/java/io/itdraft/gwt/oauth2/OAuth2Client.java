package io.itdraft.gwt.oauth2;

public interface OAuth2Client {

    void retrieveAccessToken(AuthorizationRequest request, AuthorizationRequestCallback callback);

    void refreshAccessToken(AuthorizationRequest request, AuthorizationRequestCallback callback);

}
