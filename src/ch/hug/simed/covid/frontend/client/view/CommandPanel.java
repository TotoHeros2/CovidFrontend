package ch.hug.simed.covid.frontend.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.incubator.client.toggle.GroupToggleButton;

public class CommandPanel extends Composite  {

	private static CommandPanelUiBinder uiBinder = GWT.create(CommandPanelUiBinder.class);

	interface CommandPanelUiBinder extends UiBinder<Widget, CommandPanel> {
	}
//	@UiField
//	GroupToggleButton<Integer> chooseContainer;
//	
//	
//	@UiField
//	MaterialButton addOp;
	
	public CommandPanel() {
		initWidget(uiBinder.createAndBindUi(this));
//		chooseContainer.addItem("Inclusion", 1);
//		chooseContainer.addItem("Exclusion", 2);
//		chooseContainer.setActive(0);


	}

//	@UiHandler("chooseContainer")
//	void selection(SelectionEvent<Integer> e) {
//		switch (e.getSelectedItem()) {
//		case 1:
//			break;
//		case 2:
//		default:
//			break;
//		}
//
//	}
//	@UiHandler("addOp")
//	void onClick(ClickEvent e) {
//	}

	

}
