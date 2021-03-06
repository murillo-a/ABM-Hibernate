package com.ada.Hibernate.dao;

import org.hibernate.Session;

import com.ada.Hibernate.entity.VentaEntity;
import com.ada.Hibernate.util.HibernateUtil;


public class VentaDao {

	public void insertOrUpdate(Session session, VentaEntity venta) {
		session.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(venta);
		session.getTransaction().commit();
	}

	public VentaEntity getVenta(Session session, int id) {
		
		VentaEntity venta = (VentaEntity) session.createQuery("FROM VentaEntity WHERE ID = " + id).uniqueResult();
		return venta;
	}
	
	
}
