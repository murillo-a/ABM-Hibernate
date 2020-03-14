package com.ada.Hibernate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import com.ada.Hibernate.dao.PersonaDao;
import com.ada.Hibernate.dao.VentaDao;
import com.ada.Hibernate.dto.PersonaEntity;
import com.ada.Hibernate.dto.VentaEntity;

public class Main {

	// DAo como atributo

	static PersonaDao personaDao = new PersonaDao(); // puede ser estatico

	public static void main(String[] args) {
		System.out.println("SISTEMA DE PERSONAS (ABM)");
		System.out.println("=========================\n");

		Session session = HibernateUtil.getSessionFactory().openSession();

		Scanner sc = new Scanner(System.in);

		int opcion = mostrarMenu(sc);
		while (opcion != 0) {

			switch (opcion) {
			case 1:
				alta(session, sc);
				break;
			case 2:
				modificacion(session, sc);
				break;
			case 3:
				baja(session, sc);
				break;
			case 4:
				listado(session);
				break;
			case 5:
				buscarRegistro(session, sc);
				break;
			case 6:
				venta(session, sc);
				break;
			default:
				break;
			}
			opcion = mostrarMenu(sc);
		}

		HibernateUtil.shutdown(); // encapsula a session.close()

		System.out.println("Fin del programa.");
	}

	private static void venta(Session session, Scanner sc) {
		// TODO PASAR A HIBERNATE

		// metodo verificar q existe registro (hay registro? devuelve boolean)

		System.out.print("VENTA\nIngrese ID de la persona: ");
		int idPersona = sc.nextInt();
		// TODO mostrar persona
		PersonaEntity persona = personaDao.getPersona(session, idPersona);
		System.out.println("Ha seleccionado a: " + persona.getId() + " " + persona.getNombre() + " " + persona.getEdad()
				+ " " + persona.getFechaNacimiento());

		System.out.print("Ingrese importe de la venta: ");
		Float importe = sc.nextFloat();
		Date dateVenta = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaString = sdf.format(dateVenta);
		VentaEntity venta = new VentaEntity();
		venta.setFechaVenta(dateVenta);
		venta.setImporte(importe); // hay que ingresarlo con coma, o punto dependiendo de la cfg de la maquina
		venta.setPersona(persona);

		VentaDao ventaDao = new VentaDao();
		ventaDao.insertOrUpdate(session, venta);

		// TODO mostrar ID de venta

		System.out.println("Se ha registrado la venta ");

	}

	private static void buscarRegistro(Session session, Scanner sc) {
		// TODO Auto-generated method stub

		/*
		 * System.out.print("VENTA\nIngrese ID de la persona: "); int idPersona =
		 * sc.nextInt(); // TODO mostrar persona PersonaEntity persona =
		 * personaDao.getPersona(session, idPersona).get(0);
		 * System.out.println("Ha seleccionado a: " + persona.getId() + " " +
		 * persona.getNombre() + " " + persona.getEdad() + " " +
		 * persona.getFechaNacimiento() );
		 */
		int opcion;
		do {

			System.out.println(
					"Ingrese opcion para buscar por\n1: nombre | 2: edad | otro número para cancelar búsqueda");
			opcion = sc.nextInt();
			switch (opcion) {
			case 1:
				String nombre = pedirNombre(sc);
				List <PersonaEntity> personaList = personaDao.buscarPorNombre(session, nombre);
				mostrarList(session, personaList);
				break;
			case 2:
				int edad = pedirEdad(sc);
				personaList = personaDao.buscarPorEdad(session, edad);
				mostrarList(session, personaList);
				break;
			default:
				System.out.println("Ingrese un número de opción válido.");
				break;

			}
			System.out.println("Ingrese 1 para buscar otro registro, otro número para volver al menu: ");
			opcion = sc.nextInt();
		} while (opcion == 1);

	}

