package com.bohuajia.o2o.util;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.io.File;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("/Users/projbh/Github_projects/full_stack_web_design/image/Baymax.jpeg")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.8f)
				.outputQuality(0.8f).toFile("/Users/projbh/Github_projects/full_stack_web_design/image/Baymax_new.jpeg");
	}
}
