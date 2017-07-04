package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shsxt.model.CusDevPlan;

public interface CusDevPlanDao {
	@Select("select id, plan_item, plan_date, exe_affect, sale_chance_id "
			+ "from t_cus_dev_plan where sale_chance_id = #{saleChanceId} and is_valid = 1")
	List<CusDevPlan> findSaleChancePlans(@Param(value="saleChanceId")Integer saleChanceId);
	
	@Insert("insert into t_cus_dev_plan (plan_item, plan_date, exe_affect, "
			+ "create_date, update_date, is_valid, sale_chance_id) values(#{planItem}, #{planDate}"
			+ ",#{exeAffect}, #{createDate}, #{updateDate},#{isValid}, #{saleChanceId})")
	@Options(useGeneratedKeys = true, keyProperty="id")
	void insert(CusDevPlan cusDevPlan);
	
	@Update("update t_cus_dev_plan set plan_item = #{planItem}, plan_date = #{planDate}, "
			+ "exe_affect = #{exeAffect}, update_date = #{updateDate} where id = #{id}")
	void update(CusDevPlan cusDevPlan);
	
	@Update("update t_cus_dev_plan set is_valid = 0 where id = #{id}")
	void delete(@Param(value="id")Integer id);
	
}
