package ch.hug.simed.covid.frontend.client.view.dyneditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.BeanKeyValue;
import ch.hug.simed.covid.frontend.client.bean.IntegerOperation;
import ch.hug.simed.covid.frontend.client.bean.ItemFloat;
import ch.hug.simed.covid.frontend.client.bean.RuleOp;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent;
import ch.hug.simed.covid.frontend.client.event.DataReceivedHandler;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent.TypeReceived;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import ch.hug.simed.covid.frontend.client.service.Reponse;
import gwt.material.design.client.ui.MaterialFloatBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.incubator.client.toggle.GroupToggleButton;

public class FloatEditor extends Composite implements TakesValue<ItemFloat> {

	private static FloatEditorUiBinder uiBinder = GWT.create(FloatEditorUiBinder.class);

	interface FloatEditorUiBinder extends UiBinder<Widget, FloatEditor> {
	}
	@UiField
	MaterialFloatBox floatBox,floatEndBox;
	
	@UiField
	GroupToggleButton<String> opsSelector;

	
	private ItemFloat item;
	
	private boolean isCreation = true;


	public FloatEditor(ItemFloat item) {
		this.item = item;
		initWidget(uiBinder.createAndBindUi(this));
		
		for (BeanKeyValue operation : IntegerOperation.list())
		{
			opsSelector.addItem(operation.getDisplayValue(),operation.getCode());
			
		}	
		opsSelector.setActive(0);
		AppEventBus.EVENT_BUS.addHandler(DataReceivedEvent.TYPE, new DataReceivedHandler() {


			@Override
			public void onDataReceived(DataReceivedEvent event) {
				// am I concerned ?
				if (!item.getId().equals(event.getItemId()))
				{
					return;
				}
				if (event.getTypeReceived() == TypeReceived.VALUESET)
				{
					Reponse responce = CovidService.get().getResponses().get(0);// min/max in first 
					// UI floatBox limit

					// for rule checking at save time -> to remove ?
//					item.setMinValue(responce.getMin());// dont change the value
//					item.setMaxValue(responce.getMax());
					
					if (responce.getMin() == null)
						return;
					if (isCreation) // creation
					{					

							

						// init floatBox value  + item
						item.setValue(responce.getMin().floatValue());
						item.setValueEnd(responce.getMax().floatValue());
						floatBox.setValue(responce.getMin().floatValue());	
						floatEndBox.setValue(responce.getMax().floatValue());

						isCreation = false;
						MaterialToast.fireToast("Les limites sont [" + responce.getMin() + " - " +  responce.getMax() + "]");

					} 
					else 
					{
						// change value calendar + item if neeeded by new limits
						boolean hasChange = false;
						if (item.getValue() < responce.getMin() )
						{
							item.setValue(responce.getMin());
							floatBox.setValue(responce.getMin().floatValue());	
							hasChange = true;
						}
						if (item.getValueEnd() != null && item.getValueEnd() > responce.getMax() )
						{
							item.setValueEnd(responce.getMax().floatValue());
							floatEndBox.setValue(responce.getMax().floatValue());
							hasChange = true;

						}	
						if (hasChange)
							MaterialToast.fireToast("Les limites ont été modifiés [" + responce.getMin() + " - " +  responce.getMax() + "]");
						
					}

				}
			}
		});
	}
	
	@UiHandler("floatBox")
	public void onChange(ChangeEvent event) {
		item.setValue(floatBox.getValue());
	}
	
	@UiHandler("floatEndBox")
	public void onChangeEnd(ChangeEvent event) {
		item.setValueEnd(floatEndBox.getValue());
	}
	
// use to reset the old op
	@Override
	public void setValue(ItemFloat value) {
//		this.item = value;		
		int index = 0;
		for (BeanKeyValue operation : IntegerOperation.list())
		{
			if (operation.getCode().equalsIgnoreCase(item.getOp()))
			{
				opsSelector.setActive(index);
				
			}
			index++;
		}	
	}

	@Override
	public ItemFloat getValue() {
		return item;
	}
	@UiHandler("opsSelector")
	void selection(SelectionEvent<String> e) {
		IntegerOperation op = IntegerOperation.fromCode(e.getSelectedItem());
		if (op == IntegerOperation.OP_BETWEEN || op == IntegerOperation.OP_NOT_BETWEEN)
		{
			floatEndBox.setVisible(true);			
		}
		else 
		{
			floatEndBox.setVisible(false);
			
		}
		item.setOp(op.getCode());

	}
	/// ATTENTION called also for subsequent edit


	@Override
	protected void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();
		CovidService.get().sentValueSet(item);
		
	}
}
