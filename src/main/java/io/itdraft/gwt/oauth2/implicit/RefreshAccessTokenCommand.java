package io.itdraft.gwt.oauth2.implicit;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.RootPanel;
import io.itdraft.gwt.oauth2.AccessTokenCallback;
import io.itdraft.gwt.oauth2.FailureReason;
import io.itdraft.gwt.oauth2.OAuth2ClientConfig;

public class RefreshAccessTokenCommand extends AccessTokenCommand {

    public static final int TIMEOUT = 10000;
    private final Frame frame;
    private boolean inProgress = false;

    public RefreshAccessTokenCommand(OAuth2ClientConfig config, AccessTokenCallback callback) {
        super(config, callback);

        frame = new Frame();
        frame.setVisible(false);
    }

    public void execute() {
        inProgress = true;

        Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
            @Override
            public boolean execute() {
                if (inProgress) {
                    callback.onFailure(FailureReason.REFRESH_TOKEN_TIMEOUT);
                }
                return false;
            }
        }, TIMEOUT);

        String url = authorizationUrlFactory.buildUrl(
                config.getAuthEndpointUrl(),
                config.getClientId(),
                config.getRedirectUri(),
                config.getScopes(),
                state
        );

        RootPanel.get().add(frame);

        frame.setUrl(url);
    }

    @Override
    protected void finish() {
        inProgress = false;

        RootPanel.get().remove(frame);
    }
}


