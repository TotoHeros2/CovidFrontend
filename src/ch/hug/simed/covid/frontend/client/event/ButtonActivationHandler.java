package ch.hug.simed.covid.frontend.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ButtonActivationHandler extends EventHandler {
	void onButtonActivation(ButtonActivationEvent event);
}
