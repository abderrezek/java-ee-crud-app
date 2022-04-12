package dao;

import java.util.List;

import models.Employe;

public interface EmployeDao {
	
	void insert(Employe emp);
	
	void update(Employe emp);
	
	List<Employe> search(String q);
	
	Long getCountResults();
	
	Employe findById(Long id);
	
	List<Employe> findAll(int page, int size);
	
	void deleteById(Long id);
	
	void deleteAll();

}
