package com.figo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.figo.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}
