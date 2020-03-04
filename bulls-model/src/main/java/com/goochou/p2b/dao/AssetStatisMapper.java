package com.goochou.p2b.dao;

import java.util.List;
import java.util.Map;

public interface AssetStatisMapper {
	
	List<Map<String, Object>> selectAssetStatisByTermAndRelease(Map<String, Object> searchMap);

}
