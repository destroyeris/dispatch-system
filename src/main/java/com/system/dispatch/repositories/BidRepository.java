package com.system.dispatch.repositories;

import com.system.dispatch.models.Bid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends CrudRepository<Bid, Integer> {
}
