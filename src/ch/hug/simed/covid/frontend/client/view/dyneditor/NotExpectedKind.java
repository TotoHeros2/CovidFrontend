package ch.hug.simed.covid.frontend.client.view.dyneditor;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.AbstractItem;

public class NotExpectedKind extends Composite implements TakesValue<AbstractItem> {

	private static NotExpectedKindUiBinder uiBinder = GWT.create(NotExpectedKindUiBinder.class);

	interface NotExpectedKindUiBinder extends UiBinder<Widget, NotExpectedKind> {
	}

	@UiField
	Label name;
	
	private AbstractItem item;

	public NotExpectedKind(AbstractItem item) {
		this.item = item;
		initWidget(uiBinder.createAndBindUi(this));
		name.setText(item.getAttribute());
	}

	@Override
	public void setValue(AbstractItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractItem getValue() {
		// TODO Auto-generated method stub
		return item;
	}

}
