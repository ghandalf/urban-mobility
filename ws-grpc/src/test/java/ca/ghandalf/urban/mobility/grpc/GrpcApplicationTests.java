package ca.ghandalf.urban.mobility.grpc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GrpcApplication.class })
@ContextConfiguration
public class GrpcApplicationTests {

	@Test
	public void contextLoads() {
	}

}
