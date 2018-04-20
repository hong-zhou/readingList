package com.hongzhou.readinglist.config;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hongzhou.readinglist.domain.Reader;

public class ReaderHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// TODO Auto-generated method stub
		return Reader.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		Authentication auth = (Authentication) webRequest.getUserPrincipal();
		return auth != null && auth.getPrincipal() instanceof Reader ? auth.getPrincipal() : null;
	}
}
