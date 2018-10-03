package ca.ghandalf.urban.mobility.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ContextConfiguration;

import ca.ghandalf.urban.mobility.api.controller.ApiController;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { ApiController.class })
public class ApplicationTest {

	@Test
	public void contextLoads() {
	}

}
