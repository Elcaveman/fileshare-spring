package com.fileshare.filepool.repository;

import com.fileshare.filepool.model.Filepool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilepoolRepository extends CrudRepository<Filepool, Long> {
}
