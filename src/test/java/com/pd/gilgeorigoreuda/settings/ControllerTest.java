package com.pd.gilgeorigoreuda.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.gilgeorigoreuda.auth.resolver.MemberInfoArgumentResolver;
import com.pd.gilgeorigoreuda.login.jwt.BearerTokenExtractor;
import com.pd.gilgeorigoreuda.login.jwt.JwtProvider;
import com.pd.gilgeorigoreuda.login.repository.RefreshTokenRepository;
import com.pd.gilgeorigoreuda.settings.restdocs.RestDocsConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.pd.gilgeorigoreuda.store.controller.StoreController;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@WebMvcTest({
	StoreController.class,
})
@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles("test")
public abstract class ControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected RestDocumentationResultHandler restDocs;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected MemberInfoArgumentResolver memberInfoArgumentResolver;

	@MockBean
	protected JwtProvider jwtProvider;

	@MockBean
	protected RefreshTokenRepository refreshTokenRepository;

	@MockBean
	protected BearerTokenExtractor bearerTokenExtractor;

	@BeforeEach
	void setUp(
			final WebApplicationContext context,
			final RestDocumentationContextProvider restDocumentation
	) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation))
				.alwaysDo(restDocs)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.build();
	}

}
