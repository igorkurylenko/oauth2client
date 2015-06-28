package io.itdraft.gwt.oauth2;

import io.itdraft.gwt.oauth2.storage.AccessTokenStorage;

import java.util.Set;

public class CachedOAuth2Client extends OAuth2ClientDecorator {
    private AccessTokenStorage storage = AccessTokenStorage.INSTANCE;

    public CachedOAuth2Client(OAuth2Client decorated) {
        super(decorated);
    }

    public void doRetrieveAccessToken(Set<String> scopes, AccessTokenCallback callback) {
        String key = buildStorageKey(scopes);

        AccessToken accessToken = storage.get(key);
        if (accessToken != null) {
            callback.onSuccess(accessToken);

        } else {
            AccessTokenCallback wrappedCallback = wrapCallback(callback, key);

            super.doRetrieveAccessToken(scopes, wrappedCallback);
        }
    }

    private AccessTokenCallback wrapCallback(final AccessTokenCallback callback, final String key) {
        return new AccessTokenCallback() {
            @Override
            public void onFailure(Throwable reason) {
                callback.onFailure(reason);
            }

            @Override
            public void onSuccess(AccessToken result) {
                storage.put(key, result);

                callback.onSuccess(result);
            }
        };
    }

    private String buildStorageKey(Set<String> scopes) {
        StringBuilder keyBuilder = new StringBuilder();

        keyBuilder.append(getConfig().getAuthEndpointUrl());
        keyBuilder.append(getConfig().getClientId());

        if (scopes != null) {
            for (String scope : scopes) {
                keyBuilder.append(scope);
            }
        }

        return keyBuilder.toString();
    }
}
