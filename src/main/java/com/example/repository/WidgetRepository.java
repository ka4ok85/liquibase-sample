package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Widget;

@Repository
public interface WidgetRepository extends CrudRepository<Widget, Long> {

}
