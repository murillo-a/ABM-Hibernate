package com.ada.Hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.ada.Hibernate.dto.PersonaEntity;


public class PersonaDao {

	public void insertOrUpdate(Session session, PersonaEntity persona) {
		session.beginTransaction();
		session.saveOrUpdate(persona);
		session.getTransaction().commit();
	}

	
	@SuppressWarnings("unchecked")
	public List<PersonaEntity> getPersonaList(Session session) {
		List<PersonaEntity> personaList = new ArrayList<PersonaEntity>();
		personaList = session.createQuery("From PersonaEntity").list();
		return personaList;

	}
	
	public PersonaEntity getPersona(Session session, int id) {
		PersonaEntity persona = (PersonaEntity) session.createQuery("FROM PersonaEntity WHERE ID = " + id).uniqueResult();
		return persona;

	}
	
	@SuppressWarnings("unchecked")
	public List<PersonaEntity> buscarPorNombre(Session session, String nombre) {
		List<PersonaEntity> personaList = new ArrayList<PersonaEntity>();
		personaList = session.createQuery("FROM PersonaEntity WHERE NOMBRE = '" + nombre + "'").list();
		return personaList;

	}
	
	@SuppressWarnings("unchecked")
	public List<PersonaEntity> buscarPorEdad(Session session, int edad) {
		List<PersonaEntity> personaList = new ArrayList<PersonaEntity>();
		personaList = session.createQuery("FROM PersonaEntity WHERE EDAD = " + edad).list();
		return personaList;

	}
	
	public void delete(Session session, PersonaEntity persona) {
		session.beginTransaction();
		session.delete(persona);
		session.getTransaction().commit();
	}
}
