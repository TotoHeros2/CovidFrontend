package ch.hug.simed.covid.frontend.client.view.dyneditor;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.BeanKeyValue;
import ch.hug.simed.covid.frontend.client.bean.ItemDate;
import ch.hug.simed.covid.frontend.client.bean.TimestampOperateur;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent.TypeReceived;
import ch.hug.simed.covid.frontend.client.event.DataReceivedHandler;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import ch.hug.simed.covid.frontend.client.service.Reponse;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.incubator.client.daterange.DateRangePicker;
import gwt.material.design.incubator.client.daterange.js.DateRangeLocale;
import gwt.material.design.incubator.client.daterange.js.DropdownAlignment;
import gwt.material.design.incubator.client.daterange.js.DropdownPosition;
import gwt.material.design.incubator.client.daterange.js.LocaleString;
import gwt.material.design.incubator.client.toggle.GroupToggleButton;

public class TimestampEditor extends Composite implements TakesValue<ItemDate> {

	private static TimestampEditorUiBinder uiBinder = GWT.create(TimestampEditorUiBinder.class);

	interface TimestampEditorUiBinder extends UiBinder<Widget, TimestampEditor> {
	}
//	@UiField
//	MaterialDatePicker datePicker;
	@UiField
	DateRangePicker dateRangePicker1;
	
	@UiField
	GroupToggleButton<String> dateOps;

	private ItemDate item;
	
	private boolean isCreation = true;


	public TimestampEditor(ItemDate item) {
		this.item = item;
		initWidget(uiBinder.createAndBindUi(this));
		for (BeanKeyValue operation : TimestampOperateur.list())
		{
			dateOps.addItem(operation.getDisplayValue(),operation.getCode());		
		}
//		dateOps.addItem("Entre", 1);
//		dateOps.addItem("à partir de", 2);
//		dateOps.addItem("Jusqu'à", 3);
//		dateOps.addItem("En dehors de", 4);
		dateOps.setActive(0);
		
		 DateRangeLocale locale = new DateRangeLocale();
         locale.setApplyLabel("Utiliser");
         locale.setCancelLabel("Annuler");
//         locale.setFormat("DD.MM.YYYY HH:MINUTE:SS");
         locale.setFormat("DD.MM.YYYY HH:mm:ss");
         locale.setSeparator(" à ");
         locale.setFromLabel("De");
         locale.setToLabel("A");
         locale.setCustomRangeLabel("Custom (FR)");
         locale.setWeekLabel("S");

         locale.setDaysOfWeek(new LocaleString[]{
             new LocaleString("Dim"),
             new LocaleString("Lun"),
             new LocaleString("Mar"),
             new LocaleString("Mer"),
             new LocaleString("Jeu"),
             new LocaleString("Ven"),
             new LocaleString("Sam"),
         });

         locale.setMonthNames(new LocaleString[]{
             new LocaleString("Janvier"),
             new LocaleString("Février"),
             new LocaleString("Mars"),
             new LocaleString("Avril"),
             new LocaleString("Mai"),
             new LocaleString("Juin"),
             new LocaleString("Juillet"),
             new LocaleString("Aout"),
             new LocaleString("Septembre"),
             new LocaleString("Octobre"),
             new LocaleString("Novembre"),
             new LocaleString("Décembre")
         });
         dateRangePicker1.setLocale(locale);
//         dateRangePicker2.setLocale(locale);
         dateRangePicker1.setDropdownAlignment(DropdownAlignment.CENTER);
         dateRangePicker1.setDropdownPosition(DropdownPosition.DOWN);

         dateRangePicker1.reload();
         
//         dateRangePicker2.reload();

				
//				event -> GWT.log("ValueChangeEvent : [StartDate: " + event.getValue()[0] + "], [" + event.getValue()[1] + "]"));
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
					// UI range limit
				
					// for rule checking at save time -> to remove ?
//					item.setMinValue(responce.getMin());// dont change the value
//					item.setMaxValue(responce.getMax());
					
					if (responce.getMin() == null)
						return;
					
					Date startDate = new Date(responce.getMin()*1000);
					Date endDate  = new Date(responce.getMax()*1000);
					if (isCreation) // creation
					{					

							
						// UI calendar limit
						dateRangePicker1.setMinDate(startDate);
						dateRangePicker1.setMaxDate(endDate);
						// init value calendat + item
						dateRangePicker1.setStartDate(startDate);// 
						dateRangePicker1.setEndDate(endDate);
						item.setValue(startDate);
						item.setValueEnd(endDate);
						
						// for rule checking at save time
//						item.setMinDate(startDate);
//						item.setMaxDate(endDate);
						
				        dateRangePicker1.reload();// apply calendar changes
						MaterialToast.fireToast("Les limites sont [" + responce.getMin() + " - " +  responce.getMax() + "]");

						isCreation = false;

					} 
					else // n edit
					{
						// change value calendar + item if neeeded by new limits
						boolean hasChange = false;
						// UI calendar limit
						dateRangePicker1.setMinDate(startDate);
						dateRangePicker1.setMaxDate(endDate);
						
						// for rule checking at save time
//						item.setMinDate(startDate);
//						item.setMaxDate(endDate);
						
						// change value calendar + item if neeeded by new limits
						if (item.getValue().getTime() < startDate.getTime() )
						{
							dateRangePicker1.setStartDate(startDate);// 
							item.setValue(startDate);
							hasChange = true;
						}
						else
						{
							dateRangePicker1.setStartDate(item.getValue());
						}
						if (item.getValueEnd() != null && item.getValueEnd().getTime() > endDate.getTime() )
						{
							dateRangePicker1.setEndDate(endDate);
							item.setValueEnd(endDate);
							hasChange = true;
						}
						else
						{
							dateRangePicker1.setEndDate(item.getValueEnd());
						}								
				        dateRangePicker1.reload();// apply calendar changes
						if (hasChange)
							MaterialToast.fireToast("Les limites ont été modifiés [" + responce.getMin() + " - " +  responce.getMax() + "]");
						
					}

				}
			}
		});
	}
	
	@UiHandler("dateRangePicker1")
	public void onValueChange(ValueChangeEvent<Date[]> event) {
		Date startDate =  (Date)event.getValue()[0];
		Date endDate =  (Date)event.getValue()[1];
		
		item.setValue(startDate);
		item.setValueEnd(endDate);
		

		GWT.log("ValueChangeEvent : [StartDate: " + startDate + "], [" + endDate + "]");
//		dateRangePicker.set
	}


	
	@UiHandler("dateOps")
	void selection(SelectionEvent<String> e) {
		TimestampOperateur op = TimestampOperateur.fromCode(e.getSelectedItem());
		if (op == TimestampOperateur.OP_BETWEEN || op == TimestampOperateur.OP_NOT_BETWEEN)
		{
			dateRangePicker1.setSingleDatePicker(false);
		}
		else 
		{
			dateRangePicker1.setSingleDatePicker(true);			
		}
        dateRangePicker1.reload();
		item.setOp(op.getCode());

	}
	
