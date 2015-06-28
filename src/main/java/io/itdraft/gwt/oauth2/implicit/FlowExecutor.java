package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.AccessToken;
import io.itdraft.gwt.oauth2.AccessTokenRequest;
import io.itdraft.gwt.oauth2.AccessTokenCallback;
import io.itdraft.gwt.oauth2.AuthorizationResponse;

public abstract class FlowExecutor implements FlowInitiator, FlowFinalizer {

    private boolean inProgress = false;
    private String state;
    protected AuthorizationUrlFactory authorizationUrlFactory;
    protected AuthorizationResponseFactory responseFactory;

    public FlowExecutor() {
        this(new DefaultAuthorizationUrlFactory(), new DefaultAuthorizationResponseFactory());
    }

    public FlowExecutor(AuthorizationUrlFactory authorizationUrlFactory, AuthorizationResponseFactory responseFactory) {
        this.authorizationUrlFactory = authorizationUrlFactory;
        this.responseFactory = responseFactory;
    }

    public void setAuthorizationUrlFactory(AuthorizationUrlFactory authorizationUrlFactory) {
        this.authorizationUrlFactory = authorizationUrlFactory;
    }

    public void setResponseFactory(AuthorizationResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @Override
    public void run(AccessTokenRequest request) {
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

    protected abstract void execute(AccessTokenRequest request);

    @Override
    public void finish(String uriFragment, AccessTokenCallback callback) {
        inProgress = false;

        processResponse(uriFragment, callback);

        finish();
    }

    private void processResponse(String uriFragment, AccessTokenCallback callback) {
        AuthorizationResponse response = responseFactory.create(uriFragment);

        processResponse(response, callback);
    }

    private void processResponse(AuthorizationResponse response, AccessTokenCallback callback) {
        try {
            validateOAuth2Response(response);

            callback.onSuccess(createAccessToken(response));

        } catch (Exception x) {
            callback.onFailure(x);
        }
    }

    private AccessToken createAccessToken(AuthorizationResponse response) {
        return new AccessToken(response.getAccessToken(),
                Integer.parseInt(response.getExpiresIn()));
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
