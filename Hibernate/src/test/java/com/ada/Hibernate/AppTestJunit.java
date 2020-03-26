package com.ada.Hibernate;

import com.ada.Hibernate.dao.PersonaDao;
import com.ada.Hibernate.dto.PersonaEntity;
import com.ada.Hibernate.dao.VentaDao;
import com.ada.Hibernate.dto.VentaEntity;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.ada.Hibernate.util.HibernateUtil;

/**
 * Unit test for simple App.
 */
public class AppTestJunit extends TestCase {

	Session session = HibernateUtil.getSessionFactory().openSession();
	PersonaDao personaDao = new PersonaDao();
	PersonaEntity persona = new PersonaEntity();
	
	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTestJunit.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testGetPersonaList() {
		System.out.println("test 1");
		List<PersonaEntity> list = personaDao.getPersonaList(session);
		boolean tieneRegistros = list.size() > 0;
		assertTrue("No hay registros", tieneRegistros);
	}

	public void testInsertThenDelete() {
		System.out.println("test 2");
		List<PersonaEntity> list = personaDao.getPersonaList(session);
		int listSize0 = list.size();
		persona.setNombre("TEST");
		persona.setEdad(30);
		Date date = new Date();
		persona.setFechaNacimiento(date);
		personaDao.insertOrUpdate(session, persona);
		list = personaDao.getPersonaList(session);
		int listSize1 = list.size();
		assertTrue("No se guardó el registro de prueba", listSize1 == listSize0 + 1);
		personaDao.delete(session, persona);
		list = personaDao.getPersonaList(session);
		int listSize2 = list.size();
		assertTrue("No se eliminó el registro de prueba", listSize2 == listSize0);
	}

	public void testGetPersona() {
		
	}
	
	public void testCalcularEdad() {
		
	}
	
}