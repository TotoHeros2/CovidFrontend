package ch.hug.simed.covid.frontend.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent;
import ch.hug.simed.covid.frontend.client.event.DataReceivedHandler;
import ch.hug.simed.covid.frontend.client.event.DataReceivedEvent.TypeReceived;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import gwt.material.design.client.base.SearchObject;
import gwt.material.design.incubator.client.search.InlineSearch;

public class TestInlineSearch extends Composite {

	private static TestInlineSearchUiBinder uiBinder = GWT.create(TestInlineSearchUiBinder.class);

	interface TestInlineSearchUiBinder extends UiBinder<Widget, TestInlineSearch> {
	}
	
	@UiField(provided = true)
	InlineSearch search;
	
	public TestInlineSearch() {
		search = new InlineSearch();
        search.setListSearches(CovidService.get().getResourcesSearchObjects());
        search.addCloseHandler(event -> {
            search.clear();
//            search.setActive(false);
            search.setText("");
            search.setValue("");
        });
      search.setMatcher(new SmartSearchMatcher());

		initWidget(uiBinder.createAndBindUi(this));
//		search.setListSearches(CovidService.get().getResourcesSearchObjects());    
//		AppEventBus.EVENT_BUS.addHandler(DataReceivedEvent.TYPE, new DataReceivedHandler() {
//
//			@Override
//			public void onDataReceived(DataReceivedEvent event) {
//				if (event.getTypeReceived() == TypeReceived.RESOURCES)
//				{
//					ArrayList<SearchObject> list = CovidService.get().getResourcesSearchObjects();
//					SearchObject ob = list.get(0);
//					search.getListSearches().add(ob);
////					list = new ArrayList<SearchObject>();
////					list.add(ob);
////					search.setListSearches(list);
////					list = (ArrayList<SearchObject>) list.subList(10, 20);
////					for (SearchObject ob : list)
////					{
////						search.getListSearches().add(ob);    
////					}
//				}
//				
//			}
//
//
//		});


	}


}
