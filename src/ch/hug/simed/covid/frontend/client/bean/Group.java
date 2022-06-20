package ch.hug.simed.covid.frontend.client.bean;

import java.util.ArrayList;
import java.util.List;

import ch.hug.simed.covid.frontend.client.json.IsQueryCriteria;
import ch.hug.simed.covid.frontend.client.json.QueryCriteria;
import ch.hug.simed.covid.frontend.client.json.QueryOperator;
import ch.hug.simed.covid.frontend.client.json.QueryType;

public class Group implements IsQueryCriteria {
	private String id;

	private String op = "OP_ALL";
	List<AbstractItem> items = new ArrayList<AbstractItem>();
	
	

	public Group() {
		setId(this.toString());
	}

	public List<AbstractItem> getItems() {
		return items;
	}

	public void setItems(List<AbstractItem> items) {
		this.items = items;
	}

	public void addToItem(AbstractItem item)
	{
		items.add(item);
	}
	public void removeFromItem(AbstractItem item)
	{
		items.remove(item);
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Override
	public boolean equals(Object o) {
		return getId().equals(((Group)o).getId());
	}
	
	@Override
	public QueryCriteria getCriteria() {
		// TODO Auto-generated method stub
		
		QueryCriteria toReturn = new QueryCriteria();

		toReturn.setId(getId());
		toReturn.setOp(QueryOperator.valueOf(getOp()));
		toReturn.setType(QueryType.TYPE_GROUP);
		for (AbstractItem item :getItems())
		{
			toReturn.addToItem(item.getCriteria());
		}
				
		return toReturn;
	}
}
