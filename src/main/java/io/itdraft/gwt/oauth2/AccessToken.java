package io.itdraft.gwt.oauth2;

import com.google.gwt.http.client.URL;

import java.util.Date;
import java.util.Objects;

public class AccessToken {
    // for custom serialization
    private static final String DELIMITER = "&";

    private String token;
    private int expiresIn;
    private Date dateCreated;

    public AccessToken(String token, int expiresIn) {
        this(token, expiresIn, new Date());
    }

    private AccessToken(String token, int expiresIn, Date dateCreated) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.dateCreated = dateCreated;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getToken() {
        return token;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getExpirationDate() {
        long expirationTime = getExpirationTime();

        return new Date(expirationTime);
    }

    public long getExpirationTime() {
        return dateCreated.getTime() + expiresIn * 1000;
    }

    public boolean isExpired() {
        return getTimeLeftInSeconds() == 0;
    }

    public int getTimeLeftInSeconds() {
        long curTime = new Date().getTime();
        long timeLeftSeconds = (int) Math.max(0, (getExpirationTime() - curTime) / 1000);
        return timeLeftSeconds < Integer.MAX_VALUE ? (int) timeLeftSeconds : Integer.MAX_VALUE;
    }

    public static AccessToken deserialize(String serializedAccessToken) {
        if (serializedAccessToken == null) {
            return null;
        }

        try {
            String[] props = serializedAccessToken.split(DELIMITER);
            Date dateCreated = new Date(Long.parseLong(props[0]));
            String token = URL.decodeQueryString(props[1]);
            int expiresIn = Integer.parseInt(props[2]);

            return new AccessToken(token, expiresIn, dateCreated);

        } catch (Exception x) {
            throw AccessTokenFormatException.forInputString(serializedAccessToken);
        }
    }

    public String serialize() {
        String encodedToken = URL.encodeQueryString(token);

        return dateCreated.getTime() + DELIMITER +
                encodedToken + DELIMITER +
                expiresIn;
    }

    @Override
    public String toString() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessToken that = (AccessToken) o;
        return Objects.equals(expiresIn, that.expiresIn) &&
                Objects.equals(token, that.token) &&
                Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, expiresIn, dateCreated);
    }
}