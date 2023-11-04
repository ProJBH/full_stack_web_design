package com.bohuajia.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohuajia.o2o.BaseTest;
import com.bohuajia.o2o.entity.Region;

public class RegionDaoTest extends BaseTest{
	@Autowired
	private RegionDAO regionDao;
	
	@Test
	public void testQueryArea(){
		List<Region> areaList = regionDao.queryRegion();
		assertEquals(2, areaList.size());
	}
}
