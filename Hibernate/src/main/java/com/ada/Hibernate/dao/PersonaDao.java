package com.ada.Hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.ada.Hibernate.HibernateUtil;
import com.ada.Hibernate.dto.PersonaEntity;

public class PersonaDao {
	
	public void insertEmployee(PersonaEntity emp) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(emp);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}
	
	public List<PersonaEntity> getAllEmployee() {
		Session sesn = HibernateUtil.getSessionFactory().openSession();
		List<PersonaEntity> emplo = new ArrayList<PersonaEntity>();
		try {
			emplo = sesn.createQuery("From EmployeeEntity").list();
			for (PersonaEntity emp : emplo) {
				System.out.println(emp.getFirstName() + " " + emp.getLastName());
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}

		HibernateUtil.shutdown();
		return emplo;
	}

	public void insertPersona(PersonaEntity persona) {
		// TODO Auto-generated method stub
		
	}


}
