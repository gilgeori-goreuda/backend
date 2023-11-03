package com.pd.gilgeorigoreuda.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.pd.gilgeorigoreuda.review.controller.ReviewController;
import com.pd.gilgeorigoreuda.store.controller.StoreController;

@AutoConfigureRestDocs
@WebMvcTest({
	ReviewController.class,
	StoreController.class,
})
@ActiveProfiles("test")
public abstract class ControllerTest {

	@Autowired
	protected MockMvc mockMvc;

}
