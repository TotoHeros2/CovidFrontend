package ch.hug.simed.covid.frontend.client.bean;

import java.util.ArrayList;
import java.util.List;

public class Container {
	
	private String id;

	private String op = "OP_ALL";

	private boolean isExclusion;
	private List<Group> groups = new ArrayList<Group>();
//	private List<String> operateurs = new ArrayList<String>();

//	private List<Operateur> operateurs;
	
	public Container() {
		setId(this.toString());
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
//	public List<Operateur> getOperateurs() {
//		return operateurs;
//	}
//	public void setOperateurs(List<Operateur> operateurs) {
//		this.operateurs = operateurs;
//	}
	
	public void addToGroup(Group group)
	{
		groups.add(group);
	}
//	public List<String> getOperateurs() {
//		return operateurs;
//	}
//	public void setOperateurs(List<String> operateurs) {
//		this.operateurs = operateurs;
//	}
	public void removeFromGroup(Group group)
	{
		groups.remove(group);
	}

//	public void addToOperateur(String operateur)
//	{
//		operateurs.add(operateur);
//	}
//	public void removeFromOperateur(String operateur)
//	{
//		operateurs.remove(operateur);
//	}
	public boolean isExclusion() {
		return isExclusion;
	}
	public void setExclusion(boolean isExclusion) {
		this.isExclusion = isExclusion;
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
	
}
