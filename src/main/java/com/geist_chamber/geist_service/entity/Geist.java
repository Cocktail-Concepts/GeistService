package com.geist_chamber.geist_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geist_chamber.geist_service.constant.Potency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "geist", uniqueConstraints = {
        @UniqueConstraint(name = "uc_geist", columnNames = {"geist_name", "email"})
})
@ToString
@Getter
@Setter
@RequiredArgsConstructor
public class Geist implements UserDetails {

    @Id
    @Column(name = "geist_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long geistId;
    @Column(name = "geist_name")
    private String geistname;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private String joinedDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "geist_vocations",
            joinColumns = @JoinColumn(name = "geist_id"),
            inverseJoinColumns = @JoinColumn(name = "vocation_id")
    )
    @ToString.Exclude
    @JsonBackReference
    private Set<Vocation> vocations;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String photo;
    @Enumerated
    private Set<Potency> potentiates;

    @ManyToMany(mappedBy = "geists")
    @JsonBackReference
    @ToString.Exclude
    private Set<Collective> collectives = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Geist geist = (Geist) o;
        return geistId != null && Objects.equals(geistId, geist.geistId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(this.potentiates);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.geistname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
