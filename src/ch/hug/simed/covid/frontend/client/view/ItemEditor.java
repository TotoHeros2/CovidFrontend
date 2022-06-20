package ch.hug.simed.covid.frontend.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

//import ch.hug.simed.covid.frontend.client.MainView;
import ch.hug.simed.covid.frontend.client.Operation;
import ch.hug.simed.covid.frontend.client.bean.Group;
import ch.hug.simed.covid.frontend.client.bean.IntegerOperation;
import ch.hug.simed.covid.frontend.client.bean.ItemDate;
import ch.hug.simed.covid.frontend.client.bean.ItemFloat;
import ch.hug.simed.covid.frontend.client.bean.ItemInteger;
import ch.hug.simed.covid.frontend.client.bean.ItemString;
import ch.hug.simed.covid.frontend.client.bean.AbstractItem;
import ch.hug.simed.covid.frontend.client.bean.ItemType;
import ch.hug.simed.covid.frontend.client.bean.RuleOp;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationEvent;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationHandler;
import ch.hug.simed.covid.frontend.client.event.DeleteCriteriumEvent;
import ch.hug.simed.covid.frontend.client.json.JSONItem;
import ch.hug.simed.covid.frontend.client.json.JSONValueset;
import ch.hug.simed.covid.frontend.client.service.CovidResource;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import ch.hug.simed.covid.frontend.client.service.Field;
import ch.hug.simed.covid.frontend.client.view.dyneditor.BooleanEditor;
import ch.hug.simed.covid.frontend.client.view.dyneditor.DisplayValueComp;
import ch.hug.simed.covid.frontend.client.view.dyneditor.FloatEditor;
import ch.hug.simed.covid.frontend.client.view.dyneditor.IntegerEditor;
import ch.hug.simed.covid.frontend.client.view.dyneditor.NotExpectedKind;
import ch.hug.simed.covid.frontend.client.view.dyneditor.TimestampEditor;
import ch.hug.simed.covid.frontend.client.view.dyneditor.ValueSetEditor;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialListValueBox;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;

// remove Editor

public class ItemEditor extends Composite /*implements Editor<Item> */{

	private static ItemEditorUiBinder uiBinder = GWT.create(ItemEditorUiBinder.class);

	interface ItemEditorUiBinder extends UiBinder<Widget, ItemEditor> {
	}
//	public interface ItemEditorDriver extends SimpleBeanEditorDriver<Item, ItemEditor> {
//
//	}	
	
	@UiField
	MaterialListValueBox<Field> listFields;
	
	@UiField
	MaterialListValueBox<CovidResource> listInterm0;
	
//	ItemEditorDriver editorDriver;
//	private Item item;
	
	@UiField MaterialLabel resourceName;
	
	
	@UiField SimplePanel dynamicPanel;
	
	@UiField MaterialButton saveButton;
	
	
	@UiField MaterialColumn columnInterm0;
	
	
	private boolean isReadOnly = false;
	
	// for now only one level
	private int nbOfIntermediateLevel;
	
	CovidResource rootResource;
	
	// last selected resource 
	CovidResource resource;
	
	@UiField MaterialLink editButton,deleteButton;
	

	private TakesValue<AbstractItem> lastEditor;

	

	public ItemEditor(CovidResource resource, Field field) {
		this.resource = resource;

//		this.item = item;
		initWidget(uiBinder.createAndBindUi(this));
//		editorDriver = GWT.create(ItemEditorDriver.class);
//		editorDriver.initialize(this);
//		editorDriver.edit(item);
		listFields.setAllowBlank(true);
		listInterm0.setAllowBlank(true);

		rootResource = resource.getRootResource();
		setNbOfLevel();//nb of popup to create. fixed today but wil dynamic
		resourceName.setText(rootResource.getName());
//		ArrayList<Field> list = new ArrayList<Field>();
		if (field != null)
		{
			listFields.setAcceptableValues(resource.getFields());	
			listFields.setValue(field);
			if (nbOfIntermediateLevel != 0) // just one for now
			{
				columnInterm0.setVisible(true);
				listInterm0.setAcceptableValues(rootResource.getResources());
				listInterm0.setValue(resource);
			}
			showDynamicEditor(field);
		}
		else // choix resource
		{
			if (nbOfIntermediateLevel == 0) // juste root
			{
				listFields.setAcceptableValues(resource.getFields());	
			}
			else // interm
			{
				columnInterm0.setVisible(true);
				listInterm0.setAcceptableValues(rootResource.getResources());
				listInterm0.setValue(resource);
				listFields.setAcceptableValues(resource.getFields());	

			}
		}
        AppEventBus.EVENT_BUS.addHandler(ButtonActivationEvent.TYPE, new ButtonActivationHandler() {
			
			@Override
			public void onButtonActivation(ButtonActivationEvent event) {
				ItemEditor.this.editButton.setEnabled(event.isActivate());
				ItemEditor.this.deleteButton.setEnabled(event.isActivate());
				
			}
		});
	}
	
