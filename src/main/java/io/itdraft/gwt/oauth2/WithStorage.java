package io.itdraft.gwt.oauth2;

import io.itdraft.gwt.oauth2.storage.AccessTokenStorage;


public class WithStorage extends OAuth2ClientDecorator {
    private AccessTokenStorage storage = AccessTokenStorage.INSTANCE;
    private AccessToken cachedToken;

    public WithStorage(OAuth2Client decorated) {
        super(decorated);
    }

    public void doRequestAccessToken(AccessTokenCallback callback) {
        String key = getStorageKey();

        AccessToken accessToken = cachedToken != null ? cachedToken : storage.get(key);

        if (isNiceAccessToken(accessToken)) {
            callback.onSuccess(accessToken);

        } else {
            super.doRequestAccessToken(wrapCallback(callback));
        }
    }

    public void refreshAccessToken(AccessTokenCallback callback) {
        super.refreshAccessToken(wrapCallback(callback));
    }

    public boolean isNiceAccessToken(AccessToken accessToken) {
        return accessToken != null && !accessToken.isExpired();
    }

    private AccessTokenCallback wrapCallback(final AccessTokenCallback callback) {
        return new AccessTokenCallback() {
            public void doOnFailure(FailureReason reason) {
                callback.onFailure(reason);
            }

            public void doOnSuccess(AccessToken token) {
                storeToken(token);

                callback.onSuccess(token);
            }
        };
    }

    public void storeToken(AccessToken token) {
        String key = getStorageKey();
        cachedToken = token;
        storage.put(key, token);
    }

    private String getStorageKey() {
        return getConfig().hashCode() + "";
    }
}
