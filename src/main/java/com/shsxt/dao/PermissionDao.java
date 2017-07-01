package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shsxt.model.Permission;

public interface PermissionDao {
	
	
	@Select("select count(1) from t_permission where "
			+ " role_id = #{roleId} and module_id in (${moduleIds}) "
			+ "and is_valid = 1 ")
	Integer countByIds(@Param(value="roleId") Integer roleId, @Param(value="moduleIds")String moduleIds);
	
	@Select("select count(1) from t_permission where "
			+ " role_id = #{roleId} and module_id = #{moduleId} and is_valid = 1")
	Integer count(@Param(value="roleId") Integer roleId, @Param(value="moduleId")Integer moduleId);
	
	@Delete("DELETE FROM t_permission where role_id = #{roleId} AND module_id in (${moduleIds}) ")
	void releaseModules(@Param(value="roleId")Integer roleId, @Param(value="moduleIds")String moduleIds);
	
	@Delete("DELETE FROM t_permission where role_id = #{roleId} AND module_id = #{moduleId} ")
	void releaseModule(@Param(value="roleId")Integer roleId, @Param(value="moduleId")Integer moduleId);
	
	void insertBatch(@Param(value="permissions")List<Permission> permissions);
	
}
