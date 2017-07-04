package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.model.Module;
import com.shsxt.vo.ModuleVO;

public interface ModuleDao {
	
	PageList<Module> selectForPage(PageBounds buildPageBounds);

	Module findByModuleName(@Param("moduleName")String moduleName, @Param("parentId")Integer parentId);

	Module findByOptValue(@Param("optValue")String optValue);

	void insert(Module module);

	Module findById(@Param("id")Integer id);

	void update(Module moduleFromDb);

	void deleteBatch(@Param("ids")String ids);
	
	@Select("select id, module_name from t_module where "
			+ "is_valid=1 and grade = #{grade} order by orders")
	List<Module> findByGrade(@Param("grade")Integer grade);
	
	@Select("select id, module_name, parent_id from t_module where is_valid=1 order by parent_id, orders")
	@ResultType(value=ModuleVO.class)
	List<ModuleVO> findAll();
	
	List<Module> findByIds(@Param(value="ids")String ids);
	
	@Select("select id, module_name, parent_id, opt_value "
			+ " from t_module where is_valid=1 and tree_path LIKE '${treePath}%'")
	List<Module> findSunModules(@Param(value="treePath")String treePath);
}
