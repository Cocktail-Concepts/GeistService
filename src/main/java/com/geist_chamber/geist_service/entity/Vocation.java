package com.geist_chamber.geist_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "vocation")
public class Vocation  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "vocation_id", nullable = false)
    private Long vocationId;
    @Column(unique= true , nullable = false)
    private String name;
    private String description;
    @ManyToMany(mappedBy = "vocations")
    @JsonBackReference
    @ToString.Exclude
    private Set<Geist> geists;
    @ManyToOne
    @JoinColumn(name = "collective_id")
    private Collective collective;

    public Long getVocationId() {
        return vocationId;
    }

    public void setId(Long id) {
        this.vocationId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vocation vocation = (Vocation) o;
        return vocationId != null && Objects.equals(vocationId, vocation.vocationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
