package ch.hug.simed.covid.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;

import ch.hug.simed.covid.frontend.client.bean.Group;
import gwt.material.design.client.base.SearchObject;

public class ItemTypeChoosenEvent extends GwtEvent<ItemTypeChoosenHandler>{
	public static final Type<ItemTypeChoosenHandler> TYPE = new Type<ItemTypeChoosenHandler>();

	private SearchObject chosenObject; // for now. send relevant object
	
	private Group group;
	public ItemTypeChoosenEvent(SearchObject chosenObject, Group group) {
		super();
		this.chosenObject = chosenObject;
		this.setGroup(group);
	}

	public SearchObject getChosenObject() {
		return chosenObject;
	}

	public void setChosenObject(SearchObject chosenObject) {
		this.chosenObject = chosenObject;
	}

	@Override
	public Type<ItemTypeChoosenHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ItemTypeChoosenHandler handler) {
		handler.onItemTypeChoosen(this);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
