package cn.smile.core.service.Imp;

import cn.smile.core.service.IWxUserService;
import org.springframework.cache.annotation.CachePut;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
public class IWxUserServiceImpl implements IWxUserService {
	
	/**
	 * @Cacheable(value = "")  => 获取数据时候先从缓存中获取,没有的再从数据库中获取,然后吧数据库中获取到的值保存到redis中
	 *
	 *
	 * @CachePut() => 把添加的数据保存到redis中,使用这注解必须有返回值
	 *
	 */
	
	//告诉spring使用哪个一个缓存
	//spring中提供一个root根属性获取对应类的名称和方法名称
	@CachePut(value = "smileCache")
	@Override
	public int addUser() {
		return 0;
	}
}
