package ch.hug.simed.covid.frontend.client.view.dyneditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.BeanKeyValue;
import ch.hug.simed.covid.frontend.client.bean.IntegerOperation;
import ch.hug.simed.covid.frontend.client.bean.ItemInteger;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent.TypeReceived;
import ch.hug.simed.covid.frontend.client.event.DataReceivedHandler;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import ch.hug.simed.covid.frontend.client.service.Reponse;
import gwt.material.design.client.ui.MaterialRange;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.incubator.client.toggle.GroupToggleButton;


public class IntegerEditor extends Composite implements TakesValue<ItemInteger>{

	private static AgeEditorUiBinder uiBinder = GWT.create(AgeEditorUiBinder.class);

	interface AgeEditorUiBinder extends UiBinder<Widget, IntegerEditor> {
	}
 	@UiField MaterialRange range,range2;
 	
	@UiField
	GroupToggleButton<String> integerOps;
 	
 	private ItemInteger item;
 	
	
	private boolean isCreation = true;

 	
	public IntegerEditor(ItemInteger item) {
		this.item = item;
		initWidget(uiBinder.createAndBindUi(this));
		for (BeanKeyValue operation : IntegerOperation.list())
		{
			integerOps.addItem(operation.getDisplayValue(),operation.getCode());
			
		}
		integerOps.setActive(0);
//		range.addStyleName("zindex-1");
		
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
					
					if (responce.getMin() == null)
						return;
					// UI range limit

					range.setMin(responce.getMin());
					range.setMax(responce.getMax());
					range2.setMin(responce.getMin());
					range2.setMax(responce.getMax());					
					// for rule checking at save time -> to remove ?
//					item.setMinValue(responce.getMin());// dont change the value
//					item.setMaxValue(responce.getMax());
					
					if (isCreation) // creation
					{					

							

						// init range value  + item
						item.setValue(responce.getMin());
						item.setValueEnd(responce.getMax());
						range.setValue(responce.getMin());
						range2.setValue(responce.getMax());
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
							range.setValue(responce.getMin());	
							hasChange = true;
						}
						if (item.getValueEnd() != null && item.getValueEnd() > responce.getMax() )
						{
							item.setValueEnd(responce.getMax());
							range2.setValue(responce.getMax());
							hasChange = true;

						}	
						if (hasChange)
							MaterialToast.fireToast("Les limites ont été modifiés [" + responce.getMin() + " - " +  responce.getMax() + "]");
						
					}

				}
			}
		});
	}


	@Override
	public ItemInteger getValue() {
		// TODO Auto-generated method stub
		if (range2.isVisible())
		{
			item.setValue(range.getValue());
			item.setValueEnd(range2.getValue());// before valueMin/max
		}
		else
		{
			item.setValue(range.getValue());
		}
		return item;
	}
	
	// use to reset the old op
	@Override
	public void setValue(ItemInteger value) {
//		this.item = value;		
		int index = 0;
		for (BeanKeyValue operation : IntegerOperation.list())
		{
			if (operation.getCode().equalsIgnoreCase(item.getOp()))
			{
				integerOps.setActive(index);
				
			}
			index++;
		}
	}
	@UiHandler("integerOps")
	void selection(SelectionEvent<String> e) {
		IntegerOperation op = IntegerOperation.fromCode(e.getSelectedItem());
		if (op == IntegerOperation.OP_BETWEEN || op == IntegerOperation.OP_NOT_BETWEEN)
		{
			range2.setVisible(true);			
		}
		else 
		{
			range2.setVisible(false);
			
		}
		item.setOp(op.getCode());

	}
	  
	@UiHandler("range")
	void onRangeValue(ValueChangeEvent<Integer> e) {
		
		if (e.getValue() > range2.getValue())
		{
			range2.setValue(e.getValue());
		}
	}
	@UiHandler("range2")
	void onRange2Value(ValueChangeEvent<Integer> e) {
		
		if (e.getValue() < range.getValue())
		{
			range.setValue(e.getValue());
		}
	}
	
	
	
	/// ATTENTION called also for subsequent edit


	@Override
	protected void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();
		CovidService.get().sentValueSet(item);
		
	}
	
	

}
