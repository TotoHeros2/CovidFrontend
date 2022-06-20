package ch.hug.simed.covid.frontend.client.service;


import java.util.ArrayList;
import java.util.List;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

//import ch.hug.simed.covid.frontend.client.MainView;
import ch.hug.simed.covid.frontend.client.MainViewWithNavBar;
import ch.hug.simed.covid.frontend.client.bean.Container;
import ch.hug.simed.covid.frontend.client.bean.Group;
import ch.hug.simed.covid.frontend.client.bean.AbstractItem;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent.TypeReceived;
import ch.hug.simed.covid.frontend.client.json.JSONItem;
import ch.hug.simed.covid.frontend.client.json.JSONQuery;
import ch.hug.simed.covid.frontend.client.json.JSONQueryCodec;
import ch.hug.simed.covid.frontend.client.json.JSONValueset;
import ch.hug.simed.covid.frontend.client.json.QueryCriteria;
import ch.hug.simed.covid.frontend.client.json.QueryCriteriaCodec;
import ch.hug.simed.covid.frontend.client.json.QueryOperator;
import ch.hug.simed.covid.frontend.client.json.QueryType;
import ch.hug.simed.covid.frontend.client.json.ValuesetCodec;
import gwt.material.design.client.base.SearchObject;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialLink;


public class CovidService {
	static private CovidService service;
	static private List<Info> infos;
	private String urlbase;
	private QueryServiceAsync serviceQuery;
	static private List< CovidResource> resources;

	private ValueSetServiceAsync serviceValueSet;
	
	
	private List<Reponse> responses;
	
	// hack to get the data from the ui
	private MainViewWithNavBar mainView;
	
	// try to create Container in service / model
	
	static private Container containerInclusion = new Container();
	static private Container containerExclusion = new Container();
	static private ArrayList<Container> containers = new ArrayList<Container>();
	
	static {
		containers.add(containerInclusion);
		containers.add(containerExclusion);		
	}
	
	
	private CovidService(){
        urlbase = GWT.getHostPageBaseURL();
        // test urlbase = "http://vmlp-00207:8080/db/app/frontend-gwt/";
        
        if (urlbase.contains("127.0.0.1") || urlbase.contains("midapi") )
        {
        	urlbase = "http://vmlp-00207:8080/";
        }
        else // http://vmlp-00207:8080/db/app/frontend-gwt/
        {
            String token[] = urlbase.split("db/app");
            urlbase = token[0];
        }
        Defaults.setServiceRoot(urlbase);
        loadInfo();
        initQueryService();
        initValueSetService();
	}
	
	private void initValueSetService() {
	       Resource resourceValueSet = new Resource(urlbase + "db/services/endpoint/2.0/valueset");
//	       Resource resourceValueSet = new Resource(urlbase + "db/services/endpoint/valueset");// test on V1
	       serviceValueSet = GWT.create(ValueSetServiceAsync.class);

	       ((RestServiceProxy) serviceValueSet).setResource(resourceValueSet);		
	}

	private void initQueryService() {
	       Resource resourceQuery = new Resource(urlbase + "db/services/endpoint/2.0/query");
	       serviceQuery = GWT.create(QueryServiceAsync.class);

	       ((RestServiceProxy) serviceQuery).setResource(resourceQuery);
		
	}

	private void loadInfo() {
        Resource resourceInfo = new Resource(urlbase + "db/services/endpoint/2.0/info");
        InfoServiceAsync serviceInfo = GWT.create(InfoServiceAsync.class);

        ((RestServiceProxy) serviceInfo).setResource(resourceInfo);
        
        serviceInfo.getInfo(new MethodCallback<List<Info>>() {
			
			@Override
			public void onSuccess(Method method, List<Info> responses) {
				// TODO Auto-generated method stub
				infos = responses;
				AppEventBus.EVENT_BUS.fireEvent(new DataReceivedEvent(TypeReceived.INFO));
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				// TODO Auto-generated method stub
				AppEventBus.EVENT_BUS.fireEvent(new DataReceivedEvent(TypeReceived.FAIL));

			}
		});		
	}

	public static CovidService get()
	{
		if (service == null)
		{
			service = new CovidService();
		}
		return service;
	}
	
	public  List<Info> getInfos()
	{
		return infos;
	}

	public void loadResources() {
		ResourceServiceAsync serviceRes = GWT.create(ResourceServiceAsync.class);

        Resource resourceRes = new Resource(urlbase + "db/services/endpoint/2.0/resources");
        ((RestServiceProxy) serviceRes).setResource(resourceRes);
        
        serviceRes.getResources(new MethodCallback<List< CovidResource>>() {
			
			@Override
			public void onSuccess(Method method, List<CovidResource> responses) {

				resources = responses;
				AppEventBus.EVENT_BUS.fireEvent(new DataReceivedEvent(TypeReceived.RESOURCES));
				
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				AppEventBus.EVENT_BUS.fireEvent(new DataReceivedEvent(TypeReceived.FAIL));
				
			}
		});		
	}

