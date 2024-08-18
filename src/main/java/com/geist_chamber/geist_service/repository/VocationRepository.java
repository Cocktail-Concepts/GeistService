package com.geist_chamber.geist_service.repository;

import com.geist_chamber.geist_service.entity.Collective;
import com.geist_chamber.geist_service.entity.Vocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VocationRepository extends JpaRepository<Vocation,Long> {
    Vocation findByNameAndCollective(String name, Collective collective);

    Optional<Vocation> findByVocationId(Long vocationId);
}
