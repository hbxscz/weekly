package com.figo.repository;

import com.figo.entity.Item;
import com.figo.entity.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by figo on 14/10/30.
 */
public interface ItemDao extends PagingAndSortingRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    Page<Item> findByUserId(Long id, Pageable pageRequest);
}
