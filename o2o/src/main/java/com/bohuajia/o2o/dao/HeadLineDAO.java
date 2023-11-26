package com.bohuajia.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bohuajia.o2o.entity.HeadLine;

public interface HeadLineDAO {
	/**
	 * According to the incoming query conditions (query headlines by headline name)
	 * 
	 * @return
	 */
	List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

	/**
	 * Return unique headline information based on headline id
	 * 
	 * @param lineId
	 * @return
	 */
	HeadLine queryHeadLineById(long lineId);

	/**
	 * Query headline information based on the incoming ID list 
	 * (for super administrators to use when selecting to delete headlines, mainly for processing pictures)
	 * 
	 * @param lineIdList
	 * @return
	 */
	List<HeadLine> queryHeadLineByIds(List<Long> lineIdList);

	/**
	 * Insert headline
	 * 
	 * @param headLine
	 * @return
	 */
	int insertHeadLine(HeadLine headLine);

	/**
	 * Update headline
	 * 
	 * @param headLine
	 * @return
	 */
	int updateHeadLine(HeadLine headLine);

	/**
	 * Delete headline
	 * 
	 * @param headLineId
	 * @return
	 */
	int deleteHeadLine(long headLineId);

	/**
	 * Batch delete headline
	 * 
	 * @param lineIdList
	 * @return
	 */
	int batchDeleteHeadLine(List<Long> lineIdList);
}
