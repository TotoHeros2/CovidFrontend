package ch.hug.simed.covid.frontend.client.view;

import gwt.material.design.client.base.SearchMatcher;
import gwt.material.design.client.base.SearchObject;

public class SmartSearchMatcher implements SearchMatcher {

	@Override
	public boolean match(SearchObject obj, String keyword) {
		String[] words = keyword.split(" ");    
		boolean toReturn = true;
		for ( String word : words) {
			if (!obj.getKeyword().toLowerCase().contains(word))
				toReturn = false;
		}
		return  toReturn;
	}

}
