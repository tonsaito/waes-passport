package com.waes.passport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waes.passport.entity.DiffEntity;

/**
 * Interface DiffRepository reference of the DiffEntity class
 * @author tonsaito
 *
 */
@Repository
public interface DiffRepository extends CrudRepository<DiffEntity, Long>{

}
