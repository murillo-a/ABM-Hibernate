package util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	// date dd/MM/yyyy to LocalDate
	public static LocalDate stringToDate(String dateString) {
		String sYear = dateString.substring(6);
		String sMonth;
		sMonth = dateString.substring(3, 5);
		String sDay = dateString.substring(0, 2);
		int year = Integer.parseInt(sYear);
		int month = Integer.parseInt(sMonth);
		int day = Integer.parseInt(sDay);
		LocalDate date = LocalDate.of(year, month, day);
		return date;
	}

	public static String dateToString(Date date) {
		SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
		String fechaStr = sdfDMY.format(date);
		return fechaStr;

	}

	public static int calcularEdad(LocalDate fechaNacimiento) {
		LocalDate now = LocalDate.now();
		Period diff = Period.between(fechaNacimiento, now);
		int edad = diff.getYears();
		return edad;
	}

}
