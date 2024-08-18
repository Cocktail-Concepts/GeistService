package com.geist_chamber.geist_service.repository;

import com.geist_chamber.geist_service.entity.Collective;
import com.geist_chamber.geist_service.entity.Realm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Long> {
    Optional<Realm> findByRealmId(Long id);

    Realm findByNameAndCollective(String name, Collective collective);
}