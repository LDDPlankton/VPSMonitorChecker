package com.vpsmoniterchecker.main;

import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vpsmonitorchecker.config.AppConfig;
import com.vpsmonitorchecker.portmonitor.PortScanManager;

public class Main
{

	public static void main(String[] args)
	{
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		ExampleService ex = (ExampleService)context.getBean("exampleService");
		System.out.println(ex.getMessage());
		
		for(String i : context.getBeanDefinitionNames() )
		{
			System.out.println(i);;
		}
		
        PortScanManager ex2 = (PortScanManager)context.getBean("portScanManager");
        ex2.run();


	}

}
