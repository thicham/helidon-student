package demo.gestion.etudiant.mp.models;

import java.util.List;

public class Student {
	
	
	private String id;
	private String nom;
	private String prenom;
	private List<Marks> notes; 
	
	
	public Student(String id, String nom, String prenom) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	

	public List<Marks> getNotes() {
		return notes;
	}

	public void setNotes(List<Marks> notes) {
		this.notes = notes;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	
	
	

}
