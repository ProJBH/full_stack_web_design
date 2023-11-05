package com.bohuajia.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohuajia.o2o.BaseTest;
import com.bohuajia.o2o.entity.Region;

public class RegionServiceTest extends BaseTest{
	@Autowired
	private RegionService regionService;

	@Test
	public void testGetRegionList() {
		List<Region> regionList = regionService.getRegionList();
		assertEquals("East", regionList.get(0).getRegionName());
	}
}
