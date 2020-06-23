package cn.smile.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
@Configuration
@Import(value = { MybatisConfig.class, TransactionConfig.class, DataSourceConfig.class, RedisConfig.class})
@EnableTransactionManagement
@ComponentScan(basePackages = {"cn.smile.core"}, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
public class RootApplicationConfig {
}
