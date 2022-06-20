package ch.hug.simed.covid.frontend.client.service;

import java.io.Serializable;

import org.fusesource.restygwt.client.Json;

public class QueryResponse implements Serializable {
	private int id;
	
    @Json(name="total row count")
    private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
