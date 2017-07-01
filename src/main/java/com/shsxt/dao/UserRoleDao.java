package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.shsxt.model.UserRole;

public interface UserRoleDao {

	void insertBatch(@Param(value="userRoles")List<UserRole> userRoles);
	
	@Delete("delete from t_user_role where user_id = #{userId}")
	void deleteUserRoles(@Param(value="userId")Integer userId);

}
