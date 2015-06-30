package io.itdraft.gwt.oauth2;

public abstract class OAuth2Client {

    public abstract OAuth2ClientConfig getConfig();

    public void requestAccessToken(AccessTokenCallback callback){
        if (callback == null) {
            throw new IllegalArgumentException("Auth2 request callback must be not null");
        }

        doRequestAccessToken(callback);
    }

    protected abstract void doRequestAccessToken(AccessTokenCallback callback);

    public abstract void refreshAccessToken(AccessTokenCallback callback);
}
