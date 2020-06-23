package cn.smile.core.condition;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/18
 */
public class MyCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		BeanDefinitionRegistry registry = context.getRegistry();
		BeanDefinition redisCacheManager;
		try {
			redisCacheManager = registry.getBeanDefinition("redisCacheManager");
		} catch (NoSuchBeanDefinitionException e) {
			return true;
		}
		if (StringUtils.isEmpty(redisCacheManager)) {
			return true;
		}
		else {
			return false;
		}
	}
}
