package ch.hug.simed.covid.frontend.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.Container;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationEvent;
import ch.hug.simed.covid.frontend.client.event.ButtonActivationHandler;
//import ch.hug.simed.covid.frontend.client.json.ContainerCodec;
import ch.hug.simed.covid.frontend.client.json.QueryCriteria;
import ch.hug.simed.covid.frontend.client.json.QueryCriteriaCodec;
import ch.hug.simed.covid.frontend.client.json.QueryOperator;
import ch.hug.simed.covid.frontend.client.json.QueryType;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import ch.hug.simed.covid.frontend.client.view.ContainerEditor;
import ch.hug.simed.covid.frontend.client.view.GroupEditor;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

public class MainViewWithNavBar extends Composite {

	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainViewWithNavBar> {
	}

//	@UiField VerticalPanel panelInc;	
//	@UiField MaterialButton addCrit;
//	@UiField VerticalPanel panelExc;	
//	@UiField MaterialButton addCritExc;
	
//	@UiField MaterialButton addOp,addOpExc;
	
	@UiField(provided=true)
	ContainerEditor containerInclusion;
	
	@UiField(provided=true)
	ContainerEditor containerExclusion;
	
	
	@UiField
	MaterialLink createForInclusion,deleteForInclusion;
	
	
	@UiField
	MaterialLink createForExclusion,deleteForExclusion;

	@UiField
	MaterialButton sendRequest,addGroup,removeGroup;	
	
	@UiField
	MaterialLabel hostname,version;
	
	public MainViewWithNavBar(String versionS, String hostnameS) {
//		Container containerInc = new Container();
//		Container containerExc = new Container();
		
		// try to create Container in service / model
		Container[] containers = CovidService.get().getContainers();
		Container containerInc = containers[0];
		Container containerExc = containers[1];
		containerInc.setExclusion(false);
		containerInclusion = new ContainerEditor(containerInc);
		
		containerExc.setExclusion(true);
		containerExclusion = new ContainerEditor(containerExc);
		
		initWidget(uiBinder.createAndBindUi(this));
		// hack for valuset history
		CovidService.get().setMainView(this);
        AppEventBus.EVENT_BUS.addHandler(ButtonActivationEvent.TYPE, new ButtonActivationHandler() {
			
			@Override
			public void onButtonActivation(ButtonActivationEvent event) {
				MainViewWithNavBar.this.sendRequest.setEnabled(event.isActivate());
				MainViewWithNavBar.this.addGroup.setEnabled(event.isActivate());
				MainViewWithNavBar.this.removeGroup.setEnabled(event.isActivate());
				
			}
		});
        hostname.setText("Hostname : " + hostnameS);
        version.setText("Version : " + versionS);

	}


	@UiHandler("createForInclusion")
	void onClick(ClickEvent e) {
		containerInclusion.addGroupOrOP();
		
	}
	
	@UiHandler("createForExclusion")
	void onClick2(ClickEvent e) {
		containerExclusion.addGroupOrOP();	
	}
	
	
	@UiHandler("deleteForInclusion")
	void onClickDeleteInclusion(ClickEvent e) {
		containerInclusion.removeGroup();
		
	}
	
	@UiHandler("deleteForExclusion")
	void onClickdeleteForExclusion(ClickEvent e) {
		containerExclusion.removeGroup();	
	}

	
//	@UiHandler("addCrit")
//	void onClick(ClickEvent e) {
//		panelInc.add(new Group());
//		addOp.setEnabled(true);
//		addCrit.setEnabled(false);
//		
//	}
	
//	@UiHandler("addCritExc")
//	void onClickExc(ClickEvent e) {
//		panelExc.add(new Group());
////		addOpExc.setEnabled(true);
//		addCritExc.setEnabled(false);
//	}
	
//	@UiHandler("addOp")
//	void onClickOp(ClickEvent e) {
//		panelInc.add(new Operation());
//		addCrit.setEnabled(true);
//		addOp.setEnabled(false);
//		
//	}
//	@UiHandler("addOpExc")
//	void onClickOpExc(ClickEvent e) {
//		panelExc.add(new Operation());
//		addCritExc.setEnabled(true);
////		addOpExc.setEnabled(false);
//		
//	}
	@UiHandler("sendRequest")
	void onClickSend(ClickEvent e) {
//		Container container = containerInclusion.getContainer();
//		QueryCriteria criteria = getCriteria(container);
//		ContainerCodec codec = GWT.create(ContainerCodec.class);
//		JSONValue jsonValue = codec.encode(container);
//		
//		Container containerExc = containerExclusion.getContainer();
//		JSONValue jsonValue2 = codec.encode(containerExc);
//
//		GWT.log("Inclusion = " + jsonValue.toString()  + " / Exclusion = " + jsonValue2.toString() );
		CovidService.get().sentQuery();
		
	}
	private QueryCriteria getCriteria(Container container) {
		QueryCriteria criteria = new QueryCriteria();
		criteria.setType(QueryType.TYPE_GROUP);
		criteria.setId(container.toString());
		criteria.setOp(QueryOperator.OP_ALL);
		
		QueryCriteria item = new QueryCriteria();
		item.setId("itemId");
		item.setOp(QueryOperator.OP_GTE);
		item.setResource("mv_cas");
		item.setType(QueryType.TYPE_RULE_NUM);
		item.setAttribute("age");
		item.setValue((float) 100);
		
		criteria.addToItem(item);
		
		QueryCriteriaCodec codec = GWT.create(QueryCriteriaCodec.class);
		JSONValue jsonValue = codec.encode(criteria);

		GWT.log("QueryCriteria : " + jsonValue.toString());
		return criteria;
	}


	public Container getContainer()
	{
		return containerInclusion.getContainer();
	}
	
	public void /*ArrayList<Container>*/ refreshDataFromUI()
	{
		Container container = containerInclusion.getContainer();
		Container containerExc = containerExclusion.getContainer();
		// for now only inclusion
//		ArrayList<Container> toReturn = new ArrayList<Container>();
//		toReturn.add(container);
//		toReturn.add(containerExc);
//		return toReturn;
	}
}
