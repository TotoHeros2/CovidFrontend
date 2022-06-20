package ch.hug.simed.covid.frontend.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.hug.simed.covid.frontend.client.bean.Group;
import ch.hug.simed.covid.frontend.client.event.AppEventBus;
import ch.hug.simed.covid.frontend.client.event.ItemTypeChoosenEvent;
import ch.hug.simed.covid.frontend.client.service.CovidService;
import gwt.material.design.client.base.SearchObject;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialSearch;


public class Search extends Composite {

	private static SearchUiBinder uiBinder = GWT.create(SearchUiBinder.class);
//	private static ImageResource arrow =  ImageBundle.INSTANCE.arrow();
//	private static ImageResource etage1 =  ImageBundle.INSTANCE.etage1();
//	private static ImageResource etage2 =  ImageBundle.INSTANCE.etage2();

	interface SearchUiBinder extends UiBinder<Widget, Search> {
	}
	List<SearchObject> objects;
	

	
	@UiField
    MaterialNavBar navBar, navBarSearch;

    @UiField
    SmartMaterialSearch txtSearch;
    
    private Group group;
	
	public Search(Group group) {
		this.group = group;
		initWidget(uiBinder.createAndBindUi(this));
        
        populateList();
//        txtSearch.setListSearches(objects);

        

        txtSearch.addSearchFinishHandler(event -> {
            // Get the selected search object
        	SearchObject selected = (SearchObject)txtSearch.getSelectedObject();
        	txtSearch.setText("");
        	txtSearch.clear();
//        	card.setVisible(true);
        	AppEventBus.EVENT_BUS.fireEvent(new ItemTypeChoosenEvent(selected,group));
        	txtSearch.setSelectedObject(null);
        	txtSearch.clearStatusText();

        });
        txtSearch.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
//				GWT.log(txtSearch.getText());
			}
		});
        // Add Close Handler
        txtSearch.addCloseHandler(event -> {
//            navBar.setVisible(true);
//    		txtSearch.close();
//    		txtSearch.open();

//            navBarSearch.setVisible(false);
        	txtSearch.setText("");
        	txtSearch.clear();

//            txtSearch.setListSearches(objects);
//            MaterialToast.fireToast("Close Event was fired");
        });
        
//        txtSearch.addKeyUpHandler(new KeyUpHandler() {
//			
//			@Override
//			public void onKeyUp(KeyUpEvent event) {
//				GWT.log(txtSearch.getText());
//				GWT.log(txtSearch.getListSearches().toString());
//			}
//		});
        
        txtSearch.addOpenHandler(new OpenHandler<String>() {
			
			@Override
			public void onOpen(OpenEvent<String> event) {
	        	txtSearch.setText("");
	        	txtSearch.clear();
				
			}
		});
 
	}
	
    private void populateList() {
        txtSearch.setListSearches(CovidService.get().getResourcesSearchObjects());          
	}

    
//	@UiHandler("btnSearch")
//	 void onSearch(ClickEvent e){
//		txtSearch.open();
//	 }
}
