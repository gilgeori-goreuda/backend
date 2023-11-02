package com.pd.gilgeorigoreuda.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.gilgeorigoreuda.home.controller.HomeController;
import com.pd.gilgeorigoreuda.home.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.pd.gilgeorigoreuda.review.controller.ReviewController;
import com.pd.gilgeorigoreuda.store.controller.StoreController;

@AutoConfigureRestDocs
@WebMvcTest({
//	ReviewController.class,
//	StoreController.class,
	HomeController.class
})
@ActiveProfiles("test")
public abstract class ControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected HomeService homeService;

}
