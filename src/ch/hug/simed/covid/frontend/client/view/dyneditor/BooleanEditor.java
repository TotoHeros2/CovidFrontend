package ch.hug.simed.covid.frontend.client.view.dyneditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.ItemInteger;
import gwt.material.design.client.ui.MaterialRadioButton;

public class BooleanEditor extends Composite implements TakesValue<ItemInteger> {

	private static BooleanEditorUiBinder uiBinder = GWT.create(BooleanEditorUiBinder.class);

	interface BooleanEditorUiBinder extends UiBinder<Widget, BooleanEditor> {
	}

	@UiField
	MaterialRadioButton yesRB,noRB;
	
	private ItemInteger item;

	public BooleanEditor(ItemInteger item) {
		this.item = item;
		item.setValue(0);
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("yesRB")
	    void onRadioValue(ValueChangeEvent<Boolean> e) {
		item.setValue(1);
	}
	
	@UiHandler("noRB")
    void oNonRadioValue(ValueChangeEvent<Boolean> e) {
		item.setValue(0);
	}
	
	@Override
	public void setValue(ItemInteger value) {
//		this.item = value;		
		
	}

	@Override
	public ItemInteger getValue() {
		// TODO Auto-generated method stub
		return item;
	}


}
