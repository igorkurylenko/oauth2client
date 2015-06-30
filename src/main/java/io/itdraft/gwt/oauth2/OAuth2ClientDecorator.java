package io.itdraft.gwt.oauth2;

public abstract class OAuth2ClientDecorator extends OAuth2Client {
    protected final OAuth2Client decorated;

    protected OAuth2ClientDecorator(OAuth2Client decorated) {
        this.decorated = decorated;
    }

    public void doRequestAccessToken(AccessTokenCallback callback) {
        decorated.doRequestAccessToken(callback);
    }

    public void refreshAccessToken(AccessTokenCallback callback) {
        decorated.refreshAccessToken(callback);
    }

    public OAuth2ClientConfig getConfig() {
        return decorated.getConfig();
    }

    public OAuth2Client getDecorated() {
        return decorated;
    }
}
