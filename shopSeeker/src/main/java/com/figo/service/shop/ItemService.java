package com.figo.service.shop;

import com.figo.entity.Item;
import com.figo.repository.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import java.util.List;
import java.util.Map;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional
public class ItemService {

	private ItemDao itemDao;

	public Item getItem(Long id) {
		return itemDao.findOne(id);
	}

	public void saveItem(Item entity) {
        itemDao.save(entity);
	}

	public void deleteItem(Long id) {
        itemDao.delete(id);
	}

	public List<Item> getAllItem() {
		return (List<Item>) itemDao.findAll();
	}

	public Page<Item> getUserItem(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Item> spec = buildSpecification(userId, searchParams);

		return itemDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Item> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<Item> spec = DynamicSpecifications.bySearchFilter(filters.values(), Item.class);
		return spec;
	}

	@Autowired
	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
}
