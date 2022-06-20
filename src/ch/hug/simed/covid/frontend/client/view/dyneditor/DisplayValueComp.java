package ch.hug.simed.covid.frontend.client.view.dyneditor;

import java.math.BigInteger;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.AbstractItem;
import ch.hug.simed.covid.frontend.client.bean.IntegerOperation;
import ch.hug.simed.covid.frontend.client.bean.RuleOp;
import ch.hug.simed.covid.frontend.client.bean.TimestampOperateur;
import gwt.material.design.client.ui.MaterialLabel;

import com.google.gwt.i18n.shared.DateTimeFormat;

public class DisplayValueComp extends Composite implements TakesValue<AbstractItem>{
	
	public static DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
	
	private static DisplayValueCompUiBinder uiBinder = GWT.create(DisplayValueCompUiBinder.class);

	interface DisplayValueCompUiBinder extends UiBinder<Widget, DisplayValueComp> {
	}
	@UiField
	MaterialLabel display;
	
	private AbstractItem item;

	public DisplayValueComp(AbstractItem item) {
		this.item = item;
		initWidget(uiBinder.createAndBindUi(this));
		IntegerOperation integerOperation = IntegerOperation.fromCode(item.getOp());
		TimestampOperateur timestampOperateur = TimestampOperateur.fromCode(item.getOp()) ;
		// not more from type enum
//		if (integerOperation != null)
//		if (item.getValue() != null && integerOperation != null)
		if (item.getValueAsFloat() != null) // for all 
		{
			// boolean
			if (item.getOp().equalsIgnoreCase(RuleOp.EQ.getCode()))
			{
				display.setText(RuleOp.EQ.getDisplayValue() + " " + (item.getValueAsFloat() == 0 ? "Non" : "Oui") );	
	
			}
			else if (integerOperation == IntegerOperation.OP_BETWEEN || integerOperation == IntegerOperation.OP_NOT_BETWEEN)
			{
				display.setText(integerOperation.getDisplayValue() + " [" + item.getValue() + " - " + item.getValueEnd() + "]");	
			}
			else 
			{
				display.setText(integerOperation.getDisplayValue() + " " + item.getValue() );	
				
			}

		}
		else // string
		{
			String operation = item.getOp();
			RuleOp op = RuleOp.fromCode(operation);
			if (op != null)
			{
				operation = op.getDisplayValue();
			}

			display.setText(operation + " " + (item.getValue() != null ?  item.getValue():  item.getValueAsString() ));
		}
	}

	@Override
	public void setValue(AbstractItem value) {
		this.item = value;
	}

	@Override
	public AbstractItem getValue() {
		// TODO Auto-generated method stub
		return item;
	}

}
