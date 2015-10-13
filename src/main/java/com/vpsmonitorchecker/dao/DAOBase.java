package com.vpsmonitorchecker.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class DAOBase <Entity>
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	private Class< Entity > classname;
	private String table;
	
	public DAOBase()
	{
		
	}

	public void setClass(Class< Entity > classname)
	{
		this.classname = classname;
	}
	
	public void setTable(String tbl)
	{
		this.table = tbl;
	}
	
	public String getTable()
	{
		return this.table;
	}
	
	public int insert(Entity entity) throws DAOException
    {
		Session session = this.sessionFactory.openSession();  
		Transaction tx = session.beginTransaction();
		try
		{
			session.saveOrUpdate(entity);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			throw new DAOException("[" + this.getClass().getName() + "::insert()]: " + e.getCause().getMessage() );
		}		
		Serializable id = session.getIdentifier(entity);  
		session.close();  
		return (Integer) id;    	
    }
	
	public void update(Entity entity) throws DAOException
	{
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try
		{
			session.saveOrUpdate(entity);
			tx.commit();
		}
		catch(HibernateException e)
		{
			tx.rollback();
			throw new DAOException("[" + this.getClass().getName() + "::update()]: " + e.getCause().getMessage() );
		}
		session.close();
	}
	
    public void deleteByID(int id) throws DAOException
    {
		Session session = this.sessionFactory.openSession();  
		Transaction tx = session.beginTransaction();
		Entity tmpEntity = (Entity) session.load(this.classname, id);
		try
		{
			session.delete(tmpEntity); 
			tx.commit(); 
		}
		catch(HibernateException e)
		{
			tx.rollback();
			throw new DAOException("[" + this.getClass().getName() + "::deleteByID()]: " + e.getCause().getMessage() );
		}

		session.close();  	
    }
	
	
}
