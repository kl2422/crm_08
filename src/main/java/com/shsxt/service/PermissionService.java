package com.shsxt.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shsxt.base.AssertUtil;
import com.shsxt.dao.ModuleDao;
import com.shsxt.dao.PermissionDao;
import com.shsxt.model.Module;
import com.shsxt.model.Permission;

@Service
public class PermissionService {
	
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ModuleDao moduleDao;
	
	/**
	 * 角色关联权限
	 * @param roleId
	 * @param moduleId
	 * @param checked
	 */
	public void addDoRelate(Integer roleId, Integer moduleId, boolean checked) {
		// 基本参数验证
		AssertUtil.intIsNotEmpty(roleId, "请选择角色");
		AssertUtil.intIsNotEmpty(moduleId, "请选择模块");
		
		// 验证角色以及模块
		roleService.findById(roleId);
		Module module = moduleService.findById(moduleId);
		
		if (checked) { // 授权
			bindPermission(roleId, moduleId, module);
		} else {
			// 先把自己解绑
			permissionDao.releaseModule(roleId, moduleId);
			// 解绑子模块
			releaseSonModules(module, roleId);
		}
	}
	
	/**
	 * 赋予权限
	 * @param roleId
	 * @param moduleId
	 * @param checked
	public void addDoRelate(Integer roleId, Integer moduleId, boolean checked) {
		// 基本参数验证
		AssertUtil.intIsNotEmpty(roleId, "请选择角色");
		AssertUtil.intIsNotEmpty(moduleId, "请选择模块");
		// 验证角色以及模块
		roleService.findById(roleId);
		Module module = moduleService.findById(moduleId);
		if (checked) { // 授权
			List<Permission> permissions = new ArrayList<>();
			// 绑定当前模块
			build(roleId, moduleId, module.getOptValue(), permissions);
			
			// 绑定父模块
			if (module.getParentId() != null) { // 有父级
				String treePath = module.getTreePath();
				if (module.getGrade() == ModuleGrade.FIRST.getGrade()) { // 如果是第一层的话就只有一个根节点
					Module parentModule = moduleService.findById(module.getParentId());
					// 查询是否已绑定
					Integer count = permissionDao.count(roleId, parentModule.getId());
					if (count == null || count == 0) {
						build(roleId, parentModule.getId(), parentModule.getOptValue(), permissions);
					}
				} else {
					// ,1,2,
					String ancestorIds = treePath.substring(treePath.indexOf(",") + 1, treePath.lastIndexOf(","));
					List<Module> ancestorModules = moduleDao.findByIds(ancestorIds);
					for (Module ancestorModule : ancestorModules) {
						Integer count = permissionDao.count(roleId, ancestorModule.getId());
						if (count == null || count == 0) {
							build(roleId, ancestorModule.getId(), ancestorModule.getOptValue(), permissions);
						}
					}
				}
			}
			// 绑定子模块
			String treePath = null;
			if (StringUtils.isBlank(module.getTreePath())) {
				treePath = "," + moduleId + ",";			
			} else {
				treePath = module.getTreePath() + moduleId + ",";
			}
			List<Module> sunModules = moduleDao.findSunModules(treePath);
			if (sunModules != null && sunModules.size() > 0) {
				// 先把所有的子模块解绑
				StringBuffer sunModuleIds = new StringBuffer();
				for (Module sunModule : sunModules) {
					sunModuleIds.append(sunModule.getId()).append(",");
					// 添加到list
					build(roleId, sunModule.getId(), sunModule.getOptValue(), permissions);
				}
				// 解绑
				permissionDao.releaseModules(roleId, sunModuleIds.substring(0, sunModuleIds.length() - 1));
			}
			
			// 批量保存
			permissionDao.insertBatch(permissions);
			
		} else {
			// 先把自己解绑
			permissionDao.releaseModule(roleId, moduleId);
			// 把子模块删除
			String treePath = null;
			if (StringUtils.isBlank(module.getTreePath())) {
				treePath = "," + moduleId + ",";			
			} else {
				treePath = module.getTreePath() + moduleId + ",";
			}
			List<Module> sunModules = moduleDao.findSunModules(treePath);
			if (sunModules != null && sunModules.size() > 0) {
				// 先把所有的子模块解绑
				StringBuffer sunModuleIds = new StringBuffer();
				for (Module sunModule : sunModules) {
					sunModuleIds.append(sunModule.getId()).append(",");
				}
				// 解绑
				permissionDao.releaseModules(roleId, sunModuleIds.substring(0, sunModuleIds.length() - 1));
			}
		}
	}*/
	
