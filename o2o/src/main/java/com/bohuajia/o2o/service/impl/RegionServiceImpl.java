package com.bohuajia.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bohuajia.o2o.dao.RegionDAO;
import com.bohuajia.o2o.entity.Region;
import com.bohuajia.o2o.service.RegionService;

public class RegionServiceImpl implements RegionService{
	@Autowired
	private RegionDAO regionDao;
	@Override
	public List<Region> getRegionList() {
		return regionDao.queryRegion();
	}
	
}
