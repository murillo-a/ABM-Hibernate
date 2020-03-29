package com.ada.Hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import com.ada.Hibernate.dao.PersonaDao;
import com.ada.Hibernate.dao.VentaDao;
import com.ada.Hibernate.entity.PersonaEntity;
import com.ada.Hibernate.entity.VentaEntity;
import com.ada.Hibernate.util.HibernateUtil;
import com.ada.Hibernate.util.DateUtil;

public class Main {

	static PersonaDao personaDao = new PersonaDao();
	static VentaDao ventaDao = new VentaDao();

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

		// metodo verificar q existe registro (hay registro? devuelve boolean)

		System.out.print("VENTA\nIngrese ID de la persona: ");
		int idPersona = sc.nextInt();
		// mostrar persona s
		PersonaEntity persona = personaDao.getPersona(session, idPersona);
		if (persona != null) {
			mostrarSeleccion(persona);
			// venta
			System.out.print("Ingrese importe de la venta: ");
			Float importe = sc.nextFloat();
			Date dateVenta = new Date();
			VentaEntity venta = new VentaEntity();
			venta.setFechaVenta(dateVenta);
			venta.setImporte(importe); // hay que ingresarlo con coma, o punto dependiendo de la cfg de la maquina
			venta.setPersona(persona);

			ventaDao.insertOrUpdate(session, venta);

			System.out.println("Se ha registrado la venta ");
		} else {
			System.out.println("No existe persona con el ID #" + idPersona + "\n");
		}

	}

	private static void mostrarSeleccion(PersonaEntity persona) {
		System.out.println("Ha seleccionado a: " + persona.getId() + " " + persona.getNombre() + " " + persona.getEdad()
				+ " " + DateUtil.dateToString(persona.getFechaNacimiento()));
	}



	private static void buscarRegistro(Session session, Scanner sc) {

		int opcion = 1;
		do {

			System.out.println(
					"Ingrese opcion para buscar por\n1: nombre | 2: edad | otro número para cancelar búsqueda");
			opcion = sc.nextInt();
			switch (opcion) {
			case 1:
				String nombre = pedirNombre(sc);
				List<PersonaEntity> personaList = personaDao.buscarPorNombre(session, nombre);
				mostrar(personaList);
				pedirOpcion(sc);
				break;
			case 2:				
				System.out.print("Ingrese la edad: ");
				int edad = sc.nextInt();
				personaList = personaDao.buscarPorEdad(session, edad);
				mostrar(personaList);
				pedirOpcion(sc);
				break;
			default:

				break;

			}

		} while (opcion == 1);

	}

	private static int pedirOpcion(Scanner sc) {
		System.out.println("Ingrese 1 para buscar un registro, otro número para volver al menu: ");
		int opcion = sc.nextInt();
		return opcion;
	}

	private static void mostrar(List<PersonaEntity> personaList) {
		for (PersonaEntity persona : personaList) {
			System.out.println(persona.getId() + " " + persona.getNombre() + " " + persona.getEdad() + " "
					+ DateUtil.dateToString(persona.getFechaNacimiento()));
		}
	}

	private static String pedirNombre(Scanner sc) {
		System.out.print("Ingrese el nombre: ");
		String nombre = sc.next();
		return nombre;
	}

	private static void listado(Session session) {
		System.out.println();
		System.out.println("LISTADO:");
		System.out.println("ID - NOMBRE - EDAD - F.NACIMIENTO\n");

		for (PersonaEntity persona : personaDao.getPersonaList(session)) {
			System.out.println(persona.getId() + " " + persona.getNombre() + " " + persona.getEdad() + " "
					+ DateUtil.dateToString(persona.getFechaNacimiento()));
		}

		System.out.println("\nFIN LISTADO------------\n");
	}

	private static void baja(Session session, Scanner sc) {
		System.out.print("Ingrese ID para dar de baja: ");
		int id = sc.nextInt();
		// mostrar
		PersonaEntity persona = personaDao.getPersona(session, id);
		mostrarSeleccion(persona);
		System.out.print("Desea dar de baja a esta persona?\nIngrese 1 para continuar, otro número para ver menu: ");
		int opcion = sc.nextInt();
		if (opcion == 1) {

			personaDao.delete(session, persona);

			System.out.println("Se ha eliminado al registro #" + id + ".\n");
		}
	}

	private static void modificacion(Session session, Scanner sc) {
		// TODO Auto-generated method stub
		int opcion;
		int id;
		do {
			System.out.print("Ingrese ID para modificar registro: ");
			id = sc.nextInt();
			// mostrar
			PersonaEntity persona = personaDao.getPersona(session, id);
			mostrarSeleccion(persona);
			System.out.print("Desea modificar este registro?\nIngrese 1 para continuar, otro número para ver menu: ");
			opcion = sc.nextInt();
			persona.setPersonaId(id);
			if (opcion == 1) {
				System.out.println(
						"1: modificar nombre\n2: modificar fecha de nacimiento\n3: nombre y fecha de nacimiento");
				opcion = sc.nextInt();
				System.out.println("VALOR(ES) NUEVOS: ");
				switch (opcion) {
				case 1:
					modificarNombre(sc, persona, session);
					break;
				case 2:
					modificarFecha(sc, persona, session);
					break;
				case 3:
					modificarNombre(sc, persona, session);
					modificarFecha(sc, persona, session);

					break;
				default:
					break;
				}
			}
			System.out.print("Ingrese 1 para modificar otro registro, otro número para ver menu: ");
			opcion = sc.nextInt();
		} while (opcion == 1);
	}
	

	private static void modificarNombre(Scanner sc, PersonaEntity persona, Session session) {
		
		String nombre = pedirNombre(sc);
		persona.setNombre(nombre);
		personaDao.insertOrUpdate(session, persona);
	}

	private static void modificarFecha(Scanner sc, PersonaEntity persona, Session session) {
		String fechaNacString = pedirFechaNac(sc);
		SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
			try {
			Date fechaNac = sdfDMY.parse(fechaNacString);

			int edad = DateUtil.calcularEdad(fechaNac);

			persona.setEdad(edad);
			persona.setFechaNacimiento(fechaNac);
			personaDao = new PersonaDao();
			personaDao.insertOrUpdate(session, persona);
			persona.setFechaNacimiento(fechaNac);
		} catch (ParseException e) {
			System.out.println("Ha ocurrido un error con la fecha.");
		}
		}

	private static int mostrarMenu(Scanner sc) {
		System.out.println("MENU OPCIONES:\n");
		System.out.println("1: ALTA | 2: MODIFICACION | 3: BAJA | 4: LISTADO | 5: BÚSQUEDA | 6: VENTA | 0: SALIR");
		int opcion;
		opcion = sc.nextInt();
		return opcion;
	}

	private static String pedirFechaNac(Scanner sc) {
		System.out.print("Ingrese fecha nacimiento (dd/mm/aaaa): ");
		String fechaNacimientoString = sc.next();
		return fechaNacimientoString;
	}

	private static void alta(Session session, Scanner sc) {
		System.out.println("ALTA DE PERSONA:");
		String nombre = pedirNombre(sc);
		String fechaNacimientoString = pedirFechaNac(sc);

		SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
		// SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date fechaNac = sdfDMY.parse(fechaNacimientoString);
			int edad = DateUtil.calcularEdad(fechaNac);

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

}
