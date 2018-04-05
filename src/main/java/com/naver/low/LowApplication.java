package com.naver.low;

import com.naver.low.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LowApplication /*implements CommandLineRunner */{

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(LowApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		roleRepository.save(new Role(RoleName.ROLE_USER));
		roleRepository.save(new Role(RoleName.ROLE_WEBTOONIST));
		roleRepository.save(new Role(RoleName.ROLE_ADMIN));
	}*/
}
