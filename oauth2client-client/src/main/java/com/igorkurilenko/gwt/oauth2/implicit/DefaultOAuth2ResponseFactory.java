package com.igorkurilenko.gwt.oauth2.implicit;

import com.igorkurilenko.gwt.oauth2.OAuth2Response;

public class DefaultOAuth2ResponseFactory implements OAuth2ResponseFactory {
    @Override
    public OAuth2Response create(String uriFragment) {
        return Utils.uriFragmentToOAuth2Response(uriFragment);
    }
}
