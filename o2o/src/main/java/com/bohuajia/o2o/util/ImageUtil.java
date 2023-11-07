package com.bohuajia.o2o.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bohuajia.o2o.dto.ImageHolder;

import java.io.File;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	/**
	 * Generate a random file name, the current year, month, day, hour, minute, second + five-digit random number
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		// Get a random five-digit number
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	
	/**
	 * Get the extension of the input file stream
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	/**
	 * Create the directories involved in the target path,
	 * e.g. /home/work/bohuajia/xxx.jpg, then home, work, bohuajia
	 * These three folders must be created automatically
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * Process thumbnails and return the relative value path of the newly generated image
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		// Get a unique random name
		String realFileName = getRandomFileName();
		// Get the file extension such as png, jpg, etc.
		String extension = getFileExtension(thumbnail.getImageName());
		// If the target path does not exist, it is automatically created
		makeDirPath(targetAddr);
		// Get the relative path of file storage (with file name)
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :" + relativeAddr);
		// Get the target path to save the file to
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("basePath is :" + basePath);
		// Call Thumbnails to generate an image with watermarks
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			throw new RuntimeException("Failed to create thumbnail：" + e.toString());
		}
		// Returns the relative path of the image
		return relativeAddr;
	}
	
	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("/Users/projbh/Github_projects/full_stack_web_design/image/Baymax.jpeg")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
				.outputQuality(0.8f).toFile("/Users/projbh/Github_projects/full_stack_web_design/image/Baymax_new.jpeg");
	}
}
