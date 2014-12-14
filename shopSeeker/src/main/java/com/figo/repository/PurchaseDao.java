package com.figo.repository;

import com.figo.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by figo on 14/10/30.
 */
public interface PurchaseDao extends PagingAndSortingRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {
    Page<Purchase> findByUserId(Long id, Pageable pageRequest);
    List<Purchase> findByItemId(Long itemId);
}
