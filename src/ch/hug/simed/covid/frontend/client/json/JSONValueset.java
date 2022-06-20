package ch.hug.simed.covid.frontend.client.json;

public class JSONValueset {
	private String id; 
//	private QueryCriteria query;
	private QueryCriteria inclusion;
	private QueryCriteria exclusion;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	public QueryCriteria getQuery() {
//		return query;
//	}
//	public void setQuery(QueryCriteria query) {
//		this.query = query;
//	} 
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
