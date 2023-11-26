package com.pd.gilgeorigoreuda;


import com.pd.gilgeorigoreuda.settings.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {HealthController.class})
@Import(HealthController.class)
@DisplayName("상태 체크용 API 테스트")
public class HealthControllerTest extends ControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	@DisplayName("상태 체크용 API 테스트")
	void testHealthCheckTest() throws Exception {

		mvc.perform(get("/health")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("OK"));

	}

}