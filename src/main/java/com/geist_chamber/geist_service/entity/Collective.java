package com.geist_chamber.geist_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "collective", uniqueConstraints = {
        @UniqueConstraint(name = "uc_collective", columnNames = {"name"})
})
public class Collective {
    @Id
    @Column(name = "collective_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectiveId;
    private String name;
    private String vision;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "collective_geists",
            joinColumns = @JoinColumn(name = "collective_id"),
            inverseJoinColumns = @JoinColumn(name = "geist_id")
    )
    @ToString.Exclude
    @JsonBackReference
    private Set<Geist> geists = new HashSet<>();

    @OneToMany(mappedBy = "collective",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Realm> realms = new ArrayList<>();;
    @OneToOne
    private Geist organizedBy;
    public void addGeist(Geist geist) {
        geists.add(geist);
        geist.getCollectives().add(this);
    }
    public void removeGeist(Geist geist) {
        geists.remove(geist);
        geist.getCollectives().remove(this);
    }

    public void addRealm(Realm realm) {
        realms.add(realm);
        realm.setCollective(this);
    }

    public void removeRealm(Realm realm) {
        realms.remove(realm);
        realm.setCollective(null);
    }
}
