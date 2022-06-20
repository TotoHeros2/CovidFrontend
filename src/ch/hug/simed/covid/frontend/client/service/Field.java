package ch.hug.simed.covid.frontend.client.service;

public class Field {
	private String name;
	private String type;
	private String kind;
	private int size;
	
	private CovidResource resource;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public CovidResource getResource() {
		return resource;
	}
	public void setResource(CovidResource resource) {
		this.resource = resource;
	}
	@Override
	public String toString() {
		return getName();
	}



}
