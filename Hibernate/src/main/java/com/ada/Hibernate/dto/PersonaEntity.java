package com.ada.Hibernate.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "persona", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class PersonaEntity implements Serializable {
	
	private static final long serialVersionUID = -1798070786993154676L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(name = "NOMBRE", unique = false, nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "EDAD", unique = false, nullable = false, length = 3)
	private Integer edad;
	
	@Column(name = "FECHA_NACIMIENTO", unique = false, nullable = false)
	private Date fechaNacimiento;


	public String getFechaNacimientoStr() {
		SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
		String fechaNacimientoStr = sdfDMY.format(this.fechaNacimiento);
		return fechaNacimientoStr;
	}
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Integer getId() {
		return id;
	}

	public void setPersonaId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	
	
}
