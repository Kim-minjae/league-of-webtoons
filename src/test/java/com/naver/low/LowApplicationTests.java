package com.naver.low;

import com.naver.low.services.LowUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LowApplicationTests {

	@Autowired
	LowUserDetailsService lowUserDetailsService;

	@Test
	public void contextLoads() {
	}

	@Test
	@WithMockUser(username = "minjaeMock" , authorities = {"ROLE_ADMIN"})
	public void loadUserByUserId() {

		lowUserDetailsService.loadUserById(0L);

	}

}