	/**
	 * 绑定权限
	 * @param roleId
	 * @param moduleId
	 * @param module
	 */
	private void bindPermission(Integer roleId, Integer moduleId, Module module) {
		
		List<Permission> permissions = new ArrayList<>();
		// 绑定当前模块
		build(roleId, moduleId, module.getOptValue(), permissions);
		
		// 绑定父模块
		if (module.getParentId() != null) { // 有父级
			setParentPermission(roleId, module, permissions);
		}
		// 绑定子模块
		bindSonModules(module, roleId, permissions);
		
		// 批量保存
		permissionDao.insertBatch(permissions);
	}
	
	/**
	 * 设置父权限
	 * @param roleId
	 * @param module
	 * @param permissions
	 */
	private void setParentPermission(Integer roleId, Module module, List<Permission> permissions) {
		String treePath = module.getTreePath();
		// ,1,2,
		String ancestorIds = treePath.substring(treePath.indexOf(",") + 1, treePath.lastIndexOf(","));
		List<Module> ancestorModules = moduleDao.findByIds(ancestorIds);
		for (Module ancestorModule : ancestorModules) {
			Integer count = permissionDao.count(roleId, ancestorModule.getId()); // 查看是否已绑定
			if (count == null || count == 0) {
				build(roleId, ancestorModule.getId(), ancestorModule.getOptValue(), permissions);
			}
		}
	}
	
	/**
	 * 绑定子模块
	 * @param module
	 * @param roleId
	 * @param permissions
	 */
	private void bindSonModules(Module module, Integer roleId, List<Permission> permissions ) {
		
		// 先解绑
		List<Module> sonModules = releaseSonModules(module, roleId);
		if (sonModules == null || sonModules.isEmpty()) {
			return;
		}
		// 添加到list
		for (Module sunModule : sonModules) {
			// 添加到list
			build(roleId, sunModule.getId(), sunModule.getOptValue(), permissions);
		}
	}
	
	/**
	 * 获取子模块
	 * @param module
	 * @return
	 */
	private List<Module> findSonModules(Module module) {
		String treePath = null;
		if (StringUtils.isBlank(module.getTreePath())) {
			treePath = "," + module.getId() + ",";			
		} else {
			treePath = module.getTreePath() + module.getId() + ",";
		}
		List<Module> sonModules = moduleDao.findSunModules(treePath);
		return sonModules;
	}
	
	/**
	 * 解绑子模块
	 * @param module
	 * @param roleId
	 */
	private List<Module> releaseSonModules(Module module, Integer roleId) {
		// 查询子模块
		List<Module> sonModules = findSonModules(module);
		if (sonModules == null || sonModules.isEmpty()) {
			return Collections.emptyList(); // new ArrayList<>();
		}
		// 先把所有的子模块解绑
		StringBuffer sunModuleIds = new StringBuffer();
		for (Module sunModule : sonModules) {
			sunModuleIds.append(sunModule.getId()).append(",");
		}
		// 解绑
		permissionDao.releaseModules(roleId, sunModuleIds.substring(0, sunModuleIds.length() - 1));
		return sonModules;
	}
	
	private static void build(Integer roleId, Integer moduleId, 
			String optValue, List<Permission> permissions) {
		Permission permission = new Permission();
		permission.setRoleId(roleId);
		permission.setModuleId(moduleId);
		permission.setAclValue(optValue);
		permissions.add(permission);
	}
	
	
	
}
