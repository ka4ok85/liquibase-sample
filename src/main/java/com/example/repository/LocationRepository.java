package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

}
