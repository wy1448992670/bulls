package com.goochou.p2b.hessian;

import java.util.Map;

public class MapResponse extends Response{
	private static final long serialVersionUID = 501389201886217261L;
	
	private Map<String, Object> map;

	/**
	 * @return the map
	 */
	public Map<String, Object> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	 
}
