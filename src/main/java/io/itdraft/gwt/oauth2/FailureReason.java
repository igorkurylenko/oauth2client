package io.itdraft.gwt.oauth2;

public interface FailureReason {

    RefreshTokenTimeout REFRESH_TOKEN_TIMEOUT = new RefreshTokenTimeout();
    PopupWindowClosed POPUP_WINDOW_CLOSED = new PopupWindowClosed();
    CannotOpenPopupWindow CANNOT_OPEN_POPUP_WINDOW = new CannotOpenPopupWindow();
    StateParameterMismatch STATE_PARAMETER_MISMATCH = new StateParameterMismatch();
    ResponseWithoutStateParameter RESPONSE_WITHOUT_STATE_PARAMETER =
            new ResponseWithoutStateParameter();
    UndefinedOAuth2ProviderResponse UNDEFINED_OAUTH2_PROVIDER_RESPONSE =
            new UndefinedOAuth2ProviderResponse();


    class RefreshTokenTimeout implements FailureReason {
    }

    class PopupWindowClosed implements FailureReason {
    }

    class CannotOpenPopupWindow implements FailureReason {
    }

    class ResponseWithoutStateParameter implements FailureReason {
    }

    class StateParameterMismatch implements FailureReason {
    }

    class UndefinedOAuth2ProviderResponse implements FailureReason {
    }

    class OAuth2ProviderErrorResponse implements FailureReason {
        private final String message;
        private final String messageDescription;

        public OAuth2ProviderErrorResponse(String message, String messageDescription) {
            this.message = message;
            this.messageDescription = messageDescription;
        }

        public String getMessage() {
            return message;
        }

        public String getMessageDescription() {
            return messageDescription;
        }
    }
}
