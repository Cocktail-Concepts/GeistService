package com.geist_chamber.geist_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
@RequiredArgsConstructor
public class Realm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long realmId;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "collective_id")
    private Collective collective;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Realm parent;
}
