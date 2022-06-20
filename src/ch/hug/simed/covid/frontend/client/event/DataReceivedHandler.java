package ch.hug.simed.covid.frontend.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DataReceivedHandler extends EventHandler {
	void onDataReceived(DataReceivedEvent event);

}
