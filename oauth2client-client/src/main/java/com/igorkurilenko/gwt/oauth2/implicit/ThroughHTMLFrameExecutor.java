package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2Request;
import com.igorkurilenko.gwt.oauth2.OAuth2RequestCallback;
import com.igorkurilenko.gwt.oauth2.OAuth2Response;

public class ThroughHTMLFrameExecutor implements FlowExecutor{
    @Override
    public boolean isInProgress() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void finalizeFlow(OAuth2Response response) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void initFlow(OAuth2Request request, OAuth2RequestCallback callback) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
