package io.itdraft.gwt.oauth2;

import java.util.Set;

public abstract class OAuth2Client {

    public abstract OAuth2ClientConfig getConfig();

    public void retrieveAccessToken(AccessTokenCallback callback){
        doRetrieveAccessToken(null, callback);
    }

    public void retrieveAccessToken(Set<String> scopes, AccessTokenCallback callback){
        if (callback == null) {
            throw new IllegalArgumentException("Auth2 request callback must be not null");
        }

        doRetrieveAccessToken(scopes, callback);
    }
    protected abstract void doRetrieveAccessToken(Set<String> scopes, AccessTokenCallback callback);

}
