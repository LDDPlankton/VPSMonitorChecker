package com.vpsmonitorchecker.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vpsmonitorchecker.mapper.VPSIPMapper;
import com.vpsmonitorchecker.model.VPSIPModel;

@Repository
public class VPSIPDAO extends DAOBase <VPSIPModel>
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public VPSIPDAO()
	{
		this.setClass(VPSIPModel.class);
		this.setTable("VPSIPModel");
	}
	
	public VPSIPModel findByID(int id)
	{
		String sql = "SELECT * FROM vps_ips WHERE vps_ip_id=?";
		VPSIPModel ipModel = jdbcTemplate.queryForObject(sql, new Object[]{id}, new VPSIPMapper());
		return ipModel;
	}
	
	public List<VPSIPModel> findAll(ParamBuilder pb)
	{
		String sqlBase = "SELECT * FROM vps_ips ";
		String sqlParams = pb.getSQL();
		String sql = (sqlBase+sqlParams).trim();
		
		List<VPSIPModel> modelList = jdbcTemplate.query(sql, pb.getParametersObject(), new VPSIPMapper());
		
		return modelList;
	}
}
