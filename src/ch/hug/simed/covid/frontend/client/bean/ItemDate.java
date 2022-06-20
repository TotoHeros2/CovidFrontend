package ch.hug.simed.covid.frontend.client.bean;

import java.util.Date;

public class ItemDate extends AbstractItem{
	
	private Date value;
	private Date valueEnd;


	@Override
	public Date getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		this.value = (Date) value;
	}

	@Override
	public Date getValueEnd() {
		// TODO Auto-generated method stub
		return valueEnd;
	}

	@Override
	public void setValueEnd(Object value) {
		// TODO Auto-generated method stub
		this.valueEnd = (Date) value;
	}
	@Override
	public String getValueAsString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Float getValueAsFloat() {
		if (value == null)
			return null;
		return Float.valueOf(value.getTime()/1000);
	}
	@Override
	public Float getValueEndAsFloat() {
		if (valueEnd == null)
			return null;
		return Float.valueOf(valueEnd.getTime()/1000);
	}
}
