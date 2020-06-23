package cn.smile.core.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
@MapperScan
public class MybatisConfig {
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setTypeAliasesPackage("cn.smile.core.entity");
		return sqlSessionFactory;
	}
}
