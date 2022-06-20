package ch.hug.simed.covid.frontend.client.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.OnbeforeunloadHandler;

import ch.hug.simed.covid.frontend.client.json.IsQueryCriteria;
import ch.hug.simed.covid.frontend.client.json.QueryCriteria;
import ch.hug.simed.covid.frontend.client.json.QueryOperator;
import ch.hug.simed.covid.frontend.client.json.QueryType;

// will be both group and critere (rule) as avoid polymorphism  with Editor framework
// https://groups.google.com/g/google-web-toolkit/c/lW7hw_bCXj4

//export enum RuleType { // decidera du leaf editor
//	  VarChar,
//	  Select,
//	  Number,
//	  DateTime,
//	  Boolean,
//	}

public abstract class AbstractItem implements IsQueryCriteria {
	
//	  type: RuleType; // decidera du leaf editor
	private String id;

	private ItemType type; // group / item num / str
	private String resource; // ex mv_cas
//	private RuleOp op;
//	private GroupOp opGroup; // tout dans le meme ?
	private String op;

	private String attribute; // age

	
	// values in subclasses
	
	public AbstractItem() {
		setId(this.toString());
	}

	// impl limit for saving
//	private Integer minValue;
//	private Integer maxValue;
//	
//	private Date minDate;
//	private Date maxDate;	
	
	
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public ItemType getType() {
		return type;
	}
	public void setType(ItemType type) {
		this.type = type;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
//	public RuleOp getOp() {
//		return op;
//	}
//	public void setOp(RuleOp op) {
//		this.op = op;
//	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	public void addToItem(Item item)
//	{
//		if (items == null)
//		{
//			items = new ArrayList<Item>(); 
//		}
//		items.add(item);
//	}
//	public void removeFromItem(Item item)
//	{
//		items.remove(item);
//	}

	//	public Integer getMinValue() {
//		return minValue;
//	}
//	public void setMinValue(Integer minValue) {
//		this.minValue = minValue;
//	}
//	public Integer getMaxValue() {
//		return maxValue;
//	}
//	public void setMaxValue(Integer maxValue) {
//		this.maxValue = maxValue;
//	}
//	public Date getMinDate() {
//		return minDate;
//	}
//	public void setMinDate(Date minDate) {
//		this.minDate = minDate;
//	}
//	public Date getMaxDate() {
//		return maxDate;
//	}
//	public void setMaxDate(Date maxDate) {
//		this.maxDate = maxDate;
//	}
	@Override
	public QueryCriteria getCriteria() {
		// TODO Auto-generated method stub
		
		// will implement here entre/... will create a group + 2 item
		QueryCriteria toReturn = new QueryCriteria();

//		OP_BETWEEN / OP_NOT_BETWEEN
		switch (getOp()) {
		case "OP_BETWEEN":
			toReturn.setType(QueryType.TYPE_GROUP);
			toReturn.setOp(QueryOperator.OP_ALL);
			toReturn.setId(getId() + "_virt");
			// use 2 items LGE et GTE
			QueryCriteria item1  = new QueryCriteria();
			item1.setAttribute(getAttribute());
			item1.setResource(getResource());	
			item1.setOp(QueryOperator.OP_GTE);
			item1.setType(QueryType.TYPE_RULE_NUM);
			item1.setId(getId());
			item1.setValue(getValueAsFloat());
			
			QueryCriteria item2  = new QueryCriteria();
			item2.setAttribute(getAttribute());
			item2.setResource(getResource());	
			item2.setOp(QueryOperator.OP_LTE);
			item2.setType(QueryType.TYPE_RULE_NUM);
			item2.setId(getId()+ "_2");
			item2.setValue(getValueEndAsFloat());
			

			toReturn.addToItem(item1);
			toReturn.addToItem(item2);
			
			break;
		case "OP_NOT_BETWEEN":
			toReturn.setType(QueryType.TYPE_GROUP);
			toReturn.setOp(QueryOperator.OP_ANY);	
			toReturn.setId(getId() + "_virt");

			// use 2 items LGE et GTE
			item1  = new QueryCriteria();
			item1.setAttribute(getAttribute());
			item1.setResource(getResource());	
			item1.setOp(QueryOperator.OP_LT);
			item1.setType(QueryType.TYPE_RULE_NUM);
			item1.setId(getId());
			item1.setValue(getValueAsFloat());
			
			item2  = new QueryCriteria();
			item2.setAttribute(getAttribute());
			item2.setResource(getResource());	
			item2.setOp(QueryOperator.OP_GT);
			item2.setType(QueryType.TYPE_RULE_NUM);
			item2.setId(getId()+ "_2");
			item2.setValue(getValueEndAsFloat());
			
			toReturn.addToItem(item1);
			toReturn.addToItem(item2);
			break;
// float before Entre
		case "OP_LT":
			toReturn.setOp(QueryOperator.OP_LT);
			toReturn.setAttribute(getAttribute());
			toReturn.setResource(getResource());
			toReturn.setId(getId());	
			toReturn.setType(QueryType.valueOf(getType().name()));
			toReturn.setValue(getValueAsFloat());
			break;
		case "OP_GT":
			toReturn.setOp(QueryOperator.OP_GT);
			toReturn.setAttribute(getAttribute());
			toReturn.setResource(getResource());
			toReturn.setId(getId());	
			toReturn.setType(QueryType.valueOf(getType().name()));
			toReturn.setValue(getValueAsFloat());
			break;
		case "OP_GTE":
			toReturn.setOp(QueryOperator.OP_GTE);
			toReturn.setAttribute(getAttribute());
			toReturn.setResource(getResource());
			toReturn.setId(getId());	
			toReturn.setType(QueryType.valueOf(getType().name()));
			toReturn.setValue(getValueAsFloat());
			break;
		case "OP_LTE":
			toReturn.setOp(QueryOperator.OP_LT);
			toReturn.setAttribute(getAttribute());
			toReturn.setResource(getResource());
			toReturn.setId(getId());	
			toReturn.setType(QueryType.valueOf(getType().name()));
			toReturn.setValue(getValueAsFloat());
			break;

			
		default:
			toReturn.setAttribute(getAttribute());
			toReturn.setResource(getResource());
			toReturn.setId(getId());
			if (getValueAsString() == null)
			{
				toReturn.setValue(getValueAsFloat());
			}		
			else
				toReturn.setValueStr("init".equalsIgnoreCase(getValueAsString())  ? "": getValueAsString());

			toReturn.setOp(QueryOperator.valueOf(getOp()));
			toReturn.setType(QueryType.valueOf(getType().name()));

			break;
		}
		
				
		return toReturn;
	}
// implemented by sub classes
	public Object getValue()
	{
		return null;
	}
	public void setValue(Object value)
	{
	}	
	
	public Object getValueEnd()
	{
		return null;
	}
	public void setValueEnd(Object value)
	{
	}
	
	public String getValueAsString()
	{
		return null;
	}
	public Float getValueAsFloat()
	{
		return null;
	}
	public Float getValueEndAsFloat()
	{
		return null;
	}
}
