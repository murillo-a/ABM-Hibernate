package com.ada.Hibernate;

import com.ada.Hibernate.dao.PersonaDao;
import com.ada.Hibernate.dao.VentaDao;
import com.ada.Hibernate.entity.PersonaEntity;
import com.ada.Hibernate.entity.VentaEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.ada.Hibernate.util.HibernateUtil;
import com.ada.Hibernate.util.DateUtil;

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

	public void testGetPersona() {
		System.out.println("test 1");
		List<PersonaEntity> list = personaDao.getPersonaList(session);
		boolean tieneRegistros = list.size() > 0;
		assertTrue("No hay registros o no se obtiene lista", tieneRegistros);
		PersonaEntity persona = list.get(0);
		int id = persona.getId();
		String nombre = persona.getNombre();
		int edad = persona.getEdad();
		persona = personaDao.getPersona(session, id);
		assertTrue("No funciona getPersona", persona != null);
		list = personaDao.buscarPorNombre(session, nombre);
		assertTrue("No funciona buscarPorNombre", list != null);
		list = personaDao.buscarPorEdad(session, edad);
		assertTrue("No funciona buscarPorEdad", list != null);
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

	public void testCalcularEdad() {
		Date fechaNac = new Date();
		Date hoy = new Date();
		SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fechaNac = sdfDMY.parse("30/03/1991");
			hoy = sdfDMY.parse("30/03/2020");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int edad = DateUtil.calcularEdad(fechaNac, hoy);
		assertTrue("No funciona calcularEdad", edad == 29);
	}

}