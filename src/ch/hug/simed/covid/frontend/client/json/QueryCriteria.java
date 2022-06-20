package ch.hug.simed.covid.frontend.client.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class QueryCriteria implements Serializable {
	  private static final long serialVersionUID = 7526472295622776147L;

	  private String resource;
	  private String attribute;
	  private String id;
	  private QueryType type;
	  private QueryOperator op;
//	  private QueryCriteria[] items;
	private List<QueryCriteria> items = new ArrayList<QueryCriteria>();


	  private Float value;
	  private String value_str;

	  public QueryCriteria() { }

	  public String getResource() {
	    return this.resource;
	  }

	  public void setResource(String resource) {
	    this.resource = resource;
	  }

	  public String getAttribute() {
	    return this.attribute;
	  }

	  public void setAttribute(String attribute) {
	    this.attribute = attribute;
	  }

	  public String getId() {
	    return this.id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }

	  public QueryType getType() {
	    return this.type;
	  }

	  public void setType(QueryType type) {
	    this.type = type;
	  }

	  public QueryOperator getOp() {
	    return this.op;
	  }

	  public void setOp(QueryOperator op) {
	    this.op = op;
	  }

//	  public QueryCriteria[] getItems() {
//	    return this.items;
//	  }
//
//	  public void setItems(QueryCriteria[] items) {
//	    this.items = items;
//	  }

	  public Float getValue() {
	    return this.value;
	  }

	  public void setValue(Float value) {
	    this.value = value;
	  }

	  public String getValueStr() {
	    return this.value_str;
	  }

	  public void setValueStr(String value_str) {
	    this.value_str = value_str;
	  }

	  public String toString() {
	    StringBuilder sb = new StringBuilder();

	    sb.append("QueryCriteria>");
	    sb.append(" id:").append(this.getId());
	    sb.append(" type:").append(this.getType());
	    sb.append(" resource:").append(this.getResource());
	    sb.append(" attribute:").append(this.getAttribute());
	    sb.append(" operator:").append(this.getOp());
//	    final QueryCriteria[] items = this.getItems();
//	    if( items != null ) {
//	      sb.append("\n");
//	      for(QueryCriteria rule : items) {
//	        sb.append(rule.toString()).append("\n");
//	      }
//	    } else {
//	      sb.append(" value:").append(this.getValue());
//	      sb.append(" value_str:").append(this.getValueStr());
//	    }

	    return sb.toString();
	  }

	public List<QueryCriteria> getItems() {
		return items;
	}

	public void setItems(List<QueryCriteria> items) {
		this.items = items;
	}
	public void addToItem(QueryCriteria item)
	{

		items.add(item);
	}
	public void removeFromItem(QueryCriteria item)
	{
		items.remove(item);
	}

}

