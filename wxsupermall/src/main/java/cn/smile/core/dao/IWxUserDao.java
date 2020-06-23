package cn.smile.core.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
@Mapper
public interface IWxUserDao {
	
	public int addUser();
}
