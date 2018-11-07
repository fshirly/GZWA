package com.fable.insightview.platform.dict.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fable.insightview.platform.dict.mapper.SysConstantMapper;
import com.fable.insightview.platform.dict.service.SysConstantService;
@Service
public class SysConstantServiceImpl implements SysConstantService {

	@Autowired
	private SysConstantMapper sysConstantMapper;
	
	@Override
	public Map<String, Object> querySysConstant(Map<String, Object> map) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<Map<String, Object>> queryMap = sysConstantMapper.querySysConstant(map);
		if (!CollectionUtils.isEmpty(queryMap)) {
			resMap = queryMap.get(0);
		} else {
			//只按类型搜索
			Map<String, Object> newMap = new HashMap<String, Object>(); 
			newMap.put("ConstantTypeId", map.get("ConstantTypeId"));
			resMap = sysConstantMapper.querySysConstant(newMap).get(0);
		}
		return resMap;
	}

	
}
