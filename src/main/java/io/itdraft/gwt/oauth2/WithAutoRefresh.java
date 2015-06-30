package io.itdraft.gwt.oauth2;

import com.google.gwt.user.client.Timer;

public class WithAutoRefresh extends OAuth2ClientDecorator {
    public static final int APPROXIMATE_TIME_TO_REFRESH_TOKEN_MILLIS = 10000;

    private final RefreshTimer refreshTimer = new RefreshTimer();

    public WithAutoRefresh(OAuth2Client decorated) {
        super(decorated);
    }

    public void doRefreshAccessToken(AccessTokenCallback callback) {
        super.doRefreshAccessToken(wrapCallback(callback));
    }

    public void reset() {
        refreshTimer.reset();
        super.reset();
    }

    public void doRequestAccessToken(AccessTokenCallback callback) {
        super.doRequestAccessToken(wrapCallback(callback));
    }

    private AccessTokenCallback wrapCallback(final AccessTokenCallback callback) {
        return new AccessTokenCallback() {
            protected void doOnFailure(FailureReason reason) {
                callback.onFailure(reason);
            }

            protected void doOnSuccess(AccessToken token) {
                refreshTimer.scheduleNextRefresh(token);

                callback.onSuccess(token);
            }
        };
    }

    class RefreshTimer extends Timer {
        AccessToken accessTokenScheduledFor;

        public void run() {
            doRefreshAccessToken(EMPTY_CALLBACK);
        }

        void scheduleNextRefresh(AccessToken accessToken) {
            if (accessToken != null && !accessToken.equals(accessTokenScheduledFor)) {
                cancel();

                accessTokenScheduledFor = accessToken;

                schedule(getDelayMillis());
            }
        }

        void reset() {
            cancel();
            accessTokenScheduledFor = null;
        }

        private int getDelayMillis() {
            int result = accessTokenScheduledFor.getTimeLeftInSeconds() * 1000
                    - APPROXIMATE_TIME_TO_REFRESH_TOKEN_MILLIS;

            return result < APPROXIMATE_TIME_TO_REFRESH_TOKEN_MILLIS ? 0 : result;
        }
    }
}
