package io.itdraft.gwt.oauth2;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import java.util.Random;
import java.util.Set;

public class AuthorizationRequest extends JavaScriptObject {

    protected AuthorizationRequest() {
    }

    public static AuthorizationRequest create(String authEndpointUrl, String clientId,
                                              String redirectUri, Set<String> scopes) {
        String state = new Random().nextInt() + "";

        return create(authEndpointUrl, clientId, redirectUri, scopes, state);
    }

    public static AuthorizationRequest create(String authEndpointUrl, String clientId,
                                              String redirectUri, Set<String> scopes,
                                              String state) {
        AuthorizationRequest result = JavaScriptObject.createObject().cast();

        result.setAuthEndpointUrl(authEndpointUrl);
        result.setClientId(clientId);
        result.setRedirectUri(redirectUri);
        result.setScopes(setToJsArray(scopes));
        result.setState(state);

        return result;
    }

    private static JsArrayString setToJsArray(Set<String> scopes) {
        JsArrayString jsScopes = JavaScriptObject.createArray().cast();
        for (String scope : scopes) {
            jsScopes.push(scope);
        }
        return jsScopes;
    }

    public final native String getClientId() /*-{
        return this.clientId;
    }-*/;

    public final native void setClientId(String clientId) /*-{
        this.clientId = clientId;
    }-*/;

    public final native JsArrayString getScopes() /*-{
        return this.scopes;
    }-*/;

    public final native void setScopes(JsArrayString scopes) /*-{
        this.scopes = scopes;
    }-*/;

    public final native String getState() /*-{
        return this.state;
    }-*/;

    public final native void setState(String state) /*-{
        this.state = state;
    }-*/;

    public final native String getRedirectUri() /*-{
        return this.redirectUri;
    }-*/;

    public final native void setRedirectUri(String redirectUri) /*-{
        this.redirectUri = redirectUri;
    }-*/;

    public final native String getAuthEndpointUrl() /*-{
        return this.authEndpointUrl;
    }-*/;

    public final native void setAuthEndpointUrl(String authEndpointUrl) /*-{
        this.authEndpointUrl = authEndpointUrl;
    }-*/;
}
