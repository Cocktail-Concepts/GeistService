package com.geist_chamber.geist_service.dto;

import lombok.Data;

@Data
public class RealmDto {
    private Long id;
    private String name;
    private String description;
    private Long collectiveId;
    private Long parentId;
}
