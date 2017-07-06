package com.shsxt.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.ProductQuery;
import com.shsxt.model.Product;

public interface ProductDao {

	PageList<Product> selectForPage(ProductQuery productQuery, PageBounds buildPageBounds);

}
