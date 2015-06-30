package io.itdraft.gwt.oauth2.storage;

import io.itdraft.gwt.oauth2.AccessToken;

public class WithKeyPrefix extends AccessTokenStorageDecorator {
    protected static final String KEY_PREFIX = "OAuth2Client";

    protected WithKeyPrefix(AccessTokenStorage storage) {
        super(storage);
    }

    public AccessToken get(String key) {
        return super.get(KEY_PREFIX + key);
    }

    public void put(String key, AccessToken accessToken) {
        super.put(KEY_PREFIX + key, accessToken);
    }

    public void remove(String key) {
        super.remove(KEY_PREFIX + key);
    }
}
