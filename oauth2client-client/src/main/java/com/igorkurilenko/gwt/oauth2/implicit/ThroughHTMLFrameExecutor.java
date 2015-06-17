package com.igorkurilenko.gwt.oauth2.implicit;

import com.google.gwt.user.client.ui.Frame;
import com.igorkurilenko.gwt.oauth2.OAuth2Request;

public class ThroughHTMLFrameExecutor extends FlowExecutor {

    private final Frame frame;

    public ThroughHTMLFrameExecutor(Frame frame) {
        this.frame = frame;
    }

    protected void execute(OAuth2Request request) {
        String url = retrieveAccessTokenUrlFactory.buildUrl(request);

        frame.setUrl(url);
    }

    @Override
    protected void finish() {
    }
}


