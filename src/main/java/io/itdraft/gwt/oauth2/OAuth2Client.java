package io.itdraft.gwt.oauth2;

public abstract class OAuth2Client {

    public abstract OAuth2ClientConfig getConfig();

    public void retrieveAccessToken(AccessTokenCallback callback){
        if (callback == null) {
            throw new IllegalArgumentException("Auth2 request callback must be not null");
        }

        doRetrieveAccessToken(callback);
    }
    protected abstract void doRetrieveAccessToken(AccessTokenCallback callback);

    public abstract void refreshAccessToken(AccessTokenCallback callback);
}
