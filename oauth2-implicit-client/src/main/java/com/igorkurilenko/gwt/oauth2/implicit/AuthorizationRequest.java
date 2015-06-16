package com.igorkurilenko.gwt.oauth2.implicit;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class AuthorizationRequest extends JavaScriptObject {

    protected AuthorizationRequest() {
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

    public final native int getState() /*-{
        return this.oauthState;
    }-*/;

    public final native void setState(int state) /*-{
        this.oauthState = state;
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
