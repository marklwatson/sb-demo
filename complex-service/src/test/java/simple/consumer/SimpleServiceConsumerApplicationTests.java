package simple.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import complex.service.ComplexServiceApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ComplexServiceApplication.class)
@WebAppConfiguration
public class SimpleServiceConsumerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
