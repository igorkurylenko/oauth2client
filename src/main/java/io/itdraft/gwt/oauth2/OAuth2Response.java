package io.itdraft.gwt.oauth2;

import com.google.gwt.core.client.JavaScriptObject;

public class OAuth2Response extends JavaScriptObject {

    protected OAuth2Response() {
    }

    public final native String getAccessToken() /*-{
        return this.accessToken;
    }-*/;

    public final native void setAccessToken(String accessToken)  /*-{
        this.accessToken = accessToken;
    }-*/;

    public final native String getExpiresIn() /*-{
        return this.expiresIn;
    }-*/;

    public final native void setExpiresIn(String expiresIn) /*-{
        this.expiresIn = expiresIn;
    }-*/;

    public final native String getState() /*-{
        return this.state;
    }-*/;

    public final native void setState(String state) /*-{
        this.state = state;
    }-*/;

    public final native String getError()  /*-{
        return this.error;
    }-*/;

    public final native void setError(String error) /*-{
        this.error = error;
    }-*/;

    public final native String getErrorDescription()  /*-{
        return this.errorDescription;
    }-*/;

    public final native void setErrorDescription(String errorDescription) /*-{
        this.errorDescription = errorDescription;
    }-*/;

    public final native String getErrorUri()/*-{
        return this.errorUri;
    }-*/;

    public final native void setErrorUri(String errorUri) /*-{
        this.errorUri = errorUri;
    }-*/;
}
