package cn.smile.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"cn.smile.core.controller"}, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
}, useDefaultFilters = false)
public class WebApplicationConfig implements WebMvcConfigurer {
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//		fastJsonHttpMessageConverter.setDefaultCharset(Charset.defaultCharset());
		List<MediaType> list =new ArrayList<>();
		list.add(MediaType.APPLICATION_ATOM_XML);
		list.add(MediaType.TEXT_HTML);
		list.add(MediaType.IMAGE_JPEG);
		list.add(MediaType.IMAGE_PNG);
//		fastJsonHttpMessageConverter.setSupportedMediaTypes(list);
//		converters.add(fastJsonHttpMessageConverter);
		StringHttpMessageConverter httpMessageConverter = new StringHttpMessageConverter();
		httpMessageConverter.setDefaultCharset(Charset.defaultCharset());
		
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		messageConverter.setSupportedMediaTypes(list);
		
		converters.add(httpMessageConverter);
		converters.add(messageConverter);
	}
}
