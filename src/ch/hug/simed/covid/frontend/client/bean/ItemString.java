package ch.hug.simed.covid.frontend.client.bean;

import ch.hug.simed.covid.frontend.client.json.QueryCriteria;
import ch.hug.simed.covid.frontend.client.json.QueryOperator;
import ch.hug.simed.covid.frontend.client.json.QueryType;

public class ItemString extends AbstractItem {

	private String value;

	public ItemString() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getValue() {
		return value;
	}
	@Override
	public void setValue(Object value) {
		this.value = (String) value;
	}
	@Override
	public String getValueAsString() {
		// TODO Auto-generated method stub
		return value;
	}
	@Override
	public Float getValueAsFloat() {
		// TODO Auto-generated method stub
		return null;
	}
//	
//	@Override
//	public QueryCriteria getCriteria() {
//		// TODO Auto-generated method stub
//		
//		// will implement here entre/... will create a group + 2 item
//		QueryCriteria toReturn = new QueryCriteria();
//		toReturn.setAttribute(getAttribute());
//		toReturn.setResource(getResource());
//		toReturn.setId(getId());
//		toReturn.setOp(QueryOperator.valueOf(getOp()));
//		toReturn.setType(QueryType.valueOf(getType().name()));
//		toReturn.setValueStr("init".equalsIgnoreCase(getValue()) ? "": getValue());
//		
//		return toReturn;
//	}

}
