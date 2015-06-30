package io.itdraft.gwt.oauth2;

import com.google.gwt.user.client.Timer;

public class WithAutoRefresh extends OAuth2ClientDecorator {
    public static final int APPROXIMATE_TIME_TO_REFRESH_TOKEN_MILLIS = 10000;

    private final RefreshTimer refreshTimer = new RefreshTimer();

    public WithAutoRefresh(OAuth2Client decorated) {
        super(decorated);
    }

    public void refreshAccessToken(AccessTokenCallback callback) {
        super.refreshAccessToken(wrapCallback(callback));
    }

    public void doRequestAccessToken(AccessTokenCallback callback) {
        super.doRequestAccessToken(wrapCallback(callback));
    }

    private AccessTokenCallback wrapCallback(final AccessTokenCallback callback) {
        return new AccessTokenCallback() {
            @Override
            protected void doOnFailure(FailureReason reason) {
                callback.onFailure(reason);
            }

            @Override
            protected void doOnSuccess(AccessToken token) {
                refreshTimer.scheduleNextRefresh(token);

                callback.onSuccess(token);
            }
        };
    }

    class RefreshTimer extends Timer {
        final AccessTokenCallback EMPTY_CALLBACK = new AccessTokenCallback() {
            protected void doOnFailure(FailureReason reason) {
            }

            protected void doOnSuccess(AccessToken token) {
            }
        };
        AccessToken accessTokenScheduledFor;

        void scheduleNextRefresh(AccessToken accessToken) {
            if (!accessToken.equals(accessTokenScheduledFor)) {
                cancel();

                accessTokenScheduledFor = accessToken;

                schedule(getDelayMillis());
            }
        }

        private int getDelayMillis() {
            int result = accessTokenScheduledFor.getTimeLeftInSeconds() * 1000
                    - APPROXIMATE_TIME_TO_REFRESH_TOKEN_MILLIS;

            return result < APPROXIMATE_TIME_TO_REFRESH_TOKEN_MILLIS ? 0 : result;
        }

        @Override
        public void run() {
            refreshAccessToken(EMPTY_CALLBACK);
        }
    }
}
