package ch.hug.simed.covid.frontend.client.view;

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

public class DialogAddCritere extends Composite {

	private static DialogAddCritereUiBinder uiBinder = GWT.create(DialogAddCritereUiBinder.class);

	interface DialogAddCritereUiBinder extends UiBinder<Widget, DialogAddCritere> {
	}

	public DialogAddCritere() {
		initWidget(uiBinder.createAndBindUi(this));
	}


}
