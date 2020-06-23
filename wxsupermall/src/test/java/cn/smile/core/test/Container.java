package cn.smile.core.test;

import cn.smile.core.config.RootApplicationConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/18
 */
public class Container {
	
	@Test
	
	public void test() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootApplicationConfig.class);
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println(beanDefinitionName);
		}
	}
	
}
