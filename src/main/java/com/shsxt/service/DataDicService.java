package com.shsxt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shsxt.base.AssertUtil;
import com.shsxt.dao.DataDicDao;
import com.shsxt.model.DataDic;

@Service
public class DataDicService {
	
	@Autowired
	private DataDicDao dataDicDao;

	public List<DataDic> findByName(String dataDicName) {
		AssertUtil.isNotEmpty(dataDicName, "请选择查询的名称");
		List<DataDic> dataDics = dataDicDao.findByName(dataDicName);
		return dataDics;
	}

}
