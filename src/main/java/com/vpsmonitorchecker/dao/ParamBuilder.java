package com.vpsmonitorchecker.dao;

import java.util.HashMap;
import java.util.Map;

public class ParamBuilder
{
	private Map<String,Object> params;
	
	public ParamBuilder()
	{
		this.params = new HashMap<String,Object>();
	}
	
	public void addParam(String key, Object val)
	{
		this.params.put(key, val);
	}
	
	public Map<String,Object> getParameters()
	{
		return this.params;
	}
	
	public Object[] getParametersObject()
	{
		Object[] newObject = new Object[this.params.size()];
		int position = 0;
		for(Map.Entry<String, Object> n : this.params.entrySet())
		{
			newObject[position++] = n.getValue();
		}
		return newObject;
	}
	
	public String getSQL()
	{
		String sql = "";
		int count = 1;
		
		//IF WE HAVE PARAMS APPEND WHERE TO SQL CLAUSE
		if(this.params.size() > 0)
			sql += "WHERE ";
		
		for(Map.Entry<String,Object> e : this.params.entrySet())
		{
			String key = e.getKey();
			sql += key + " = :" + key;

			if(count < this.params.size())
				sql += " AND ";
			
			count++;
		}
		
		return sql;
	}
	
}