	private static void mostrarList(Session session, List<PersonaEntity> personaList) {
	for	(PersonaEntity pers : personaList) {
			System.out.println(
					pers.getId() + " " + pers.getNombre() + " " + pers.getEdad() + " " + pers.getFechaNacimientoStr());
	}
	}

	private static String pedirNombre(Scanner sc) {
		System.out.print("Ingrese el nombre: ");
		String nombre = sc.next();
		return nombre;
	}

	private static int pedirEdad(Scanner sc) {
		System.out.print("Ingrese la edad: ");
		int edad = sc.nextInt();
		return edad;
	}

	private static void listado(Session session) {
		System.out.println();
		System.out.println("LISTADO:");
		System.out.println("ID - NOMBRE - EDAD - F.NACIMIENTO\n");

		for (PersonaEntity pers : personaDao.getPersonaList(session)) {
			System.out.println(
					pers.getId() + " " + pers.getNombre() + " " + pers.getEdad() + " " + pers.getFechaNacimientoStr());
		}

		System.out.println("\nFIN LISTADO------------\n");
	}

	private static void baja(Session session, Scanner sc) {
		System.out.print("Ingrese ID para dar de baja: ");
		int id = sc.nextInt();
		// show
		PersonaEntity persona = personaDao.getPersona(session, id);
		System.out.println("Ha seleccionado a " + persona.getId() + " " + persona.getNombre() + " " + persona.getEdad()
				+ " " + persona.getFechaNacimientoStr());
		System.out.print("Desea dar de baja a esta persona?\nIngrese 1 para continuar, otro número para ver menu: ");
		int opcion = sc.nextInt();
		if (opcion == 1) {

			personaDao.delete(session, persona);

			System.out.println("Se ha eliminado al registro #" + id + ".\n");
		}
	}

	private static void modificacion(Session session, Scanner sc) {
		// TODO Auto-generated method stub

	}

	private static int mostrarMenu(Scanner sc) {
		System.out.println("MENU OPCIONES:\n");
		System.out.println("1: ALTA | 2: MODIFICACION | 3: BAJA | 4: LISTADO | 5: BÚSQUEDA | 6: VENTA | 0: SALIR");
		int opcion;
		opcion = sc.nextInt();
		return opcion;
	}

	private static void alta(Session session, Scanner sc) {
		System.out.println("ALTA DE PERSONA:");
		String nombre = pedirNombre(sc);
		System.out.print("Ingrese fecha nacimiento (dd/mm/aaaa): ");
		String fechaNacimientoString = sc.next();

		SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date fechaNac = sdfDMY.parse(fechaNacimientoString);
			fechaNacimientoString = sdfYMD.format(fechaNac);
			int edad = calcularEdad(fechaNac);

			PersonaEntity persona = new PersonaEntity();
			persona.setNombre(nombre);
			persona.setEdad(edad);
			persona.setFechaNacimiento(fechaNac);
			PersonaDao personaDao = new PersonaDao();

			personaDao.insertOrUpdate(session, persona);

			String msj = "Se ha añadido a " + nombre + ".";
			System.out.println(msj);

		} catch (ParseException e) {
			System.out.println("Ha ocurrido un error en el alta.");
		}

	}

	private static int calcularEdad(Date fechaNac) {
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar hoy = new GregorianCalendar();
		gc.setTime(fechaNac);
		int anioActual = hoy.get(Calendar.YEAR);
		int anioNacim = gc.get(Calendar.YEAR);

		int mesActual = hoy.get(Calendar.MONTH);
		int mesNacim = gc.get(Calendar.MONTH);

		int diaActual = hoy.get(Calendar.DATE);
		int diaNacim = gc.get(Calendar.DATE);

		int dif = anioActual - anioNacim;

		if (mesActual < mesNacim) {
			dif -= 1;
		} else {
			if (mesActual == mesNacim && diaActual < diaNacim) {
				dif -= 1;
			}
		}

		return dif;
	}

}
