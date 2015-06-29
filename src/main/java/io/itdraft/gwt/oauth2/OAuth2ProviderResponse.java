package io.itdraft.gwt.oauth2;

public class OAuth2ProviderResponse {

    private final String accessToken;
    private final String expiresIn;
    private final String state;
    private final String error;
    private final String errorDescription;
    private final String errorUri;

    public OAuth2ProviderResponse(String accessToken,
                                  String expiresIn,
                                  String state) {
        this(accessToken, expiresIn, state, null, null, null);
    }

    public OAuth2ProviderResponse(String error,
                                  String errorDescription,
                                  String errorUri,
                                  String state) {
        this(null, null, state, error, errorDescription, errorUri);
    }

    public OAuth2ProviderResponse(String accessToken,
                                  String expiresIn,
                                  String state,
                                  String error,
                                  String errorDescription,
                                  String errorUri) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.state = state;
        this.error = error;
        this.errorDescription = errorDescription;
        this.errorUri = errorUri;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getExpiresIn() {
        return this.expiresIn;
    }

    public String getState() {
        return this.state;
    }

    public String getError() {
        return this.error;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public String getErrorUri() {
        return this.errorUri;
    }
}
