package cn.smile.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
@PropertySource("classpath:db.properties")
public class DataSourceConfig {
	
	@Value("${jdbc.username}")
	private String name;
	@Value("${jdbc.password}")
	private String password;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.driver}")
	private String driver;
	
	
	@Bean
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setPassword(password);
		druidDataSource.setUsername(name);
		druidDataSource.setUrl(url);
		druidDataSource.setDriverClassName(driver);
		
		return druidDataSource;
	}
}
