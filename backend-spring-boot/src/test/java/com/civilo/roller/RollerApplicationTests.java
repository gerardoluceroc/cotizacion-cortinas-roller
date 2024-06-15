package com.civilo.roller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RollerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainTest() {
		RollerApplication.main(new String[] {});
		assertNotNull(RollerApplication.class);
	}

}
