package ch.hug.simed.covid.frontend.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.Group;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.ItemTypeChoosenEvent;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import gwt.material.design.client.base.SearchObject;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialSearch;

public class Search25 extends Composite {

	private static Search25UiBinder uiBinder = GWT.create(Search25UiBinder.class);

	interface Search25UiBinder extends UiBinder<Widget, Search25> {
	}
	// search
//	@UiField MaterialNavBar navBar,navBarSearch;
//    @UiField MaterialLink btnSearch;
//    
//    @UiField
//    MaterialSearch txtSearch;
    
    private Group group;

    
	public Search25(Group group) {
		this.group= group;
		initWidget(uiBinder.createAndBindUi(this));
//        txtSearch.setMatcher(new SmartSearchMatcher());
//
////      txtSearch.open();
////      navBar.setVisible(false);
////      navBarSearch.setVisible(true);
//      // Add Close Handler
//      txtSearch.addCloseHandler(event -> {
//          navBar.setVisible(true);
//          navBarSearch.setVisible(false);
////          MaterialToast.fireToast("Close Event was fired");
//      });
//      // Add Open Handler
//      txtSearch.addOpenHandler(openEvent -> {
//          navBar.setVisible(false);
//          navBarSearch.setVisible(true);
////          MaterialToast.fireToast("Open Event was fired");
//      });
//      txtSearch.addSearchFinishHandler(event -> {
//          // Get the selected search object
//      	SearchObject selected = (SearchObject)txtSearch.getSelectedObject();
//      	txtSearch.setText("");
//      	txtSearch.clear();
////      	card.setVisible(true);
//      	AppEventBus.EVENT_BUS.fireEvent(new ItemTypeChoosenEvent(selected,group));
//      	txtSearch.setSelectedObject(null);
//      	txtSearch.clearStatusText();
//      	txtSearch.setActive(false);
//      });      
      populateList();
	}

    private void populateList() {
//        txtSearch.setListSearches(CovidService.get().getResourcesSearchObjects());          
	}

//	@Override
//	protected void onAttach() {
//		// TODO Auto-generated method stub
//		super.onAttach();
//	      navBar.setVisible(false);
//	      navBarSearch.setVisible(true);
//	}
//    @UiHandler("btnSearch")
//    void onSearch(ClickEvent e){
//        txtSearch.open();
//    }
    
    
}
