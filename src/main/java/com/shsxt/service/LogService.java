package com.shsxt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shsxt.dao.LogDao;
import com.shsxt.model.Log;

@Service
public class LogService {
	
	@Autowired
	private LogDao logDao;
	
	public void addLog(Log log){
		logDao.addLog(log);
	}
}
