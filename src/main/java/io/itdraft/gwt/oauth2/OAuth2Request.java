package io.itdraft.gwt.oauth2;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class OAuth2Request extends JavaScriptObject {

    protected OAuth2Request() {
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
