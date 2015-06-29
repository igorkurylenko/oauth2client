package io.itdraft.gwt.oauth2;

import io.itdraft.gwt.oauth2.storage.AccessTokenStorage;
import java.util.Set;


public class WithStorage extends OAuth2ClientDecorator {
    private AccessTokenStorage storage = AccessTokenStorage.INSTANCE;

    public WithStorage(OAuth2Client decorated) {
        super(decorated);
    }

    public void doRetrieveAccessToken(AccessTokenCallback callback) {
        String key = buildStorageKey();

        AccessToken accessToken = storage.get(key);
        if (accessToken != null) {
            callback.onSuccess(accessToken);

        } else {
            AccessTokenCallback wrappedCallback = wrapCallback(callback, key);

            super.doRetrieveAccessToken(wrappedCallback);
        }
    }

    private AccessTokenCallback wrapCallback(final AccessTokenCallback callback, final String key) {
        return new AccessTokenCallback() {
            @Override
            public void doOnFailure(FailureReason reason) {
                callback.onFailure(reason);
            }

            @Override
            public void doOnSuccess(AccessToken result) {
                storage.put(key, result);

                callback.onSuccess(result);
            }
        };
    }

    private String buildStorageKey() {
        StringBuilder keyBuilder = new StringBuilder();

        keyBuilder.append(getConfig().getAuthEndpointUrl());
        keyBuilder.append(getConfig().getClientId());

        Set<String> scopes = getConfig().getScopes();
        if (scopes != null) {
            for (String scope : scopes) {
                keyBuilder.append(scope);
            }
        }

        return keyBuilder.toString();
    }
}
