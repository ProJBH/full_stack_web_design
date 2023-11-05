package com.bohuajia.o2o.web.superAdmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bohuajia.o2o.entity.Region;
import com.bohuajia.o2o.service.RegionService;

@Controller
@RequestMapping("/superadmin")
public class RgionController {
	@Autowired
	private RegionService regionService;
	@RequestMapping(value= "/listregion", method=RequestMethod.GET)
	//@ResponseBody: transfer modelMap obj to json form to frontend
	@ResponseBody
	private Map<String, Object> listRegion(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Region> list = new ArrayList<Region>();
		try {
			list = regionService.getRegionList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		}catch(Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}
}
