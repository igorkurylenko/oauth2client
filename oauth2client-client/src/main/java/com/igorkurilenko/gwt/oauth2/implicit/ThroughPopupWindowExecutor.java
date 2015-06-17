package com.igorkurilenko.gwt.oauth2.implicit;

import com.google.gwt.core.client.JavaScriptObject;
import com.igorkurilenko.gwt.oauth2.OAuth2Request;

public class ThroughPopupWindowExecutor extends FlowExecutor {

    static final class PopupWindow extends JavaScriptObject {

        protected PopupWindow() {
        }

        native boolean isClosed() /*-{
            return this.closed;
        }-*/;

        native void close() /*-{
            this.close();
        }-*/;
    }

    private static final int DEFAULT_WINDOW_WIDTH = 800;
    private static final int DEFAULT_WINDOW_HEIGHT = 600;

    private PopupWindow popupWindow;
    private int windowWidth;
    private int windowHeight;

    public ThroughPopupWindowExecutor() {
        this(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    }

    public ThroughPopupWindowExecutor(int windowWidth, int windowHeight) {
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

    protected void start(OAuth2Request request) {
        String url = buildUrl(request);

        popupWindow = openPopupWindow(url, windowHeight, windowWidth);

        if (popupWindow == null) {
            throw new RuntimeException("Failed to open popup window");
        }
    }

    private String buildUrl(OAuth2Request request) {
        throw new UnsupportedOperationException();
    }

    private native PopupWindow openPopupWindow(String url, int height, int width) /*-{
        return $wnd.open(url, 'popupWindow', 'width=' + width + ',height=' + height);
    }-*/;

    @Override
    protected void finish() {
        popupWindow.close();
    }
}

