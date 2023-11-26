package com.bohuajia.o2o.service;

import java.io.IOException;
import java.util.List;

import com.bohuajia.o2o.dto.HeadLineExecution;
import com.bohuajia.o2o.dto.ImageHolder;
import com.bohuajia.o2o.entity.HeadLine;

public interface HeadLineService {
	public static final String HLLISTKEY = "headlinelist";

	/**
	 * Returns the specified headline list based on the incoming conditions
	 * 
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition);

	/**
	 * Add headline information and store headline images
	 * 
	 * @param headLine
	 * @param thumbnail
	 * @return
	 */
	HeadLineExecution addHeadLine(HeadLine headLine, ImageHolder thumbnail);

	/**
	 * Update headline information
	 * 
	 * @param headLine
	 * @param thumbnail
	 * @return
	 */
	HeadLineExecution modifyHeadLine(HeadLine headLine, ImageHolder thumbnail);

	/**
	 * Delete headline information
	 * 
	 * @param headLineId
	 * @return
	 */
	HeadLineExecution removeHeadLine(long headLineId);

	/**
	 * Batch delete headline information
	 * 
	 * @param headLineIdList
	 * @return
	 */
	HeadLineExecution removeHeadLineList(List<Long> headLineIdList);
}
