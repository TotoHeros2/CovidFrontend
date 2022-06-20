package ch.hug.simed.covid.frontend.client.view;

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2017 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
//package gwt.material.design.client.ui;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;
import gwt.material.design.client.base.HasActive;
import gwt.material.design.client.base.HasOpenClose;
import gwt.material.design.client.base.HasSearchHandlers;
import gwt.material.design.client.base.SearchObject;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.events.SearchFinishEvent;
import gwt.material.design.client.events.SearchFinishEvent.SearchFinishHandler;
import gwt.material.design.client.events.SearchNoResultEvent;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialSearchResult;
import gwt.material.design.client.ui.MaterialValueBox;
import gwt.material.design.client.ui.html.Label;

import java.util.ArrayList;
import java.util.List;

import static gwt.material.design.jquery.client.api.JQuery.$;

//@formatter:off

/**
 * Material Search is a value box component that returns a result based on your search
 * <p>
 * <p>
 * <h3>UiBinder Usage:</h3>
 * <pre>
 * {@code
 * <m:MaterialSearch placeholder="Sample"/>
 * }
 * </pre>
 * <p>
 * <h3>Populating the search result objects</h3>
 * {@code
 * <p>
 * List<SearchObject> objects = new ArrayList<>();
 * <p>
 * private void onInitSearch() {
 * objects.add(new SearchObject(IconType.POLYMER, "Pushpin", "#!pushpin"));
 * objects.add(new SearchObject(IconType.POLYMER, "SideNavs", "#!sidenavs"));
 * objects.add(new SearchObject(IconType.POLYMER, "Scrollspy", "#!scrollspy"));
 * objects.add(new SearchObject(IconType.POLYMER, "Tabs", "#!tabs"));
 * txtSearch.setListSearches(objects);
 * }
 * <p>
 * }
 * </p>
 *
 * @author kevzlou7979
 * @author Ben Dol
 * @see <a href="http://gwtmaterialdesign.github.io/gwt-material-demo/#navbar">Material Search</a>
 * @see <a href="https://material.io/guidelines/patterns/search.html#">Material Design Specification</a>
 */
//@formatter:on
public class SmartMaterialSearch extends  MaterialSearch  {

	public SmartMaterialSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SmartMaterialSearch(String placeholder, Color backgroundColor, Color iconColor, boolean active, int shadow) {
		super(placeholder, backgroundColor, iconColor, active, shadow);
		// TODO Auto-generated constructor stub
	}

	public SmartMaterialSearch(String placeholder) {
		super(placeholder);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean match(SearchObject obj, String keyword) {
		// TODO Auto-generated method stub
//		return super.match(obj, keyword);
//		obj.getKeyword().toLowerCase().contains(keyword);
		String[] words = keyword.split(" ");    
		boolean toReturn = true;
		for ( String word : words) {
			if (!obj.getKeyword().toLowerCase().contains(word))
				toReturn = false;
		}
		return  toReturn;
	}


//	@Override
//	public void setSelectedObject(SearchObject selectedObject) {
//		selectedObject.setKeyword(" ");
//		this.getSearchResultPanel().clear();
//		super.setSelectedObject(selectedObject);
//	}
	
	
}