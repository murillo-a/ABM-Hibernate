package com.ada.Hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.ada.Hibernate.HibernateUtil;
import com.ada.Hibernate.dto.PersonaEntity;

public class PersonaDao {

	public void insertOrUpdate(Session session, PersonaEntity persona) {
		session.beginTransaction();
		session.saveOrUpdate(persona);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	
	public List<PersonaEntity> getPersonaList(Session session) {
		List<PersonaEntity> personalist = new ArrayList<PersonaEntity>();

		personalist = session.createQuery("From PersonaEntity").list();
		session.close();
		HibernateUtil.shutdown();
		return personalist;

	}
	
}
