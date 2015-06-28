package io.itdraft.gwt.oauth2;

import com.google.gwt.junit.client.GWTTestCase;

import java.util.Date;

public class AccessTokenGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "io.itdraft.gwt.oauth2.OAuth2Client";
    }

    public void testParseAccessToken() throws Exception {
        AccessToken original = new AccessToken("token&-_.!~*'()", 3600);
        String ownWaySerialized = original.serialize();
        AccessToken deserialized = AccessToken.deserialize(ownWaySerialized);

        assertEquals("Deserialized access token equals the original", original, deserialized);
    }

    public void testIsExpired() {
        AccessToken accessToken = new AccessToken("token", 1);
        sleepFor(1001);
        assertTrue("Access token is expired", accessToken.isExpired());

        accessToken = new AccessToken("token", 3600);
        assertTrue("Access token isn't expired", !accessToken.isExpired());
    }

    private void sleepFor(int sleepDurationMillis) {
        long now = new Date().getTime();
        while (new Date().getTime() < now + sleepDurationMillis) { }
    }
}