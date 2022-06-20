package ch.hug.simed.covid.frontend.client.view;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
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

import ch.hug.simed.covid.frontend.client.MainViewWithNavBar;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationEvent;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationHandler;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.incubator.client.toggle.GroupToggleButton;

public class GroupOperateurEditor extends Composite  {

	private static GroupOperateurEditorUiBinder uiBinder = GWT.create(GroupOperateurEditorUiBinder.class);

	interface GroupOperateurEditorUiBinder extends UiBinder<Widget, GroupOperateurEditor> {
	}
	
	@UiField
	GroupToggleButton<String> groupToggle;
	
//	private String value = "OP_ALL";// def value

	public GroupOperateurEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		groupToggle.addItem("Ou", "OP_ANY", false);
		groupToggle.addItem("Et", "OP_ALL", true);
//		groupToggle.addItem("None", "OP_NONE",false);

		groupToggle.setActive(1);// def value
//		groupToggle.getItems().get(0).setBackgroundColor(Color.GREY_LIGHTEN_5);
////		groupToggle.getItems().get(0).setTextColor(Color.BLACK);
////		groupToggle.getItems().get(1).setTextColor(Color.BLACK);
		groupToggle.getItems().get(1).setBackgroundColor(Color.GREEN_LIGHTEN_1);
//		groupToggle.getItems().get(2).setBackgroundColor(Color.WHITE);
		groupToggle.setLayoutPosition(Position.RELATIVE);
		groupToggle.setTop(-40d); // inside group
		groupToggle.setHeight("20px");
//		groupToggle.setDepth(0);// bug above nav bar + botton broken
		
		// test desabled
        AppEventBus.EVENT_BUS.addHandler(ButtonActivationEvent.TYPE, new ButtonActivationHandler() {
			
			@Override
			public void onButtonActivation(ButtonActivationEvent event) {
				groupToggle.setEnabled(event.isActivate());
				
			}
		});
	}
	/*	
	 * 		Pour Incl/Excl	
		    position: relative;
		    top: -77px;
		    height: 20px;
		    left: 200px;
	*/	
	// use for Inclusion/exclusion only
	public void setLeft(double left)
	{
		groupToggle.setLayoutPosition(Position.RELATIVE);
		groupToggle.setDisplay(Display.INLINE);

		groupToggle.setTop(-4d);// was 77
		groupToggle.setMarginLeft(10d);
//		groupToggle.setLeft(left);		
	}
	// use for Inclusion/exclusion only
	public void setRight(double right)
	{
		groupToggle.setTop(-77d);
		groupToggle.setRight(right);		
	}	
	@UiHandler("groupToggle")
	void selection(SelectionEvent<String> e) {
		switch (e.getSelectedItem()) {
		case "OP_ANY":
//			value = "OP_ANY";
			groupToggle.getItems().get(0).setBackgroundColor(Color.GREEN_LIGHTEN_1);
			groupToggle.getItems().get(1).setBackgroundColor(Color.GREY_LIGHTEN_5);
//			groupToggle.getItems().get(2).setBackgroundColor(Color.WHITE);

			break;
		case "OP_ALL":
//			value = "OP_ALL";
			groupToggle.getItems().get(0).setBackgroundColor(Color.GREY_LIGHTEN_5);
			groupToggle.getItems().get(1).setBackgroundColor(Color.GREEN_LIGHTEN_1);
//			groupToggle.getItems().get(2).setBackgroundColor(Color.WHITE);
			break;

//		case "OP_NONE":
////			value = "OP_NONE";
//			groupToggle.getItems().get(0).setBackgroundColor(Color.WHITE);
//			groupToggle.getItems().get(1).setBackgroundColor(Color.WHITE);
//			groupToggle.getItems().get(2).setBackgroundColor(Color.GREEN_LIGHTEN_1);
//			break;
		default:
			break;
		}

	}
	
	public String getValue()
	{
		String value = groupToggle.getSingleValue();
		if (value == null)
		{
			return "OP_ALL";
		}
		return value;
//		return value;
	}

}
