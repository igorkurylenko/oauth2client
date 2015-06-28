package io.itdraft.gwt.oauth2.storage;

import com.google.gwt.user.client.Cookies;
import io.itdraft.gwt.oauth2.AccessToken;

import java.util.Date;

class CookieAccessTokenStorage implements AccessTokenStorage {

    public AccessToken get(String key) {
        String serializedToken = Cookies.getCookie(key);

        return serializedToken == null ? null : AccessToken.deserialize(serializedToken);
    }

    public void put(String key, AccessToken accessToken) {
        String serializedToken = accessToken.serialize();
        Date expirationDate = accessToken.getExpirationDate();

        Cookies.setCookie(key, serializedToken, expirationDate);
    }
}

