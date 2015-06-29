package io.itdraft.gwt.oauth2.implicit;

import java.util.Set;

public interface AuthorizationUrlFactory {

    String buildUrl(String authorizationEndpointUrl,
                    String clientId,
                    String redirectUri,
                    Set<String> scopes,
                    String state);

}
