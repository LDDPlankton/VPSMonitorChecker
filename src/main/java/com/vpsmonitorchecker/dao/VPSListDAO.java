package com.vpsmonitorchecker.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vpsmonitorchecker.mapper.VPSListMapper;
import com.vpsmonitorchecker.model.VPSListModel;

@Repository
public class VPSListDAO extends DAOBase <VPSListModel>
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public VPSListDAO()
	{
		this.setClass(VPSListModel.class);
		this.setTable("VPSListModel");
	}
	
	public VPSListModel findByID(int id)
	{
		String sql = "SELECT * FROM vps_list WHERE vps_id=?";
		VPSListModel listModel = jdbcTemplate.queryForObject(sql, new Object[]{id}, new VPSListMapper());
		return listModel;
	}
	
	public List<VPSListModel> findAll(ParamBuilder pb)
	{
		String sqlBase = "SELECT * FROM vps_list ";
		String sqlParams = pb.getSQL();
		String sql = (sqlBase+sqlParams).trim();
		
		List<VPSListModel> modelList = jdbcTemplate.query(sql, pb.getParametersObject(), new VPSListMapper());
		
		return modelList;
	}
	
	//0=DISABLED, 1=ONLINE, 2=OFFLINE
	public void changeServerStatus(int statusID, int vpsID)
	{
		String sql = "UPDATE vps_list SET status=? WHERE vps_id=?";
		this.jdbcTemplate.update(sql, statusID, vpsID);
	}
	


}
