package com.winning.devops.spring.boot.elasticsearch.repository;

import com.winning.devops.spring.boot.elasticsearch.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * User Dao
 * @author chensj
 * @date 2019-06-08 22:08
 */
@Repository
public interface UserRepository extends CrudRepository<User,String> {
}
