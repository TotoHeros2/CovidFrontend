package ch.hug.simed.covid.frontend.client.json;

public class JSONQuery {
	private String id; 
	
	private QueryCriteria inclusion;
	private QueryCriteria exclusion;
	
	
	public QueryCriteria getInclusion() {
		return inclusion;
	}
	public void setInclusion(QueryCriteria inclusion) {
		this.inclusion = inclusion;
	}
	public QueryCriteria getExclusion() {
		return exclusion;
	}
	public void setExclusion(QueryCriteria exclusion) {
		this.exclusion = exclusion;
	}


}
