package com.ada.Hibernate;

import org.hibernate.Session;

import com.ada.Hibernate.dto.*;
import com.ada.Hibernate.dao.*;

public class App {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// Add new employee object
		PersonaEntity persona = new PersonaEntity();
		persona.setEmail("demo-user@mail.com");
		persona.setFirstName("demo");
		persona.setLastName("user");

		PersonaDao eDao = new PersonaDao();
		eDao.insertPersona(persona);

		
		/*
		 * todo esto va en capa Dao. todo lo q tenga q ver con DB va en Dao. creo q el util shutdown no?
		session.save(persona);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
*/
	}
}
