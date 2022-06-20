package ch.hug.simed.covid.frontend.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationEvent;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationHandler;
import gwt.material.design.client.ui.MaterialRadioButton;

public class GroupOperateurEditorRB extends Composite {

	private static GroupOperateurEditorRBUiBinder uiBinder = GWT.create(GroupOperateurEditorRBUiBinder.class);

	interface GroupOperateurEditorRBUiBinder extends UiBinder<Widget, GroupOperateurEditorRB> {
	}


	@UiField	
	MaterialRadioButton rbOu,rbEt;
	
	interface InlineStyle extends CssResource {
		String rbInline();
	}
	@UiField InlineStyle style;

	@UiField HTMLPanel panel;
	public GroupOperateurEditorRB() {
		initWidget(uiBinder.createAndBindUi(this));
		panel.addStyleName(style.rbInline());
		String id = HTMLPanel.createUniqueId();
		rbOu.setName("operateur" + id);
		rbEt.setName("operateur" + id);
		// test disabled
        AppEventBus.EVENT_BUS.addHandler(ButtonActivationEvent.TYPE, new ButtonActivationHandler() {
			
			@Override
			public void onButtonActivation(ButtonActivationEvent event) {
				rbOu.setEnabled(event.isActivate());
				rbEt.setEnabled(event.isActivate());
				
			}
		});

	}
	public String getValue()
	{
		if (rbEt.getValue())
		{
			return "OP_ALL";
		}
		return "OP_ANY";
	}

}
