package com.hcmute.projectCT;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:mysql://localhost:3306/projectct_db?useSSL=false&serverTimezone=UTC",
		"spring.datasource.username=root",
		"spring.datasource.password=Xc.Nvt.2003",
		"spring.jpa.hibernate.ddl-auto=update",
		"spring.jackson.time-zone=GMT",
		"spring.mail.host=smtp.gmail.com",
		"spring.mail.port=587",
		"spring.mail.username=testmail@gmail.com",
		"spring.mail.password=testmailpassword",
		"server.address=127.0.0.1",
		"server.port=8080",
		"jwt.secret_key=mySecretKey",
		"jwt.expired_day=7",
		"jwt.expired_hour=24",
		"gemini.api.key=myGeminiAPIKey",
		"time_zone=GMT"
})
class ProjectCtTlcnApplicationTests {

	@Test
	void contextLoads() {
	}

}
