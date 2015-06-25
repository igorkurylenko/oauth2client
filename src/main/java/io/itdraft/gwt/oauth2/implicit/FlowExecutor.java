package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AuthorizationRequest;
import io.itdraft.gwt.oauth2.OAuth2RequestCallback;
import io.itdraft.gwt.oauth2.AuthorizationResponse;

public abstract class FlowExecutor implements FlowInitiator, FlowFinalizer {

    private boolean inProgress = false;
    private String state;
    protected AuthUrlFactory authUrlFactory;
    protected OAuth2ResponseFactory responseFactory;

    public FlowExecutor() {
        this(new DefaultAuthUrlFactory(), new DefaultOAuth2ResponseFactory());
    }

    public FlowExecutor(AuthUrlFactory authUrlFactory, OAuth2ResponseFactory responseFactory) {
        this.authUrlFactory = authUrlFactory;
        this.responseFactory = responseFactory;
    }

    public void setAuthUrlFactory(AuthUrlFactory authUrlFactory) {
        this.authUrlFactory = authUrlFactory;
    }

    public void setResponseFactory(OAuth2ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @Override
    public void run(AuthorizationRequest request) {
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

    protected abstract void execute(AuthorizationRequest request);

    @Override
    public void finish(String uriFragment, OAuth2RequestCallback callback) {
        inProgress = false;

        processResponse(uriFragment, callback);

        finish();
    }

    private void processResponse(String uriFragment, OAuth2RequestCallback callback) {
        AuthorizationResponse response = responseFactory.create(uriFragment);

        processResponse(response, callback);
    }

    private void processResponse(AuthorizationResponse response, OAuth2RequestCallback callback) {
        try {
            validateOAuth2Response(response);

            callback.onSuccess(response);

        } catch (Exception x) {
            callback.onFailure(x);
        }
    }

    private void validateOAuth2Response(AuthorizationResponse response) {
        String stateFomResponse = response.getState();

        if (stateFomResponse == null) {
            throw new RuntimeException("OAuth2 response 'state' parameter was not provided");

        } else if (!stateFomResponse.equals(state)) {
            throw new RuntimeException("Request and response 'state' parameters mismatch");
        }
    }

    protected abstract void finish();

}
