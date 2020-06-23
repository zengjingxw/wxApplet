package cn.smile.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
public class TransactionConfig {

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
		DataSourceTransactionManager d = new DataSourceTransactionManager();
		d.setDataSource(dataSource);
		return d;
	}
}
