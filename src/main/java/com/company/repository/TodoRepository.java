package com.company.repository;

import com.company.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bernard-w on 29/10/17.
 */
@Repository
public interface TodoRepository extends CrudRepository<Todo,Integer>{

}
