package com.ada.Hibernate.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static String dateToString(Date date) {
		SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
		String fechaStr = sdfDMY.format(date);
		return fechaStr;
	}
	
	public static int calcularEdad(Date fechaNac, Date hoyDate) {
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar hoy = new GregorianCalendar();
		gc.setTime(fechaNac);
		hoy.setTime(hoyDate);
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
