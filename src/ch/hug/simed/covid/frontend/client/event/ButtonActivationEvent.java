package ch.hug.simed.covid.frontend.client.event;

import com.google.gwt.event.shared.GwtEvent;


public class ButtonActivationEvent extends GwtEvent<ButtonActivationHandler>{
	
	public static final Type<ButtonActivationHandler> TYPE = new Type<ButtonActivationHandler>();

	
	private boolean activate;
	
	public ButtonActivationEvent(boolean activate) {
		super();
		this.activate = activate;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void dispatch(ButtonActivationHandler handler) {
		handler.onButtonActivation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ButtonActivationHandler> getAssociatedType() {
		return TYPE;
	}

	public boolean isActivate() {
		return activate;
	}


}
