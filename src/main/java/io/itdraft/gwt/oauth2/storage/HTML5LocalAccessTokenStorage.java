package io.itdraft.gwt.oauth2.storage;

import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;
import io.itdraft.gwt.oauth2.AccessToken;

class HTML5LocalAccessTokenStorage implements AccessTokenStorage {

    private StorageMap storageMap;

    public HTML5LocalAccessTokenStorage() {
        Storage localStorage = Storage.getLocalStorageIfSupported();
        if (localStorage != null) {
            storageMap = new StorageMap(localStorage);
        }
    }

    private void ensureLocalStorageSupported() {
        if (storageMap == null) {
            throw new UnsupportedOperationException("HTML5 local storage is not supported");
        }
    }

    public AccessToken get(String key) {
        ensureLocalStorageSupported();

        String serializedAccessToken = storageMap.get(key);

        return serializedAccessToken == null ? null :
                AccessToken.deserialize(serializedAccessToken);
    }

    public void put(String key, AccessToken accessToken) {
        ensureLocalStorageSupported();

        String serializedAccessToken = accessToken.serialize();

        storageMap.put(key, serializedAccessToken);
    }

    @Override
    public void remove(String key) {
        ensureLocalStorageSupported();

        storageMap.remove(key);
    }
}
