package io.itdraft.gwt.oauth2.storage;

import com.google.gwt.storage.client.Storage;
import io.itdraft.gwt.oauth2.AccessToken;

public interface AccessTokenStorage {
    AccessTokenStorage INSTANCE = new WithKeyPrefix(
            Storage.isLocalStorageSupported() ?
                    new HTML5LocalAccessTokenStorage() :
                    new CookieAccessTokenStorage());


    AccessToken get(String key);

    void put(String key, AccessToken accessToken);

    void remove(String key);
}
