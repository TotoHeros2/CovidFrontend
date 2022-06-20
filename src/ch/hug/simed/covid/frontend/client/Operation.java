package ch.hug.simed.covid.frontend.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class Operation extends Composite  {

	private static OperationUiBinder uiBinder = GWT.create(OperationUiBinder.class);

	interface OperationUiBinder extends UiBinder<Widget, Operation> {
	}

	public Operation() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	public Operation(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}



}
