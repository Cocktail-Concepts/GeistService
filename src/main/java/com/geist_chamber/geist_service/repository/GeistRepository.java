package com.geist_chamber.geist_service.repository;

import com.geist_chamber.geist_service.entity.Geist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeistRepository extends JpaRepository<Geist,Long> {

    Geist findByGeistname(String username);

    Geist findByEmail(String email);
}
