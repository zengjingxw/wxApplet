package cn.smile.core.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
public class WebXmlConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootApplicationConfig.class };
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebApplicationConfig.class };
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "*.wx" };
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter [] filters = new CharacterEncodingFilter[1];
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceResponseEncoding(true);
		characterEncodingFilter.setForceRequestEncoding(true);
		characterEncodingFilter.setForceEncoding(true);
		filters[0] = characterEncodingFilter;
		return filters;
	}
	
	
	@Override
	protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
		super.registerServletFilter(servletContext, filter);
		FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("encDing", filter);
		filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),false,"/*");
		return filterRegistration;
	}
}
