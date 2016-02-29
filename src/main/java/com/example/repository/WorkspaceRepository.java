package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Workspace;

@Repository
public interface WorkspaceRepository extends CrudRepository<Workspace, Long> {

}
