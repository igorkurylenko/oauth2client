package io.itdraft.gwt.oauth2;

public class AccessTokenFormatException extends IllegalArgumentException {
    static final long serialVersionUID = -2848938806368998894L;

    public AccessTokenFormatException () {
        super();
    }

    public AccessTokenFormatException (String s) {
        super (s);
    }

    static AccessTokenFormatException forInputString(String s) {
        return new AccessTokenFormatException("For input string: \"" + s + "\"");
    }
}