//	@UiHandler("dateOps")
//	void selection(SelectionEvent<Integer> e) {
//		switch (e.getSelectedItem()) {
//		case 1:
//		case 4:
//			dateRangePicker1.setSingleDatePicker(false);
////			dateRangePicker2.setVisible(true);
//			break;
//		case 2:
//		case 3:
//			
//			dateRangePicker1.setSingleDatePicker(true);
////			dateRangePicker2.setVisible(false);
//		default:
//			break;
//		}
//        dateRangePicker1.reload();
////        dateRangePicker2.reload();
//	}
	@Override
	public void setValue(ItemDate value) {
//		this.item = value;		
		
		int index = 0;
		for (BeanKeyValue operation : TimestampOperateur.list())
		{
			if (operation.getCode().equalsIgnoreCase(item.getOp()))
			{
				dateOps.setActive(index);
				
			}
			index++;
		}
	}
	@Override
	public ItemDate getValue() {
//		Date[] dates = dateRangePicker1.getValue();
//		if (dates == null)// bug was ==
//		{
////			dates[0] = dateRangePicker1.getStartDate();
////			dates[1] = dateRangePicker1.getEndDate();
//			// bidon must beg and end of the day
//			long time = new Date().getTime();
//
//			Date date1 = new Date(time);
//			CalendarUtil.resetTime(date1);
//			item.setStartDate(date1);
//
//			Date date2 = new Date(time);
//
//			CalendarUtil.resetTime(date2);
//			CalendarUtil.addDaysToDate(date2, 1);
//			item.setEndDate(date2);
//			
//		}
		

		return item;
	}



// for test we test receive 0: {min: 1514764800, max: 1618380000}
///  called also for subsequent edit as limit can change
	// will be check at save time in itemeditor

	@Override
	protected void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();
		CovidService.get().sentValueSet(item); // to do real
	}


}
