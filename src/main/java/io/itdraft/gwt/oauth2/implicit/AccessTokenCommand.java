package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.*;

import static io.itdraft.gwt.oauth2.implicit.Utils.isEmpty;

public abstract class AccessTokenCommand {
    private static long COUNTER = 0;
    protected final String state;
    protected final OAuth2ClientConfig config;
    protected final AccessTokenCallback callback;
    protected AuthorizationUrlFactory authorizationUrlFactory;

    public AccessTokenCommand(OAuth2ClientConfig config, AccessTokenCallback callback) {
        this(config, callback,
                new DefaultRandomStateGenerator(),
                new DefaultAuthorizationUrlFactory());
    }

    public AccessTokenCommand(OAuth2ClientConfig config,
                              AccessTokenCallback callback,
                              StateGenerator stateGenerator,
                              AuthorizationUrlFactory authorizationUrlFactory) {
        this.state = generateUniqueState(stateGenerator);
        this.config = config;
        this.callback = callback;
        this.authorizationUrlFactory = authorizationUrlFactory;
    }

    public String generateUniqueState(StateGenerator stateGenerator) {
        return stateGenerator == null ? (COUNTER++) + "" :
                (COUNTER++) + "-" + stateGenerator.generateState();
    }

    public abstract void execute();

    public void processOAuth2ProviderResponse(OAuth2ProviderResponse response) {
        if (!isEmpty(response.getError())) {
            callback.onFailure(createOAuth2ProviderErrorReason(response));
        } else {
            callback.onSuccess(createAccessToken(response));
        }

        finish();
    }

    public FailureReason.OAuth2ProviderErrorResponse createOAuth2ProviderErrorReason(OAuth2ProviderResponse response) {
        return new FailureReason.OAuth2ProviderErrorResponse(
                response.getError(), response.getErrorDescription());
    }

    private AccessToken createAccessToken(OAuth2ProviderResponse response) {
        return new AccessToken(response.getAccessToken(),
                Integer.parseInt(response.getExpiresIn()));
    }

    public String getState() {
        return state;
    }

    public AccessTokenCallback getAccessTokenCallback() {
        return callback;
    }

    public void setAuthorizationUrlFactory(AuthorizationUrlFactory authorizationUrlFactory) {
        this.authorizationUrlFactory = authorizationUrlFactory;
    }

    protected abstract void finish();

}
