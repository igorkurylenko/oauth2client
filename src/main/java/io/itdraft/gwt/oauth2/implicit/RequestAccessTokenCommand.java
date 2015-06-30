package io.itdraft.gwt.oauth2.implicit;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import io.itdraft.gwt.oauth2.AccessTokenCallback;
import io.itdraft.gwt.oauth2.FailureReason;
import io.itdraft.gwt.oauth2.OAuth2ClientConfig;

class RequestAccessTokenCommand extends AccessTokenCommand {

    public static final int CHECK_POPUP_CLOSED_DELAY_MS = 200;
    private boolean inProgress;

    static final class PopupWindow extends JavaScriptObject {

        protected PopupWindow() {
        }

        native boolean isClosed() /*-{
            return this.closed;
        }-*/;

        native void close() /*-{
            this.close();
        }-*/;

        native void focus() /*-{
            this.focus();
        }-*/;
    }

    private static final int DEFAULT_WINDOW_WIDTH = 800;
    private static final int DEFAULT_WINDOW_HEIGHT = 600;

    private PopupWindow popupWindow;
    private int windowWidth;
    private int windowHeight;

    public RequestAccessTokenCommand(OAuth2ClientConfig config,
                                     AccessTokenCallback callback) {
        this(config, callback, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    }

    public RequestAccessTokenCommand(OAuth2ClientConfig config,
                                     AccessTokenCallback callback,
                                     int windowWidth,
                                     int windowHeight) {
        super(config, callback);

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public void execute() {
        inProgress = true;

        String url = authorizationUrlFactory.buildUrl(
                config.getAuthEndpointUrl(),
                config.getClientId(),
                config.getRedirectUri(),
                config.getScopes(),
                state
        );

        popupWindow = openPopupWindow(url, windowHeight, windowWidth);

        if (popupWindow == null) {
            callback.onFailure(FailureReason.CANNOT_OPEN_POPUP_WINDOW);

        } else {
            Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
                @Override
                public boolean execute() {
                    if (inProgress && popupWindow.isClosed()) {
                        inProgress = false;
                        callback.onFailure(FailureReason.POPUP_WINDOW_CLOSED);
                    }

                    return inProgress;
                }
            }, CHECK_POPUP_CLOSED_DELAY_MS);
        }
    }

    public void focusPopupWindow() {
        if (popupWindow != null) {
            popupWindow.focus();
        }
    }

    private native PopupWindow openPopupWindow(String url, int height, int width) /*-{
        return $wnd.open(url, 'popupWindow', 'width=' + width + ',height=' + height);
    }-*/;

    protected void finish() {
        inProgress = false;
        popupWindow.close();
    }

    public void cancel() {
        finish();
    }
}

