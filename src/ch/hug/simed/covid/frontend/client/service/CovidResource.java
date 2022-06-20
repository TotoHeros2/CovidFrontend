package ch.hug.simed.covid.frontend.client.service;

import java.util.ArrayList;
import java.util.List;

public class CovidResource {
	private String name;
	List<Field> fields = new ArrayList<Field>();
	List<CovidResource> resources = new ArrayList<CovidResource>();

	// resource niveau sup
	private CovidResource resource;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public void addField(Field field) {
		this.fields.add(field);
	}
	
	public List<CovidResource> getResources() {
		return resources;
	}
	public void setResources(List<CovidResource> resources) {
		this.resources = resources;
	}
	
	public void addResource(CovidResource resource) {
		this.resources.add(resource);
	}

	public CovidResource getResource() {
		return resource;
	}
	public void setResource(CovidResource resource) {
		this.resource = resource;
	}
	public CovidResource getRootResource() {
		if (resource == null)
		{
			return this;
		}
		CovidResource rootResource = resource;
		while (rootResource.getResource() != null)
		{
			rootResource = rootResource.getResource();
		}
		return rootResource;
	}
	
	// for compat with current version 1 res -> n fields
//	public List<Field> getFields() {
//		List<Field> fields = new ArrayList<Field>();
//		if (resources == null)
//		{
//			return fields;
//		}
//		for (CovidResource resource : resources)
//		{
//			fields.add((Field)resource);
//		}
//
//		return fields;
//	}
	@Override
	public String toString() {
		return getName();
	}
	
	
}
