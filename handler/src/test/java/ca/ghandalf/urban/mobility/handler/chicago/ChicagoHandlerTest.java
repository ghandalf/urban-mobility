package ca.ghandalf.urban.mobility.handler.chicago;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ChicagoHandler.class })
public class ChicagoHandlerTest {

	@Autowired
	private ChicagoHandler handler;
	
	@Before
	public void setUp() {
		Assert.assertNotNull(handler);
	}
	
	@Test
	public void getDataFailure() {
		handler.getData();
	}
}
