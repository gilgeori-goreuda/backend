package com.pd.gilgeorigoreuda.search.resolver;

import com.pd.gilgeorigoreuda.statistics.event.KeywordEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SearchParamsArgumentResolver implements HandlerMethodArgumentResolver {

    private final ApplicationEventPublisher publisher;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SearchParams.class);
    }

    @Override
    public SearchParameter resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        Map<String, String> parameterMap = new HashMap<>();

        // m_lat, m_lng, r_lat, r_lng, street_address, food_type
        Enumeration<String> parameterNames = nativeRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            parameterMap.put(name, nativeRequest.getParameter(name));
        }

        keywordEventPublish(parameterMap);

        return SearchParameter.of(
                parameterMap.getOrDefault("m_lat", "0"),
                parameterMap.getOrDefault("m_lng", "0"),
                parameterMap.getOrDefault("r_lat", "0"),
                parameterMap.getOrDefault("r_lng", "0"),
                parameterMap.getOrDefault("food_type", "")
        );
    }

    private void keywordEventPublish(final Map<String, String> parameterMap) {
        String streetAddress = parameterMap.get("street_address");
        if (streetAddress != null && !streetAddress.isBlank()) {
            publisher.publishEvent(new KeywordEvent(streetAddress));
        }
    }

}
