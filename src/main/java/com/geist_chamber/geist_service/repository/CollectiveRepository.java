package com.geist_chamber.geist_service.repository;

import com.geist_chamber.geist_service.entity.Collective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectiveRepository extends JpaRepository<Collective, Long> {
    boolean existsByName(String name);

    Optional<Collective> findByCollectiveId(Long collectiveId);

    Collective findByName(String name);
}