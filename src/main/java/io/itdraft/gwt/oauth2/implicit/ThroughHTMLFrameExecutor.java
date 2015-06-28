package io.itdraft.gwt.oauth2.implicit;

import com.google.gwt.user.client.ui.Frame;
import io.itdraft.gwt.oauth2.AccessTokenRequest;

public class ThroughHTMLFrameExecutor extends FlowExecutor {

    private final Frame frame;

    public ThroughHTMLFrameExecutor(Frame frame) {
        this.frame = frame;
    }

    protected void execute(AccessTokenRequest request) {
        String url = authorizationUrlFactory.buildUrl(request);

        frame.setUrl(url);
    }

    @Override
    protected void finish() {
    }
}


