package com.vpsmonitorchecker.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.vpsmoniterchecker.main.ExampleService;
import com.vpsmonitorchecker.model.VPSIPModel;
import com.vpsmonitorchecker.model.VPSListModel;
import com.vpsmonitorchecker.model.VPSMonitorModel;


@Configuration
@ComponentScan(basePackages = {"com.vpsmonitorchecker.dao","com.vpsmonitorchecker.portmonitor"})
public class AppConfig 
{
	@Bean
	public ExampleService exampleService()
	{
		ExampleService es = new ExampleService();
		return es;
	}
	
    @Bean
    public DataSource dataSource() 
    {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/vpsmonitor");
	    dataSource.setUsername("{user}");
	    dataSource.setPassword("{password}");
	    return dataSource;
    }

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource)
	{
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public SessionFactory sessionFactory(DataSource dataSource)
	{
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
	    bean.setDataSource(dataSource);
	    
        Class<?>[] classes = {VPSIPModel.class, VPSListModel.class, VPSMonitorModel.class};
        bean.setAnnotatedClasses(classes);
	    
		Properties props = new Properties();
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		props.put("hibernate.show_sql", "false");
		
		bean.setHibernateProperties(props);
		try
		{
			bean.afterPropertiesSet();
		}
		catch (IOException e)
		{
			System.out.println("sessionFactory() IOException: " + e.getMessage() );
		}

        return bean.getObject();
	}
}