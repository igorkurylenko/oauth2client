package io.itdraft.gwt.oauth2.storage;

import com.google.gwt.junit.client.GWTTestCase;
import io.itdraft.gwt.oauth2.AccessToken;

public class HTML5LocalAccessTokenStorageGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "io.itdraft.gwt.oauth2.OAuth2Client";
    }

    public void testAccessTokenLocalStorage() {
        HTML5LocalAccessTokenStorage storage = new HTML5LocalAccessTokenStorage();
        String key = "http://example.com/oauth2/authorize";
        AccessToken originalAccessToken = new AccessToken("token&-_.!~*'()", 3600);

        storage.put(key, originalAccessToken);

        assertEquals(storage.get(key), originalAccessToken);
    }
}