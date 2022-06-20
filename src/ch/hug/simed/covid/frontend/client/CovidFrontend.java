package ch.hug.simed.covid.frontend.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.RootPanel;

import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent.TypeReceived;
import ch.hug.simed.covid.frontend.client.event.DataReceivedHandler;
import ch.hug.simed.covid.frontend.client.json.ResourceCodec;
import ch.hug.simed.covid.frontend.client.service.CovidResource;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import ch.hug.simed.covid.frontend.client.service.Field;
import ch.hug.simed.covid.frontend.client.service.Info;
import gwt.material.design.client.ui.MaterialToast;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CovidFrontend implements EntryPoint {
	
//	 static {
//       MaterialDesignBase.injectCss(ResourceBundle.INSTANCE.appCss());
//	GWT.<ResourceBundle>create(ResourceBundle.class).appCss().ensureInjected();
//	 
//	 }


//	private Label version;
//	private Label host;

	/**
	 * This is the entry point method.
	 * this code was in ApplicationView of the original maven GMD project covid-frontend
	 */
	public void onModuleLoad() {
		
        // Inject Resources
		
//		version = Label.wrap(Document.get().getElementById("version"));
//		host = Label.wrap(Document.get().getElementById("host"));
		
//		RootPanel.get().add(new MainView());
// test 
//		RootPanel.get().add(new MainViewWithNavBar());

//		RootPanel.get().add(new Label("Test multi couche"));
//		ArrayList<SearchObject> testResources = CovidService.get().getTestCriteria();
//		RootPanel.get().add(new GenericCriteriaChooser(testResources.get(0), testResources.get(4)));
		
//
//		RootPanel.get().add(new TestInlineSearch());
		
		
		CovidService service = CovidService.get();
//		testQuery();
		AppEventBus.EVENT_BUS.addHandler(DataReceivedEvent.TYPE, new DataReceivedHandler() {

			@Override
			public void onDataReceived(DataReceivedEvent event) {
				if (event.getTypeReceived() == TypeReceived.INFO)
				{
					List<Info> responses = service.getInfos();

//					version.setText(responses.get(0).getVersion());
//					host.setText(responses.get(1).getHost());
					service.loadResources();
					RootPanel.get().add(new MainViewWithNavBar(responses.get(0).getVersion(),responses.get(1).getHost()));

				}
				else if (event.getTypeReceived() == TypeReceived.RESOURCES)
				{
					List<CovidResource> resources = service.getResources();

					for (CovidResource resource :resources)
					{
						GWT.log(resource.getName());
						List<Field> fields = resource.getFields();
						//							for (Field field: fields)
						//							{
						//								GWT.log(field.getName());
						//
						//							}
						ResourceCodec codec = GWT.create(ResourceCodec.class);
						JSONValue jsonValue = codec.encode(resource);
						GWT.log(jsonValue.toString());

					}
//					Group group = new Group(); // add root group
//					RootPanel.get().add(group);
				}
				else if (event.getTypeReceived() == TypeReceived.FAIL)
				{
					MaterialToast.fireToast("Connection fail !");
//					Group group = new Group();
//					RootPanel.get().add(group);
				}
			}
		});
		//			Group group = new Group();
		//
		//			RootPanel.get().add(group);
	}
	// test json query 

	private void testQuery()
    {
//        "type":"TYPE_RULE_NUM",
//        "id":"[]rule[0]",
//        "resource":"mv_cas",
//        "attribute":"age",
//        "op":"OP_GT",
//        "value":"60"},

//		Item crit = new Item();
//		crit.setId("critAge");
//		crit.setResource("mv_cas");
//		crit.setType(ItemType.TYPE_RULE_NUM);
//		crit.setOp(RuleOp.GT);
//		crit.setValue(80);
//		
////		"type":"TYPE_GROUP",
////		"id":"[]",
////		"op":"OP_ALL",
//		
//		Item group = new Item();
//		group.setId("group0");
//		group.setType(ItemType.TYPE_GROUP);
//		group.setOp(RuleOp.ALL);
//		
//		group.addToItem(crit);
		
//		JSONGroup groupJson = new JSONGroup();
//		groupJson.setId("group0");
////		groupJson.setType(ItemType.TYPE_GROUP.TYPE_GROUP.name());
//		groupJson.setOp(RuleOp.ALL.getDisplayValue());
//		
//		JSONItem json = new JSONItem();
//		json.setId("item0");
//		json.setType(ItemType.TYPE_RULE_NUM.name());
//		json.setOp(RuleOp.GT.getDisplayValue());
//		json.setResource("mv_cas");
//		json.setAttribute("age");
//		json.setValue("80");
//
//		groupJson.addToItem(json);
//		CovidService service = CovidService.get();
//		service.sentQuery(groupJson);
		
		
	}
}
