package io.itdraft.gwt.oauth2.storage;

import io.itdraft.gwt.oauth2.AccessToken;

public abstract class AccessTokenStorageDecorator implements AccessTokenStorage {

    protected final AccessTokenStorage decoratedStorage;

    protected AccessTokenStorageDecorator(AccessTokenStorage decoratedStorage) {
        this.decoratedStorage = decoratedStorage;
    }

    @Override
    public AccessToken get(String key) {
        return decoratedStorage.get(key);
    }

    @Override
    public void put(String key, AccessToken accessToken) {
        decoratedStorage.put(key, accessToken);
    }
}
