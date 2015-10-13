package com.vpsmonitorchecker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vpsmonitorchecker.model.VPSMonitorModel;

public class VPSMonitorMapper implements RowMapper<VPSMonitorModel>
{
	public VPSMonitorModel mapRow(ResultSet row, int rowNum) throws SQLException
	{
		VPSMonitorModel model = new VPSMonitorModel();
		model.setID(row.getInt("monitor_id"));
		model.setVpsID(row.getInt("vps_id"));
		model.setMonitorIPOrUrl(row.getString("monitor_ip_or_url"));
		model.setMonitorPort(row.getInt("monitor_port"));
		model.setMonitorTimeout(row.getInt("monitor_timeout"));
		model.setIsRequireValidContent(row.getInt("is_require_valid_content"));
		model.setValidContent(row.getString("valid_content"));
		model.setCreateDateTime(row.getDate("create_datetime"));
		model.setLastMonitoredDateTime(row.getDate("last_monitored_datetime"));
		model.setStatus(row.getInt("status"));
		return model;
	}
}
