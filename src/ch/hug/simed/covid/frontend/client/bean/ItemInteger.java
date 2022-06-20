package ch.hug.simed.covid.frontend.client.bean;

import ch.hug.simed.covid.frontend.client.json.QueryCriteria;
import ch.hug.simed.covid.frontend.client.json.QueryOperator;
import ch.hug.simed.covid.frontend.client.json.QueryType;

public class ItemInteger extends AbstractItem  {
	
	private Integer value;
	private Integer valueEnd;

	@Override
	public Integer getValue() {
		return value;
	}
	@Override
	public void setValue(Object value) {
		this.value = (Integer)value;
	}
	@Override
	public Integer getValueEnd() {
		return valueEnd;
	}
	@Override
	public void setValueEnd(Object valueEnd) {
		this.valueEnd = (Integer) valueEnd;
	}
	
	@Override
	public String getValueAsString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Float getValueAsFloat() {
		// TODO Auto-generated method stub
		if (value == null)
			return null;
		return value.floatValue();
	}
	@Override
	public Float getValueEndAsFloat() {
		// TODO Auto-generated method stub
		if (valueEnd == null)
			return null;
		return valueEnd.floatValue();
	}
}
