package com.vpsmonitorchecker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vpsmonitorchecker.model.VPSIPModel;

public class VPSIPMapper implements RowMapper<VPSIPModel>
{
	public VPSIPModel mapRow(ResultSet row, int rowNum) throws SQLException
	{
		VPSIPModel model = new VPSIPModel();
		model.setID(row.getInt("vps_ip_id"));
		model.setVpsID(row.getInt("vps_id"));
		model.setIPAddress(row.getString("ip_address"));
		model.setCreateDateTime(row.getDate("create_datetime"));
		return model;				
	}
}
