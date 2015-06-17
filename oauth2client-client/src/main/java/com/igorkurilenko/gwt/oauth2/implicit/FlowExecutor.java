package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2Request;
import com.igorkurilenko.gwt.oauth2.OAuth2RequestCallback;
import com.igorkurilenko.gwt.oauth2.OAuth2Response;

public abstract class FlowExecutor implements FlowInitiator, FlowFinalizer {

    private boolean inProgress = false;
    private String state;
    protected UrlFactory retrieveAccessTokenUrlFactory;
    protected UrlFactory refreshAccessTokenUrlFactory;

    public FlowExecutor() {
        this(new RetrieveAccessTokenUrlFactory(), new RefreshAccessTokenUrlFactory());
    }

    public FlowExecutor(UrlFactory retrieveAccessTokenUrlFactory,
                        UrlFactory refreshAccessTokenUrlFactory) {
        this.retrieveAccessTokenUrlFactory = retrieveAccessTokenUrlFactory;
        this.refreshAccessTokenUrlFactory = refreshAccessTokenUrlFactory;
    }

    public void setRetrieveAccessTokenUrlFactory(UrlFactory retrieveAccessTokenUrlFactory) {
        this.retrieveAccessTokenUrlFactory = retrieveAccessTokenUrlFactory;
    }

    public void setRefreshAccessTokenUrlFactory(UrlFactory refreshAccessTokenUrlFactory) {
        this.refreshAccessTokenUrlFactory = refreshAccessTokenUrlFactory;
    }

    @Override
    public void run(OAuth2Request request) {
        if (isInProgress()) {
            throw new IllegalStateException("Authorization is in progress");
        }

        inProgress = true;

        state = request.getState();

        execute(request);
    }

    public final boolean isInProgress() {
        return inProgress;
    }

    protected abstract void execute(OAuth2Request request);

    @Override
    public void finish(OAuth2Response response, OAuth2RequestCallback callback) {
        inProgress = false;

        processResponse(response, callback);

        finish();
    }

    private void processResponse(OAuth2Response response, OAuth2RequestCallback callback) {
        try {
            validateOAuth2Response(response);

            callback.onSuccess(response);

        } catch (Exception x) {
            callback.onFailure(x);
        }
    }

    private void validateOAuth2Response(OAuth2Response response) {
        String stateFomResponse = response.getState();

        if (stateFomResponse == null) {
            throw new RuntimeException("OAuth2 response 'state' parameter was not provided");

        } else if (!stateFomResponse.equals(state)) {
            throw new RuntimeException("Request and response 'state' parameters mismatch");
        }
    }

    protected abstract void finish();

}
