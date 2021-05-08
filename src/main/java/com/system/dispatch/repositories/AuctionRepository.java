package com.system.dispatch.repositories;

import com.system.dispatch.models.Auction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Integer> {
    @Query(value = "SELECT * FROM auction WHERE status = 'ACTIVE'", nativeQuery = true)
    List<Auction> findAllActive();
}
