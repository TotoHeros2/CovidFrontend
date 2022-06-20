package ch.hug.simed.covid.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;

import ch.hug.simed.covid.frontend.client.view.ItemEditor;

public class DeleteCriteriumEvent extends GwtEvent<DeleteCriteriumHandler>{
	public static final Type<DeleteCriteriumHandler> TYPE = new Type<DeleteCriteriumHandler>();

	ItemEditor itemEditor;	
	public DeleteCriteriumEvent(ItemEditor itemEditor) {
		super();
		this.itemEditor = itemEditor;
	}

	public ItemEditor getItemEditor() {
		return itemEditor;
	}

	public void setItemEditor(ItemEditor itemEditor) {
		this.itemEditor = itemEditor;
	}

	@Override
	public Type<DeleteCriteriumHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteCriteriumHandler handler) {
		handler.onDeleteCriterium(this);
	}
}