	public List<CovidResource> getResources() {
		return resources;
	}
// only for testing with one group only
	public void sentQuery(JSONQuery query)
	{
		serviceQuery.query(query, new MethodCallback<List<QueryResponse>>() {
			
			@Override
			public void onSuccess(Method method, List<QueryResponse> responses) {
				Window.alert("query response : " + responses.get(responses.size() - 1).getCount() + " patients");
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("query fail with ex : " + exception.getMessage());
			}
		});
	}
	// future version with the complete tree from the 2 containers
	// can be use directly when containers are refreshed from controller MainView
	public void sentQuery()
	{
		mainView.refreshDataFromUI();// all
		JSONQuery jsonQuery = new JSONQuery();
		for (Container container : containers)
		{
			QueryCriteria query = new QueryCriteria();
			query.setType(QueryType.TYPE_GROUP);
			query.setOp(QueryOperator.valueOf(container.getOp()));
			query.setId(container.getId());
			for (Group group : container.getGroups())
			{
				query.addToItem(group.getCriteria());
			}
			if (container.isExclusion())
				jsonQuery.setExclusion(query);
			else
				jsonQuery.setInclusion(query);
			
		}
		JSONQueryCodec codec = GWT.create(JSONQueryCodec.class);
		JSONValue jsonValue = codec.encode(jsonQuery);
		GWT.log(jsonValue.toString());
		sentQuery(jsonQuery);
		
	}
	
	// future version with the complete tree from the 2 containers
	public void sentValueSet(AbstractItem item)
	{
//		Container container = mainView.refreshDataFromUI().get(0);// for now only inclusion
////		ContainerCodec codec = GWT.create(ContainerCodec.class);
////		JSONValue jsonValue = codec.encode(container);
////		GWT.log("Test container json : " + jsonValue.toString());
//		
////		// generate json from the 2 containers and call valueset service
//
//		// for now
//		// valueset with last item
////		Group group = getGroup();
//		// not yet added
//
//		
//		// test before new version
////		if (item.getAttribute().equalsIgnoreCase("age"))
////		{
////			item.setValueStr(null);
////			item.setOp("OP_EQ");
////		}
//		// 1.04.21 tree of QueryCriteria
//		JSONValueset jsonValueset = new JSONValueset();
//		jsonValueset.setId(item.toString()); // is that a rule ?
//		QueryCriteria query = new QueryCriteria();
//		query.setType(QueryType.TYPE_GROUP);
//		query.setOp(QueryOperator.valueOf(container.getOp()));
//		query.setId(container.getId());
//		for (Group group : container.getGroups())
//		{
//			query.addToItem(group.getCriteria());
//		}
//		if (query == null)// ca doit planter qq part !!!
//		{
//			GWT.log("bug valueset");
//			return;
//			
//		}
//		jsonValueset.setQuery(query);
		
		mainView.refreshDataFromUI();// for all
		JSONValueset jsonValueset = new JSONValueset();
		jsonValueset.setId(item.toString()); // is that a rule ?
		GWT.log("Container static = " + containerInclusion.getId() + " / " + containerExclusion.getId());

		for (Container container : containers)
		{
			QueryCriteria query = new QueryCriteria();
			query.setType(QueryType.TYPE_GROUP);
			query.setOp(QueryOperator.valueOf(container.getOp()));
			query.setId(container.getId());
			for (Group group : container.getGroups())
			{
				query.addToItem(group.getCriteria());
			}
			if (container.isExclusion())
			{
				jsonValueset.setExclusion(query);
			}
			else
			{
				jsonValueset.setInclusion(query);
//				jsonValueset.setQuery(query);
			}
		}
		
		ValuesetCodec codec = GWT.create(ValuesetCodec.class);
		JSONValue jsonValue = codec.encode(jsonValueset);
		GWT.log(jsonValue.toString());
		sentValueSet(jsonValueset);
	}


	public void sentValueSet(JSONValueset valueset)
	{
		serviceValueSet.valueset(valueset, new MethodCallback<List<Reponse>>() {
			
			@Override
			public void onSuccess(Method method, List<Reponse> responses) {
//				GWT.log("recup n valeurs : " + responses.size());
//				for (Reponse response : responses)
//				{
//					GWT.log(responses.toString());
//
//				}
				CovidService.this.responses = responses;
				AppEventBus.EVENT_BUS.fireEvent(new DataReceivedEvent(TypeReceived.VALUESET,valueset.getId()));
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("valueset fail with exception : " + exception.getMessage());
				GWT.log("response : " + method.getResponse().getText());
				
			}
		});
	}
	
