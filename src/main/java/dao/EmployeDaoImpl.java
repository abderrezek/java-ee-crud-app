package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import models.Employe;
import utils.HibernateConfig;

public class EmployeDaoImpl implements EmployeDao {
	
	private SessionFactory sessionFactory;
	
	public EmployeDaoImpl() {
		this.sessionFactory = HibernateConfig.getFactory();
	}
	
	@Override
	public void insert(Employe emp) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// old: save()
			session.persist(emp);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) tx.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void update(Employe emp) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// old: update()
			session.merge(emp);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) tx.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public Employe findById(Long id) {
		Session session = sessionFactory.openSession();
		try {
			Employe emp = (Employe) session.get(Employe.class, id);
			return emp;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Employe> findAll(int page, int size) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();			
			Query query = session.createQuery("from Employe");
			if (page != -1 && size != -1) {
				query.setFirstResult(((page - 1) + 1) * size);
				query.setMaxResults(size);				
			}
			List<Employe> listEmps = query.list();
			tx.commit();
			return listEmps;
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	@Override
	public Long getCountResults() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();			
			String countQ = "Select count (e.id) from Employe e";
		    Query countQuery = session.createQuery(countQ);
		    Long countResults = (Long) countQuery.uniqueResult();
			return countResults;
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employe emp = this.findById(id);
			// old: delete()
			session.remove(emp);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) tx.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteAll() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Employe> listEmps = this.findAll(-1, -1);
			for (Employe emp: listEmps) {
				session.remove(emp);
			}
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) tx.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public List<Employe> search(String q) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from Employe e where "
					+ "e.nom like :q OR "
					+ "e.prenom like :q OR "
					+ "e.dateNaissance like :q OR "
					+ "e.lieuNaissance like :q";
			Query query = session.createQuery(hql);
			query.setParameter("q", "%" + q + "%");
			List<Employe> listEmps = query.list();
			tx.commit();
			return listEmps;
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

}
