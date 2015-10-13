package com.vpsmonitorchecker.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vpsmonitorchecker.mapper.VPSMonitorMapper;
import com.vpsmonitorchecker.model.VPSMonitorModel;

@Repository
public class VPSMonitorDAO extends DAOBase <VPSMonitorModel>
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public VPSMonitorDAO()
	{
		this.setClass(VPSMonitorModel.class);
		this.setTable("VPSMonitorModel");
	}
	
	public VPSMonitorModel findByID(int id)
	{
		String sql = "SELECT * FROM vps_monitor WHERE monitor_id=?";
		VPSMonitorModel monitorModel = jdbcTemplate.queryForObject(sql, new Object[]{id}, new VPSMonitorMapper());
		return monitorModel;
	}
	
	public List<VPSMonitorModel> findAll(ParamBuilder pb)
	{
		String sqlBase = "SELECT * FROM vps_monitor ";
		String sqlParams = pb.getSQL();
		String sql = (sqlBase+sqlParams).trim();
		
		List<VPSMonitorModel> modelList = jdbcTemplate.query(sql, pb.getParametersObject(), new VPSMonitorMapper());
		
		return modelList;
	}

	//status: 0=Disabled, 1=Online, 2=Offline
	public void changeServerStatus(int statusID, int monitorID)
	{
		String sql = "UPDATE vps_monitor SET status=? WHERE monitor_id=?";
		this.jdbcTemplate.update(sql, statusID, monitorID);
	}
	
	public void updateLastMonitoredDateTime(int monitorID)
	{
		String sql = "UPDATE vps_monitor SET last_monitored_datetime=? WHERE monitor_id=?";
		this.jdbcTemplate.update(sql, new Date(), monitorID);		
	}
	
}
