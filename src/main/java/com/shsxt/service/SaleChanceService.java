package com.shsxt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.dao.SaleChanceDao;
import com.shsxt.dto.SaleChanceQuery;
import com.shsxt.exception.ParamException;
import com.shsxt.model.SaleChance;

@Service
public class SaleChanceService {
	
	@Autowired
	private SaleChanceDao saleChanceDao;
	
	private static Logger logger = LoggerFactory.getLogger(SaleChanceService.class);
	
	/**
	 * 查询列表
	 * @param query
	 * @return
	 */
	public Map<String, Object> selectForPage(SaleChanceQuery query) {
		// 分页查询 构建一个PageBounds-->dao返回PageList-->sql
		if (query.getPage() == null || query.getPage() < 1) {
			query.setPage(1);
		}
		if (query.getRows() == null || query.getRows() < 1) {
			query.setRows(10);
		}
		PageBounds pageBounds = new PageBounds(query.getPage(), query.getRows());
		
		PageList<SaleChance> saleChances = saleChanceDao.selectForPage(query, pageBounds);
		Paginator paginator = saleChances.getPaginator(); // 分页对象
		Map<String, Object> result = new HashMap<>();
		result.put("paginator", paginator);
		result.put("rows", saleChances);
		result.put("total", paginator.getTotalCount());
		return result;
	}
	
	/**
	 * 添加
	 * @param saleChance
	 */
	public void addOrUpdate(SaleChance saleChance, String userName) {
		// 基本参数验证
		if (saleChance.getCustomerId() == null || saleChance.getCustomerId() < 1) {
			throw new ParamException("请选择客户");
		}
		if (saleChance.getCgjl() == null || saleChance.getCgjl() < 1) {
			throw new ParamException("请输入成功几率");
		}
		saleChance.setCreateMan(userName);
		Integer id = saleChance.getId();
		SaleChance saleChanceFromDB = null;
		if (id != null) { // 验证一下该记录是否存在
			saleChanceFromDB = saleChanceDao.findById(id);
			if (saleChanceFromDB == null) {
				throw new ParamException("该记录不存在");
			}
		}
		// 是否有过分配
		String assignMan = saleChance.getAssignMan();
		if(StringUtils.isNoneBlank(assignMan)) {
			if (id != null) { // 修改指派时间
				if (!saleChance.getAssignMan().equals(saleChanceFromDB.getAssignMan())) {
					saleChance.setAssignTime(new Date());
				} else {
					saleChance.setAssignTime(saleChanceFromDB.getAssignTime());
				}
			} else {
				saleChance.setAssignTime(new Date());
			}
			saleChance.setState(1);
		} else {
			saleChance.setState(0);
		}
		
		saleChance.setDevResult(0);
		
		if (id == null) { // 新增
			saleChance.setCreateDate(new Date());
			saleChance.setIsValid(1);
		}
		saleChance.setUpdateDate(new Date());
		if (id == null) { // 添加
			saleChanceDao.insert(saleChance);
			logger.info("插入返回主键：{}", saleChance.getId());
		} else {
			saleChanceDao.update(saleChance);
		}
		
	}
	
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new ParamException("请选择记录进行删除");
		}
		saleChanceDao.delete(ids);
	}

}