	// resource or load localy
	public ArrayList<SearchObject> getCriteres() {
        ArrayList<SearchObject> objects = new ArrayList<>();
        SearchObject ob = new SearchObject(IconType.ADD,"Démographie");
        objects.add(ob);
        ob = new SearchObject(IconType.ADD,"Démographie - age");
        objects.add(ob);        
        ob = new SearchObject(IconType.ADD,"Démographie - sexe");
        objects.add(ob);       
        ob = new SearchObject(IconType.ADD,"Démographie - décédé");
        objects.add(ob); 
        ob = new SearchObject(IconType.ADD,"Démographie - covid positif");
        objects.add(ob); 
        ob = new SearchObject(IconType.ADD,"Laboratoire");
        objects.add(ob);
        ob = new SearchObject(IconType.ADD,"Laboratoire - Créatinine");
        objects.add(ob);
        ob = new SearchObject(IconType.ADD,"Laboratoire - Créatinine - Sang veineux");
        objects.add(ob);
        ob = new SearchObject(IconType.ADD,"Diagnostic");
        objects.add(ob);
        ob = new SearchObject(IconType.ADD,"Diagnostic - Intolérence à la créatinine");
        objects.add(ob);
        return objects;
	}
	public ArrayList<SearchObject> getResourcesSearchObjects() {
        ArrayList<SearchObject> objects = new ArrayList<>();
        // test add test
        objects.addAll(getTestCriteria());
        SearchObject ob = null;
        for (CovidResource resource : resources)
        {
        	ob = new SearchObject(resource,"",resource.getName(), IconType.ADD);
        	objects.add(ob);
        	for (Field field: resource.getFields())
        	{
        		field.setResource(resource);
            	ob = new SearchObject(field,"",resource.getName()  + " - " + field.getName(), IconType.ADD);
            	objects.add(ob);       		
        	}
        }
        return objects; 
	}

	public List<Reponse> getResponses() {
		return responses;
	}
	
	public ArrayList<SearchObject> getTestCriteria()
	{
        ArrayList<SearchObject> objects = new ArrayList<>();

		CovidResource resource = null;
        SearchObject ob = null;
        Field field = null;
		resource = new CovidResource();
		resource.setName("root0");
    	ob = new SearchObject(resource,"",resource.getName(), IconType.ADD);        
    	objects.add(ob);    
    	
		CovidResource resourceInterm = new CovidResource();
		resourceInterm.setName("interm0_0");
    	ob = new SearchObject(resourceInterm,"","root0 - interm0_0", IconType.ADD);        
    	objects.add(ob);    	
    	resource.addResource(resourceInterm);
    	resourceInterm.setResource(resource);
    	
    	field = new Field();
    	field.setName("leaf0_0_0");
    	ob = new SearchObject(field,"","root0 - interm0_0 - leaf0_0_0", IconType.ADD);        
    	objects.add(ob); 
    	resourceInterm.addField(field);
    	field.setResource(resourceInterm);
    	
    	field = new Field();
    	field.setName("leaf0_0_1");
    	ob = new SearchObject(field,"","root0 - interm0_0 - leaf0_0_1", IconType.ADD);        
    	objects.add(ob); 
    	resourceInterm.addField(field);
    	field.setResource(resourceInterm);
   	

    	resourceInterm = new CovidResource();
    	resourceInterm.setName("interm0_1");
    	ob = new SearchObject(resourceInterm,"","root0 - interm0_1", IconType.ADD);        
    	objects.add(ob);    	
    	resource.addResource(resourceInterm);
    	resourceInterm.setResource(resource);

    	field = new Field();
    	field.setName("leaf0_1_0");
    	ob = new SearchObject(field,"","root0 - interm0_1 - leaf0_1_0", IconType.ADD);        
    	objects.add(ob); 
    	resourceInterm.addField(field);
    	field.setResource(resourceInterm);
    	
    	field = new Field();
    	field.setName("leaf0_1_1");
    	ob = new SearchObject(field,"","root0 - interm0_1 - leaf0_1_1", IconType.ADD);        
    	objects.add(ob); 
    	resourceInterm.addField(field);
    	field.setResource(resourceInterm);
   	    	
        return objects; 
       
	}

	public void setMainView(MainViewWithNavBar mainView) {
		this.mainView = mainView;
	}
	// get group from mainView
	private Group getGroup() {
		// TODO Auto-generated method stub
		Container container = mainView.getContainer();
		
		return container.getGroups().get(0);
	}
	
	public Container[] getContainers()
	{
		Container[] toReturn = new Container[2];
		toReturn[0] = containerInclusion;
		toReturn[1] = containerExclusion;		
		
		return toReturn;
	}
	
}
