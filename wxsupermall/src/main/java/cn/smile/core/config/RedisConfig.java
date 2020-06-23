package cn.smile.core.config;

import cn.smile.core.condition.MyCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
@Configuration
@EnableCaching
@PropertySource("classpath:db.properties")
public class RedisConfig {
	JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
	GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
	
	@Value("${redis.maxTotal}")
	private Integer maxTotal;
	@Value("${redis.maxIdle}")
	private Integer maxIdle;
	@Value("${redis.url}")
	private String hostName;
	@Value("${redis.password}")
	private String password;
	@Value("${redis.port}")
	private Integer port;
	
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxIdle(maxIdle);
		return jedisPoolConfig;
	}
	
	
	@Bean
	@Scope(scopeName ="prototype")
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setPassword(password);
		factory.setHostName(hostName);
		factory.setPassword(password);
		factory.setPoolConfig(jedisPoolConfig());
		return factory;
	}
	
//	@Bean
//	public LettuceConnectionFactory lettuceConnectionFactory() {
//
//		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//		redisStandaloneConfiguration.setHostName(hostName);
//		redisStandaloneConfiguration.setPassword(password);
//		redisStandaloneConfiguration.setPort(port);
//		LettuceConnectionFactory lettuceConnectionFactor = new LettuceConnectionFactory(redisStandaloneConfiguration);
//		return lettuceConnectionFactor;
//	}
	
	@Bean
	public StringRedisSerializer stringRedisSerializer() {
		return new StringRedisSerializer();
	}
	
//====================================RedisTemplate===================================================================
	@Bean
	@Primary
	public RedisTemplate redisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(redisSerializer);
		return redisTemplate;
	}
	
	@Bean(name ="redisTemplateDataTwo" )
	public RedisTemplate redisTemplateDataTwo() {
		RedisTemplate redisTemplate = new RedisTemplate();
		JedisConnectionFactory factory = jedisConnectionFactory();
		factory.setDatabase(1);
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(redisSerializer);
		return redisTemplate;
	}
	
//=================================/RedisTemplate===========================================
	@Bean
	@Primary
	public StringRedisTemplate stringRedisTemplate() {
    	StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
    	stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
		stringRedisTemplate.setValueSerializer(stringRedisSerializer());
    	stringRedisTemplate.setKeySerializer(stringRedisSerializer());
    	return stringRedisTemplate;
	}
	
	@Bean(name = "stringRedisTemplateTwo")
	public StringRedisTemplate stringRedisTemplateTwo() {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		JedisConnectionFactory factory = jedisConnectionFactory();
		factory.setDatabase(1);
		stringRedisTemplate.setConnectionFactory(factory);
		stringRedisTemplate.setValueSerializer(stringRedisSerializer());
		stringRedisTemplate.setKeySerializer(stringRedisSerializer());
		return stringRedisTemplate;
	}
	
	@Bean
	public RedisCacheManager redisCacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
		return redisCacheManager;
	}
	
	//配置缓存管理器
	@Bean
	@Conditional(value = { MyCondition.class })
	public SimpleCacheManager simpleCacheManager() {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//		ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
//		cacheFactoryBean.setName("default");
//		RedisCache cache = new RedisCache("smileCache",null,redisTemplate(),100L);
		return simpleCacheManager;
	}
	
	//配置key生成器
	@Bean
	public SimpleKeyGenerator simpleKeyGenerator() {
		return new SimpleKeyGenerator();
	}
}
