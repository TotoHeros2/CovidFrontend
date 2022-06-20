package ch.hug.simed.covid.frontend.client.bean;

public class ItemFloat extends AbstractItem {

	private Float value;
	private Float valueEnd;

	@Override
	public Float getValue() {
		return value;
	}
	@Override
	public void setValue(Object value) {
		this.value = (Float)value;
	}
	@Override
	public Float getValueEnd() {
		return valueEnd;
	}
	@Override
	public void setValueEnd(Object valueEnd) {
		this.valueEnd = (Float) valueEnd;
	}
	
	@Override
	public String getValueAsString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Float getValueAsFloat() {
		return value;
	}
	@Override
	public Float getValueEndAsFloat() {
		return valueEnd;
	}

}
