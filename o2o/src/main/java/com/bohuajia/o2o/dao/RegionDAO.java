package com.bohuajia.o2o.dao;

import java.util.List;

import com.bohuajia.o2o.entity.Region;

public interface RegionDAO {
	/**
	 * list region list
	 * @return regionList
	 */
	List<Region> queryRegion();
}
