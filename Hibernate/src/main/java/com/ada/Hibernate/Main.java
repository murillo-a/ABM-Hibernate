package com.ada.Hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import org.hibernate.Session;

import com.ada.Hibernate.dao.PersonaDao;
import com.ada.Hibernate.dto.PersonaEntity;

public class Main {
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

		HibernateUtil.shutdown();

		System.out.println("Fin del programa.");
	}

	private static void venta(Session session, Scanner sc) {
		// TODO PASAR A HIBERNATE
		
		
				// metodo verificar q existe registro (hay registro? devuelve boolean)
		
				// hay q hacer un join para juntar tablas
		
				System.out.print("VENTA\nIngrese ID de la persona: ");
				int id = sc.nextInt();
				
				// TODO buscar ID y mostrar nombre antes q el user ingrese la venta, crear metodo?
				System.out.print("Ingrese importe de la venta: ");
				float importe = sc.nextFloat();
				Date dateVenta = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String fechaString = sdf.format(dateVenta);

				// TODO usar Persona Dao aca para hacer el insert aca con los datos anteriores 

				// TODO mostrar ID de venta
				System.out.println("Se ha registrado la venta.");

	}

	private static void buscarRegistro(Session session, Scanner sc) {
		// TODO Auto-generated method stub
		
	}

	private static void listado(Session session) {
		System.out.println();
		System.out.println("LISTADO:");
		System.out.println("ID - NOMBRE - EDAD - F.NACIMIENTO\n");
		PersonaDao personaDao = new PersonaDao();
	
		
		for (PersonaEntity pers : personaDao.getPersonaList(session)) {
			System.out
					.println(pers.getId() + " " + pers.getNombre() + " " + pers.getEdad() + " " + pers.getFechaNacimiento());
		}

		System.out.println("\nFIN LISTADO------------\n");
	}

	private static void baja(Session session, Scanner sc) {
		// TODO Auto-generated method stub
		
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
		System.out.print("ALTA DE PERSONA\nIngrese nombre: ");
		String nombre = sc.next();
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
