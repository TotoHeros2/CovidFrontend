package ch.hug.simed.covid.frontend.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.Container;
import ch.hug.simed.covid.frontend.client.bean.Group;
import ch.hug.simed.covid.frontend.client.bean.AbstractItem;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationEvent;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationHandler;
import ch.hug.simed.covid.frontend.client.event.DeleteCriteriumEvent;
import ch.hug.simed.covid.frontend.client.event.DeleteCriteriumHandler;
import ch.hug.simed.covid.frontend.client.event.ItemTypeChoosenEvent;
import ch.hug.simed.covid.frontend.client.event.ItemTypeChoosenHandler;
import ch.hug.simed.covid.frontend.client.resource.ResourceBundle;
import ch.hug.simed.covid.frontend.client.service.CovidResource;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import ch.hug.simed.covid.frontend.client.service.Field;
import gwt.material.design.client.MaterialDesignBase;
import gwt.material.design.client.base.SearchObject;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.incubator.client.search.InlineSearch;

public class GroupEditor extends Composite /*implements Editor<Group> */{
	
	 static {
		 MaterialDesignBase.injectCss(ResourceBundle.INSTANCE.appCss());
	 }

	private static GroupEditorUiBinder uiBinder = GWT.create(GroupEditorUiBinder.class);

	interface GroupEditorUiBinder extends UiBinder<Widget, GroupEditor> {
	}
	// remove Editor

//	public interface GroupEditorDriver extends SimpleBeanEditorDriver<Group, GroupEditor> {
//
//	}	
	@UiField VerticalPanel panel;
//	private GroupEditorDriver editorDriver;	
	
	private Group group;
	
	private List<ItemEditor> itemEditors = new ArrayList<ItemEditor>();
	
//	@UiField MaterialButton btnCancelDialog;

	@UiField MaterialDialog dialog;
	
	@UiField MaterialButton addItem;
	
	@UiField GroupOperateurEditorRB groupOp;
	
	
	@UiField(provided=true)
	InlineSearch search;
	
	boolean chooseDone = false; 

	public GroupEditor(Group group) {
		this.group = group;
		search = new InlineSearch();
		search.setMatcher(new SmartSearchMatcher());
		initWidget(uiBinder.createAndBindUi(this));
//		editorDriver = GWT.create(GroupEditorDriver.class);
//		editorDriver.initialize(this);
//		editorDriver.edit(group);
		// 2.4.1 !! GroupToggleButton 
		
// in css		dialog.setSize("" + dialog.getWidth() + "px", "900px");
		
		// get chossen object from Search comp
		// ATT tous les groupes recoivent cette notif !!!!!!!!!!
		
		// manage inline search
        search.setListSearches(CovidService.get().getResourcesSearchObjects());
        
//      search.setValue("");
        search.addCloseHandler(event -> {
//          search.setValue("");
        	search.clear();
        	dialog.close();
        	if (!chooseDone) // cancel
        		AppEventBus.EVENT_BUS.fireEvent(new ButtonActivationEvent(true));
    		chooseDone = false;

        });
      search.addSearchFinishHandler(event -> {
    	  SearchObject selectedObject = search.getSelectedObject();
    	  AppEventBus.EVENT_BUS.fireEvent(new ItemTypeChoosenEvent(selectedObject,group));
//          search.clear();
//          search.setValue("");
    	  search.clear();
    	  dialog.close();
    	  chooseDone = true;
      });
		
		
		
        AppEventBus.EVENT_BUS.addHandler(ItemTypeChoosenEvent.TYPE,new ItemTypeChoosenHandler() {
			
			@Override
			public void onItemTypeChoosen(ItemTypeChoosenEvent event) {
//				if (same group) do it
				if (event.getGroup() != group)
				{
					return;
				}
				dialog.close();
				Field field = null;
				CovidResource resource = null;
				if (event.getChosenObject().getO() instanceof Field)
				{
					field = (Field) event.getChosenObject().getO();
					resource = field.getResource();
				}
				else // for now 2 level -> resource
				{
					resource = (CovidResource) event.getChosenObject().getO();
				}
//				GWT.log(event.getChosenObject().getKeyword());
				ItemEditor itemEditor = new ItemEditor(resource,field); // add group needed for history
				itemEditors.add(itemEditor);
				panel.add(itemEditor);// will add editor	
			}
		});
        
        // delete item
        AppEventBus.EVENT_BUS.addHandler(DeleteCriteriumEvent.TYPE, new DeleteCriteriumHandler() {
			
			@Override
			public void onDeleteCriterium(DeleteCriteriumEvent event) {
				panel.remove(event.getItemEditor());
				itemEditors.remove(event.getItemEditor());
			}
		});
        AppEventBus.EVENT_BUS.addHandler(ButtonActivationEvent.TYPE, new ButtonActivationHandler() {
			
			@Override
			public void onButtonActivation(ButtonActivationEvent event) {
				GroupEditor.this.addItem.setEnabled(event.isActivate());
			}
		});
//        dialog.addCloseHandler(new CloseHandler<MaterialDialog>() {
//			
//			@Override
//			public void onClose(CloseEvent<MaterialDialog> event) {
//				search.txtSearch.clear();
//				search.txtSearch.setText("");
//				search.txtSearch.getSearchResultPanel().clear();
//
//			}
//		});
	}

	@UiHandler("addItem")
	void onClick(ClickEvent e) {
		chooseDone = false;

		// bug will be clear the next time
		search.clear();
		search.setValue("");
		AppEventBus.EVENT_BUS.fireEvent(new ButtonActivationEvent(false));
		dialog.open();
	}
	
	
//	@UiHandler("btnCancelDialog")
//	void onClickcancel(ClickEvent e) {
//		AppEventBus.EVENT_BUS.fireEvent(new ButtonActivationEvent(true));
//
//		dialog.close();
//	}

	public Group getGroup() {
		// must build from all level editors !!
//		Group group = new Group();
		// must be reinit when compute
//		group = new Group(); bug must use already created !!!!!!!
		group.setOp(groupOp.getValue());
		group.setItems(new ArrayList<AbstractItem>());
		for (ItemEditor itemEditor : itemEditors)
		{
			group.addToItem(itemEditor.getItem());
		}
		group.setOp(groupOp.getValue());
		return group;
	}
}
