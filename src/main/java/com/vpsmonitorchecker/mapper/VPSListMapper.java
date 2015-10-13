package com.vpsmonitorchecker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.jdbc.core.RowMapper;

import com.vpsmonitorchecker.model.VPSListModel;

public class VPSListMapper implements RowMapper<VPSListModel>
{
	public VPSListModel mapRow(ResultSet row, int rowNum) throws SQLException
	{
		VPSListModel model = new VPSListModel();
		model.setID(row.getInt("vps_id"));
		model.setName(row.getString("name"));
		model.setPrimaryIP(row.getString("primary_ip"));
		model.setStatus(row.getInt("status"));
		model.setCreateDateTime(row.getDate("create_datetime"));
		model.setSshNormalUser(row.getString("ssh_normal_user"));
		model.setSshNormalPass(row.getString("ssh_normal_pass"));
		model.setSshRootPassword(row.getString("ssh_root_password"));
		model.setSshRootKey(row.getString("ssh_root_key"));
		model.setSshIsNormalUserRequired(row.getInt("ssh_is_normal_user_required"));
		model.setSshIsRootKeyRequired(row.getInt("ssh_is_root_key_required"));
		model.setRestAuthorizationHash(row.getString("rest_authorization_hash"));
		return model;		
	}
}
