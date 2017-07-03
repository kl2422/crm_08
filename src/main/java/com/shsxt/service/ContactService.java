package com.shsxt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.base.AssertUtil;
import com.shsxt.dao.ContactDao;
import com.shsxt.dto.ContactQuery;
import com.shsxt.model.Contact;

@Service
public class ContactService {
	
	@Autowired
	private ContactDao contactDao;

	public Map<String, Object> selectForPage(ContactQuery query) {
		PageList<Contact> contacts = contactDao.selectForPage(query, query.buildPageBounds());
		Paginator paginator = contacts.getPaginator(); // 分页对象
		Map<String, Object> result = new HashMap<>();
		result.put("paginator", paginator);
		result.put("rows", contacts);
		result.put("total", paginator.getTotalCount());
		return result;
	}

	public void save(Contact contact) {
		// 基本参数验证 TODO
		contactDao.insert(contact);
	}

	public void update(Contact contact) {
		// 基本参数验证TODO
		AssertUtil.intIsNotEmpty(contact.getId(), "请选择一条记录进行更新");
		
		contactDao.update(contact);
	}

	public void delete(Integer id) {
		AssertUtil.intIsNotEmpty(id, "请选择要删除的记录");
		contactDao.delete(id);
	}

}
