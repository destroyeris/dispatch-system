package com.system.dispatch.repositories;

import com.system.dispatch.models.SoldItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldItemRepository extends CrudRepository<SoldItem, Integer> {
}
