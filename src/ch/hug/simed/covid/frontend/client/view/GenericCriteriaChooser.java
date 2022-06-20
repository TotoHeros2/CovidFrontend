package ch.hug.simed.covid.frontend.client.view;

import java.util.ArrayList;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.service.CovidResource;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import ch.hug.simed.covid.frontend.client.service.Field;
import gwt.material.design.client.base.SearchObject;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialListValueBox;

public class GenericCriteriaChooser extends Composite {

	private static GenericCriteriaChooserUiBinder uiBinder = GWT.create(GenericCriteriaChooserUiBinder.class);

	interface GenericCriteriaChooserUiBinder extends UiBinder<Widget, GenericCriteriaChooser> {
	}

	SearchObject rootObject;
	SearchObject choosenObject;
	
	@UiField MaterialLabel resourceName;
	@UiField MaterialLabel selected;


//	@UiField
//	HorizontalPanel horizontalPanel;
	
	@UiField
	MaterialListValueBox<Field> listFields;
	
	@UiField
	MaterialListValueBox<CovidResource> listInterm0,listInterm1; // will be Resource
	
	
	public GenericCriteriaChooser(SearchObject rootObject, SearchObject choosenObject) {
		this.rootObject = rootObject;
		this.choosenObject = choosenObject;
		initWidget(uiBinder.createAndBindUi(this));
		resourceName.setText(((CovidResource)rootObject.getO()).getName());
		listFields.setAllowBlank(true);
		listInterm0.setAllowBlank(true);
		listInterm1.setAllowBlank(true);
		// en dur for now
 		ArrayList<SearchObject> criteria = CovidService.get().getTestCriteria();
 		CovidResource res0 = (CovidResource) criteria.get(1).getO();
 		CovidResource res1 = (CovidResource) criteria.get(4).getO();
 		ArrayList<CovidResource> interm0 = new ArrayList<CovidResource>();
 		interm0.add(res0);
 		interm0.add(res1);

 		res0.addResource((CovidResource) criteria.get(2).getO());
 		res0.addResource((CovidResource) criteria.get(3).getO());
 		
 		res1.addResource((CovidResource) criteria.get(5).getO());
 		res1.addResource((CovidResource) criteria.get(6).getO());
		listInterm0.setAcceptableValues(interm0);
		listInterm0.setValue(res1);
		listFields.setAcceptableValues(res1.getFields());	


	}
	@UiHandler("listFields")
	void onValueChange(ValueChangeEvent<Field> event) {
		Field field = event.getValue();
		if (field != null)
		selected.setText(field.getName() + " selected!");
		else
			selected.setText("No selection!");
	}
	
	
	@UiHandler("listInterm0")
	void onValueChange0(ValueChangeEvent<CovidResource> event) {
		selected.setText("No selection!");

		CovidResource resource = event.getValue();
		if (resource == null)
		{
			listFields.setAcceptableValues(new ArrayList<Field>());

		}
		else
		{
			listFields.setAcceptableValues(resource.getFields());
			
		}
	}
}
