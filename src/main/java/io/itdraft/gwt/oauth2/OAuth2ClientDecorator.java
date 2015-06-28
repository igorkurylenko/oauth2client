package io.itdraft.gwt.oauth2;

import java.util.Set;

public abstract class OAuth2ClientDecorator extends OAuth2Client {

    protected final OAuth2Client decorated;

    protected OAuth2ClientDecorator(OAuth2Client decorated) {
        this.decorated = decorated;
    }

    public void doRetrieveAccessToken(Set<String> scopes, AccessTokenCallback callback) {
        decorated.doRetrieveAccessToken(scopes, callback);
    }

    @Override
    public OAuth2ClientConfig getConfig() {
        return decorated.getConfig();
    }
}
