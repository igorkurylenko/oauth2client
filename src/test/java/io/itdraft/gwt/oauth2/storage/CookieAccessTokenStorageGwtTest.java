package io.itdraft.gwt.oauth2.storage;

import com.google.gwt.junit.client.GWTTestCase;
import io.itdraft.gwt.oauth2.AccessToken;

public class CookieAccessTokenStorageGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "io.itdraft.gwt.oauth2.OAuth2Client";
    }

    public void testCookieAccessTokenStorage() {
        CookieAccessTokenStorage storage = new CookieAccessTokenStorage();
        String key = "http://example.com/oauth2/authorize";
        AccessToken originalAccessToken = new AccessToken("token&-_.!~*'()", 3600);

        storage.put(key, originalAccessToken);

        assertEquals(storage.get(key), originalAccessToken);
    }
}
