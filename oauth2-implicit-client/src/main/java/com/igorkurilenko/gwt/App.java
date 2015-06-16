package com.igorkurilenko.gwt;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class App implements EntryPoint {

	public void onModuleLoad() {
		Label label = new Label("OAuth2 implicit flow client demo");

		RootPanel.get().add(label);
	}
}
