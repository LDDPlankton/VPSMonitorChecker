package com.vpsmonitorchecker.portmonitor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.vpsmonitorchecker.dao.VPSListDAO;
import com.vpsmonitorchecker.dao.VPSMonitorDAO;

public class PortScanner implements Runnable
{
	private VPSListDAO vpsListDAO;
	private VPSMonitorDAO vpsMonitorDAO;
	
	private String server_ip;											//SERVER IP TO CHECK
	private String server_status;										//SERVER STATUS [ONLINE, OFFLINE]
	private int server_resolve_timeout;									//TIMEOUT FOR RESOLVING IP TO SEE IF REACHABLE
	private int port_timeout;											//DEFAULT TIMEOUT FOR RESOLVING PORT TO SEE IF CAN CONNECT
	private List<Integer> portList;										//LIST OF PORTS TO CHECK
	private List<Integer> portsOffline;									//STORES LIST OF PORTS OFFLINE
	private List<Integer> portsOnline;									//STORES LIST OF PORTS ONLINE
	private int service_id = 0;
	
	public PortScanner()
	{
		
	}
	
	public PortScanner(VPSListDAO vpsListDAO, VPSMonitorDAO vpsMonitorDAO, int service_id, String server_ip, List<Integer> portList, int timeout)
	{
		this.vpsListDAO = vpsListDAO;
		this.vpsMonitorDAO = vpsMonitorDAO;
		this.server_ip = server_ip;
		this.server_status = "ONLINE";
		this.server_resolve_timeout = 5000;
		this.port_timeout = timeout*1000;
		this.portList = new ArrayList<Integer>(portList);
		this.portsOffline = new ArrayList<Integer>();
		this.service_id = service_id;
	}
	
	public void handle_server_unresponsive()
	{
		System.out.println("SERVER OFFLINE="+this.server_ip);

		//MARK OFFLINE
		this.vpsListDAO.changeServerStatus(2, this.service_id);
	}
	
	public void handle_server_responsive()
	{
		System.out.println("SERVER ONLINE="+this.server_ip);

		//MARK ONLINE
		this.vpsListDAO.changeServerStatus(1, this.service_id);
	}
	
	public void handle_unresponsive_ports()
	{
		System.out.print("SERVER IP PORTS OFFLINE:" + this.server_ip + " => ");
		for(int port : this.portsOffline)
		{
			System.out.print(port + ",");
			this.vpsMonitorDAO.changeServerStatus(2, this.service_id);
			this.vpsMonitorDAO.updateLastMonitoredDateTime(this.service_id);
		}
		System.out.println();
	}
	
	public void handle_responsive_ports()
	{
		System.out.print("SERVER IP PORTS ONLINE:" + this.server_ip + " => ");
		for(int port : this.portsOffline)
		{
			System.out.print(port + ",");
			this.vpsMonitorDAO.changeServerStatus(1, this.service_id);
			this.vpsMonitorDAO.updateLastMonitoredDateTime(this.service_id);
		}
		System.out.println();
	}

	public void run()
	{
		//System.out.println("IP:"+this.server_ip + " RUN STARTED");
		
		//CHECK SERVER REACHABLE STATUS [ONLINE, OFFLINE]

		Boolean status = false;
		try
		{
			status = InetAddress.getByName(this.server_ip).isReachable(this.server_resolve_timeout);
		}
		catch (IOException e)
		{
			//System.out.println("E="+e.getMessage());
			status = false;
		}
		if(!status)
		{
			this.server_status = "OFFLINE";
			this.handle_server_unresponsive();
			return;
		}
		else
		{
			this.handle_server_responsive();
		}
		
		//System.out.println("IP:"+this.server_ip + " CHECKING PORTS");
		
		
		//NOW CHECK PORTS
		for(int port : this.portList)
		{
			try
			{
				Socket mySocket = new Socket();
				mySocket.connect( new InetSocketAddress(this.server_ip, port), this.port_timeout);	
				mySocket.close();	
				
				this.portsOnline.add(port);
			}
			catch (IOException e)
			{
				//System.out.println("E="+e.getMessage());
				this.portsOffline.add(port);
			}
		}
		
		System.out.println("IP:"+this.server_ip + " RUN COMPLETE");
		this.handle_unresponsive_ports();
		this.handle_responsive_ports();

	}
}
