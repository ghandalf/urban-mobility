package ca.ghandalf.urban.mobility.restapi.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import  org.springframework.test.context.ContextConfiguration;

import ca.ghandalf.urban.mobility.restapi.controller.MontrealController;
import ca.ghandalf.urban.mobility.restapi.configuration.ApplicationConfiguration;
import ca.ghandalf.urban.mobility.restapi.configuration.ApplicationProperties;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ComponentScan("ca.ghandalf.urban.mobility")
@ContextConfiguration(classes = { ApplicationProperties.class, ApplicationConfiguration.class, MontrealController.class })
//@WebIntegrationTest -- TODO read and use it
public class RestApiApplicationTests {

	@Test
	public void contextLoads() {
	}

}
