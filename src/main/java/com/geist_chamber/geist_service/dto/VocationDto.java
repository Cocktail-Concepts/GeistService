package com.geist_chamber.geist_service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.geist_chamber.geist_service.entity.Collective;
import com.geist_chamber.geist_service.entity.Geist;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
public class VocationDto {
    private Long vocationId;
    private String name;
    private String description;
    private Long collectiveId;
}
