package com.ada.Hibernate.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "venta", uniqueConstraints = { @UniqueConstraint(columnNames = "ID") })

public class VentaEntity implements Serializable {

	private static final long serialVersionUID = 1413687227050853952L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "FECHA", unique = false, nullable = false)
	private Date fechaVenta;
	
	@Column(name = "IMPORTE", unique = false, nullable = false)
	private Float importe;
	
	@ManyToOne
	@JoinColumn(name = "ID_PERSONA", unique = false, nullable = false) //puede aparecer el mismo ID en varias ventas, en la tabla ventas
	private PersonaEntity persona;

	public Integer getId() {
		return id;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Float getImporte() {
		return importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public PersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
	}
	

}
