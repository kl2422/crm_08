package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.DataDicQuery;
import com.shsxt.model.DataDic;

public interface DataDicDao {
	
	@Select("select id, data_dic_name, data_dic_value from t_datadic where data_dic_name = #{name}")
	List<DataDic> findByName(@Param(value="name") String name);

	PageList<DataDic> selectForPage(DataDicQuery query, PageBounds buildPageBounds);

	List<DataDic> findAll();
	
	void insert(DataDic dataDic);

	void update(DataDic dataDic);

	void deleteBatch(String ids);

}