	@UiHandler("saveButton")
	 void onSave(ClickEvent e){
		// avoid to save null value
		AbstractItem item = getItem();
		if (item.getValue() == null && item.getValueEnd() == null)
		{
			return; 
		}
//		if (item.getStartDate() != null)
//		{
//			if (item.getStartDate().getTime() <  item.getMinDate().getTime() )
//			{
//				Window.alert("Le critère ne peut pas être sauvegarder car non compris dans l'intervalle = [" + DisplayValueComp.dateTimeFormat.format(item.getMinDate()) +" - " +  DisplayValueComp.dateTimeFormat.format(item.getMaxDate()) + "]"); 
//				return;
//			}
//
//		}
//		if (item.getEndDate() != null)
//		{
//			if (item.getEndDate().getTime() >  item.getMaxDate().getTime() )
//			{
//				Window.alert("Le critère ne peut pas être sauvegarder car non compris dans l'intervalle = [" + DisplayValueComp.dateTimeFormat.format(item.getMinDate()) +" - " +  DisplayValueComp.dateTimeFormat.format(item.getMaxDate()) + "]"); 
//				return;
//			}
//		}
		
		isReadOnly = true;
		AppEventBus.EVENT_BUS.fireEvent(new ButtonActivationEvent(true));

		updateUI();		
	}
	
	@UiHandler("deleteButton")
	 void onDelete(ClickEvent e){
		AppEventBus.EVENT_BUS.fireEvent(new DeleteCriteriumEvent(this));
	}
	
	
	@UiHandler("editButton")
	 void onEdit(ClickEvent e){
		AppEventBus.EVENT_BUS.fireEvent(new ButtonActivationEvent(false));

		dynamicPanel.setWidget((IsWidget) lastEditor);
		lastEditor.setValue(null);// Bug :  use to update op
		editButton.setVisible(false);
		deleteButton.setVisible(false);

		saveButton.setVisible(true);
		
	}
	
	@UiHandler("listFields")
	void onValueChange(ValueChangeEvent<Field> event) {
		Field field = event.getValue();
		showDynamicEditor(field);
		if (field != null)
		{
//		 sentValueSet(field);
			showDynamicEditor(field);
		}
	}
	
	@UiHandler("listInterm0")
	void onValueChangeInterm(ValueChangeEvent<CovidResource> event) {
		CovidResource resource = event.getValue();
		showDynamicEditor(null);

		if (resource == null)
		{
			listFields.setAcceptableValues(new ArrayList<Field>());
		}
		else
		{
			listFields.setAcceptableValues(resource.getFields());		
		}
	}

	// for now only one level
	private void setNbOfLevel() {
		nbOfIntermediateLevel = 0;
		if (rootResource.getFields().size() == 0) // at least an interm
		{
			nbOfIntermediateLevel = 1; 
		}
	}



	protected void updateUI() {
		if (isReadOnly)
		{
			AbstractItem item = getItem();
			saveButton.setVisible(false);
			resourceName.setText(rootResource.getName() + " : " + (nbOfIntermediateLevel == 0 ? "" : listInterm0.getValue().getName() + " : ") + listFields.getValue().getName());
			listFields.setVisible(false);
			listInterm0.setVisible(false);

			dynamicPanel.clear();
			dynamicPanel.setWidget(new DisplayValueComp(item));

			editButton.setVisible(true);
			deleteButton.setVisible(true);
		}
		else
		{
			if (nbOfIntermediateLevel == 0)
			{
				listInterm0.setVisible(false);
			}
			else
			{
				listInterm0.setVisible(true);
			}				
			saveButton.setVisible(true);
			editButton.setVisible(false);
			deleteButton.setVisible(false);

		}
	}



