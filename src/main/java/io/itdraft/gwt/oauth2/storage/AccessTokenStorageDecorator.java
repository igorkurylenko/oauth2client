package io.itdraft.gwt.oauth2.storage;

import io.itdraft.gwt.oauth2.AccessToken;

public abstract class AccessTokenStorageDecorator implements AccessTokenStorage {

    protected final AccessTokenStorage decorated;

    protected AccessTokenStorageDecorator(AccessTokenStorage decorated) {
        this.decorated = decorated;
    }

    public AccessToken get(String key) {
        return decorated.get(key);
    }

    public void put(String key, AccessToken accessToken) {
        decorated.put(key, accessToken);
    }

    public void remove(String key) {
        decorated.remove(key);
    }
}
