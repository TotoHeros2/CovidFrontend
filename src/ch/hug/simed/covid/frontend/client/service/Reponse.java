package ch.hug.simed.covid.frontend.client.service;

import org.fusesource.restygwt.client.Json;

public class Reponse {
	
	private String nom;
	private Integer min;
	private Integer max;
	private String valeur_car;
	private String unite;
	private String sexe;
	private boolean decede;
	private String utilisateur;
	
    @Json(name="total row count")
    private int count;



	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public String getValeur_car() {
		return valeur_car;
	}

	public void setValeur_car(String valeur_car) {
		this.valeur_car = valeur_car;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public boolean isDecede() {
		return decede;
	}

	public void setDecede(boolean decede) {
		this.decede = decede;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

//	@Override
//	public String toString() {
//		return "Reponse [nom=" + nom + ", min=" + min + ", max=" + max + ", valeur_car=" + valeur_car + ", unite="
//				+ unite + ", sexe=" + sexe + ", decede=" + decede + ", utilisateur=" + utilisateur + ", count=" + count
//				+ "]";
//	}


	@Override
	public String toString() {
		return getNom();
	}
	
}
