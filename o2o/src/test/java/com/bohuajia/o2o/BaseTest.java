package com.bohuajia.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * Configure Spring and Junit
 * When starting Junit while loading SpringIOC container 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
//Tell Junit where is the location of the Spring configuration file
@ContextConfiguration({ "classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {
	
	
}
