package ch.hug.simed.covid.frontend.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.Container;
import ch.hug.simed.covid.frontend.client.bean.Group;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTitle;
//import gwt.material.design.client.ui.html.Div;

public class ContainerEditor extends Composite /*implements Editor<Container>*/{

	private static ContainerEditorUiBinder uiBinder = GWT.create(ContainerEditorUiBinder.class);

	interface ContainerEditorUiBinder extends UiBinder<Widget, ContainerEditor> {
	}
// remove Editor
//	public interface ContainerEditorDriver extends SimpleBeanEditorDriver<Container, ContainerEditor> {
//
//	}
	interface MyStyle extends CssResource {
		String inclusion();
		String exclusion();
	}
	@UiField MyStyle style;
	
	
	
	@UiField VerticalPanel panel;	
//	@UiField MaterialButton addCrit;
//	@UiField MaterialButton addOp;
	
//	boolean addOp = false;
	
	
	@UiField  MaterialLabel panelTitle;
	
//	@UiField Div div;
	
	Container container;
//	public ContainerEditorDriver editorDriver;
	List<GroupEditor> groupEditors = new ArrayList<GroupEditor>();
// no more 	List<GroupOperateurEditor> operateurEditors = new ArrayList<GroupOperateurEditor>();
	// operation pour Container Incl/Excl
//	@UiField GroupOperateurEditor groupOp;
	@UiField GroupOperateurEditorRB groupOp;

	public ContainerEditor(Container container) {
		this.container = container;
		initWidget(uiBinder.createAndBindUi(this));
//		div.setHeight("10px");
		if (container.isExclusion())
		{
			panelTitle.setTitle("Exclusion");
			panelTitle.setText("Exclusion");
			panelTitle.addStyleName(style.exclusion());

		}
		else
		{
			panelTitle.setTitle("Inclusion");
			panelTitle.setText("Inclusion");
			panelTitle.addStyleName(style.inclusion());


		}
//		editorDriver = GWT.create(ContainerEditorDriver.class);
//		editorDriver.initialize(this);
//		editorDriver.edit(container);
	}
	
	public void addGroupOrOP()
	{
//		div.setVisible(false);
		// no more now group op= ALL/ANY/NONE
//		if (groupEditors.size() != 0) // not first group
//		{
//			GroupOperateurEditor operateurEditor = new GroupOperateurEditor();
//			operateurEditors.add(operateurEditor);
//			panel.add(operateurEditor);
//		}
		Group group = new Group();

		GroupEditor groupEditor = new GroupEditor(group);
		groupEditors.add(groupEditor);
		panel.add(groupEditor);// will add editor

//		if (addOp)
//		{
//			OperateurEditor operateurEditor = new OperateurEditor();
//			operateurEditors.add(operateurEditor);
//			panel.add(operateurEditor);
//			addOp = false;
//		}
//		else
//		{
//			Group group = new Group();
//
//			GroupEditor groupEditor = new GroupEditor(group);
//			groupEditors.add(groupEditor);
//			panel.add(groupEditor);// will add editor
//			addOp = true;
//		}
		
	}

//	@UiHandler("addCrit")
//	void onClick(ClickEvent e) {
//		Group group = new Group();
////		Container container = editorDriver.flush();
////		container.addToGroup(group);
////		editorDriver.edit(container);
//		GroupEditor groupEditor = new GroupEditor(group);
//		groupEditors.add(groupEditor);
//		panel.add(groupEditor);// will add editor
//		addOp.setEnabled(true);
//		addCrit.setEnabled(false);
//		
//	}
//
//	
//	@UiHandler("addOp")
//	void onClickOp(ClickEvent e) {
////		Container container = editorDriver.flush();
////		GWT.log("type container = " + container.isExclusion());
//		OperateurEditor operateurEditor = new OperateurEditor();
//		operateurEditors.add(operateurEditor);
//		panel.add(operateurEditor);
//		addCrit.setEnabled(true);
//		addOp.setEnabled(false);
//		
//	}

	public Container getContainer()
	{
//		return editorDriver.flush();
		// must build from all level editors !!
//		Container  jsonContainer = new Container();
//		jsonContainer.setExclusion(this.container.isExclusion());
		
		Container  jsonContainer = container;
		jsonContainer.setGroups(new ArrayList<Group>() );
//		jsonContainer.setOperateurs(new ArrayList<String>() );

		for (GroupEditor groupEditor : groupEditors)
		{
			jsonContainer.addToGroup(groupEditor.getGroup());
		}
		jsonContainer.setOp(groupOp.getValue());
		// no more now group op= ALL/ANY/NONE

//		for (GroupOperateurEditor operateurEditor : operateurEditors)
//		{
//			jsonContainer.addToOperateur(operateurEditor.getValue());
//		}		
		return jsonContainer;
	}

	public void removeGroup() {
		// no more now group op= ALL/ANY/NONE

//		if (operateurEditors.size() != 0) // not first group
//		{
//			GroupOperateurEditor operateurEditor = operateurEditors.get(operateurEditors.size() -1);
//			operateurEditors.remove(operateurEditor);
//			panel.remove(operateurEditor);
//		}
		if (groupEditors.size() != 0)
		{
			GroupEditor groupEditor = groupEditors.get(groupEditors.size() -1);;
			groupEditors.remove(groupEditor);
			panel.remove(groupEditor);// will add editor
		}
	}

}
