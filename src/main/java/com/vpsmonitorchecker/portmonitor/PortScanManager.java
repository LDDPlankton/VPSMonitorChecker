package com.vpsmonitorchecker.portmonitor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.vpsmonitorchecker.dao.ParamBuilder;
import com.vpsmonitorchecker.dao.VPSListDAO;
import com.vpsmonitorchecker.dao.VPSMonitorDAO;
import com.vpsmonitorchecker.model.VPSMonitorModel;

@Service
public class PortScanManager
{
	@Autowired
	private VPSListDAO vpsListDAO;
	@Autowired
	private VPSMonitorDAO vpsMonitorDAO;
	
	public PortScanManager()
	{

	}
	
	public void run()
	{
		/*
        ParamBuilder pb = new ParamBuilder();
        List<VPSMonitorModel> monitorList = this.vpsMonitorDAO.findAll(pb);
        for(VPSMonitorModel model : monitorList)
        {
        	int monitorID = model.getID();
        	String ip_or_url = model.getMonitorIPOrUrl();
        	List<Integer> portList = new ArrayList<Integer>();
        	portList.add(model.getMonitorPort());
        	int timeout = model.getMonitorTimeout();
        	
			//SET IP + PORT LIST
			Runnable r = new PortScanner(monitorID, ip_or_url, portList, timeout);
			Thread t = new Thread(r);
			t.start();
        }
        */
    	int monitorID = 1;
    	String ip_or_url = "http://msn.com";
    	List<Integer> portList = new ArrayList<Integer>();
    	portList.add(80);
    	int timeout = 10;
    	
		//SET IP + PORT LIST
		Runnable r = new PortScanner(this.vpsListDAO, this.vpsMonitorDAO, monitorID, ip_or_url, portList, timeout);
		Thread t = new Thread(r);
		t.start();
	}
}
