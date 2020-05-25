package demo.gestion.etudiant.mp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import demo.gestion.etudiant.mp.models.Student;


@ApplicationScoped
public class StudentService {
	private static final List<Student>  list=new ArrayList<>();
	
	static {
		list.add(new Student("1", "nom1", "prenom1"));
		list.add(new Student("2", "nom2", "prenom2"));
		list.add(new Student("3", "nom3", "prenom3"));
		list.add(new Student("4", "nom4", "prenom4"));
	}
	
	public List<Student>  getEtudiantsList(){
		return list;
	}
	
	public Optional<Student>  getEtudiant(String id){
		return list.stream().filter(e->e.getId().equalsIgnoreCase(id)).findFirst();	
	}


}
