package com.igorkurilenko.gwt.oauth2.implicit;

public interface FlowExecutor extends FlowInitializer, FlowFinalizer {

    boolean isInProgress();

}
