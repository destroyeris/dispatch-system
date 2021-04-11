package com.system.dispatch.repositories;

import com.system.dispatch.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {
}
