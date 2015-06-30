package io.itdraft.gwt.oauth2;

public abstract class OAuth2Client {
    public static final AccessTokenCallback EMPTY_CALLBACK = new AccessTokenCallback() {
        protected void doOnFailure(FailureReason reason) {
        }

        protected void doOnSuccess(AccessToken token) {
        }
    };

    public abstract OAuth2ClientConfig getConfig();

    public final void requestAccessToken(AccessTokenCallback callback) {
        doRequestAccessToken(callback == null ? EMPTY_CALLBACK : callback);
    }

    protected abstract void doRequestAccessToken(AccessTokenCallback callback);

    public final void refreshAccessToken() {
        refreshAccessToken(EMPTY_CALLBACK);
    }

    public final void refreshAccessToken(AccessTokenCallback callback) {
        doRefreshAccessToken(callback == null ? EMPTY_CALLBACK : callback);
    }

    public abstract void doRefreshAccessToken(AccessTokenCallback callback);

    /**
     * Resets cached token, stops a started flow execution.
     * Useful method for a logout scenario implementations.
     */
    public abstract void reset();
}