	public AbstractItem getItem() {
//		return item;
		return ((TakesValue<AbstractItem>)dynamicPanel.getWidget()).getValue();
//		return getUIValues();
	}
	
//	private Item getUIValues()
//	{
//		Item item = new Item();
//		item.setResource(resourceName.getTitle());
//		item.setAttribute(listFields.getSelectedItemText());
////		if (dynamicPanel.getWidget() instanceof IntegerEditor)
////		{
////			item.setValue(((IntegerEditor)dynamicPanel.getWidget()).getValue());
////		}
////		else
//		if (dynamicPanel.getWidget() instanceof ValueSetEditor)
//		{
//			item.setValueStr(((ValueSetEditor)dynamicPanel.getWidget()).getValue());
//		}
////		item.setId(id.getText());
//		return item;
//	}
	protected void showDynamicEditor(Field field) {
		dynamicPanel.clear();
		if (field == null)
		{
			saveButton.setVisible(false);
			return;
		}
		AbstractItem item = null;

		switch (field.getKind()) {
		case "integer":
			item = new ItemInteger();
			item.setOp(IntegerOperation.OP_BETWEEN.getCode());
			item.setType(ItemType.TYPE_RULE_NUM);
			dynamicPanel.setWidget(new IntegerEditor((ItemInteger)item));
			break;
		case "varchar":
			item = new ItemString();
			item.setType(ItemType.TYPE_RULE_STR);
// no more autocomplete			dynamicPanel.setWidget(new VarcharEditor(item));
			dynamicPanel.setWidget(new ValueSetEditor((ItemString)item,field.getName()));
			((ItemString)item).setValue("init"); // init
			// for now
//			sentValueSetOld(field);
			break;
		case "boolean":
			item = new ItemInteger();
			item.setType(ItemType.TYPE_RULE_NUM);
			dynamicPanel.setWidget(new BooleanEditor((ItemInteger)item));
			break;	
		case "timestamp":
			item = new ItemDate();
			item.setOp(IntegerOperation.OP_BETWEEN.getCode());
			item.setValue(new Date(0));
			item.setValueEnd(new Date(0));

			item.setType(ItemType.TYPE_RULE_NUM);
			dynamicPanel.setWidget(new TimestampEditor((ItemDate)item));
			break;				
		case "float":
			item = new ItemFloat();
			item.setOp(IntegerOperation.OP_BETWEEN.getCode());
			item.setType(ItemType.TYPE_RULE_NUM);// was bug
			item.setValue(0.1f);
			item.setValueEnd(0.2f);

			dynamicPanel.setWidget(new FloatEditor((ItemFloat)item));
			break;			
		default:
			// null
			item = new ItemString();

			if ("valueset".equalsIgnoreCase(field.getType()))
			{
				item.setType(ItemType.TYPE_RULE_STR);
//				item.setValue(null);
				item.setValue("init"); // init
//				group.addToItem(item);// history
				dynamicPanel.setWidget(new ValueSetEditor((ItemString)item));
				// for now
				//sentValueSetOld(field); now in ValueSetEditor
			}
			else
			{
				item.setType(ItemType.TYPE_RULE_STR);
				item.setValue("bidon"); // init

				dynamicPanel.setWidget(new NotExpectedKind((ItemString)item));
			}
			break;
		}
		item.setResource(field.getResource().getName());	
		item.setAttribute(field.getName());
//		item.setId(String.valueOf(item.hashCode()));// in constructor
		if (item.getOp() == null)
			item.setOp(RuleOp.EQ.getCode());// for now
		
		
//to be impl		sentValueSet();// all times. Editors will use it or not
		// try to save last editor
		lastEditor = (TakesValue<AbstractItem>)dynamicPanel.getWidget(); 
		dynamicPanel.setVisible(true);	
		saveButton.setVisible(true);
	
	}
	// TODO
	// must be at least rewritten with group and item for history
	private void sentValueSet() {
		CovidService.get().sentValueSet(getItem());
	}
	



	public boolean isReadOnly() {
		return isReadOnly;
	}



	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
}
