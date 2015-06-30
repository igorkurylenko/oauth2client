package io.itdraft.gwt.oauth2.implicit;

import io.itdraft.gwt.oauth2.*;

import static io.itdraft.gwt.oauth2.implicit.Utils.isEmpty;

abstract class AccessTokenCommand {
    private static long COUNTER = 0;
    protected final String state;
    protected final OAuth2ClientConfig config;
    protected final AccessTokenCallback callback;
    protected AuthorizationUrlFactory authorizationUrlFactory;

    public AccessTokenCommand(OAuth2ClientConfig config, AccessTokenCallback callback) {
        this(config, callback,
                new DefaultRandomStateParameterGenerator(),
                new DefaultAuthorizationUrlFactory());
    }

    public AccessTokenCommand(OAuth2ClientConfig config,
                              AccessTokenCallback callback,
                              StateParameterGenerator stateParameterGenerator,
                              AuthorizationUrlFactory authorizationUrlFactory) {
        this.state = generateUniqueState(stateParameterGenerator);
        this.config = config;
        this.callback = callback;
        this.authorizationUrlFactory = authorizationUrlFactory;
    }

    public String generateUniqueState(StateParameterGenerator stateParameterGenerator) {
        return stateParameterGenerator == null ? (COUNTER++) + "" :
                (COUNTER++) + "-" + stateParameterGenerator.generateState();
    }

    public abstract void execute();

    public void processOAuth2ProviderResponse(OAuth2ProviderResponse response) {
        if (!isEmpty(response.getError())) {
            callback.onFailure(createOAuth2ProviderErrorReason(response));

        } else if(!isEmpty(response.getAccessToken())) {
            callback.onSuccess(createAccessToken(response));

        }else{
            callback.onFailure(FailureReason.UNDEFINED_OAUTH2_PROVIDER_RESPONSE);
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

    public abstract void cancel();
}
