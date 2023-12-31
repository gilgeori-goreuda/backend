package com.pd.gilgeorigoreuda.common.config.web;

import com.pd.gilgeorigoreuda.auth.resolver.MemberInfoArgumentResolver;
import com.pd.gilgeorigoreuda.search.resolver.SearchParamsArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ResolverConfig implements WebMvcConfigurer {

    private final MemberInfoArgumentResolver memberInfoArgumentResolver;
    private final SearchParamsArgumentResolver searchParamsArgumentResolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver);
        resolvers.add(searchParamsArgumentResolver);
    }

}
