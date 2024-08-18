package com.geist_chamber.geist_service.service;

import com.geist_chamber.geist_service.dto.VocationDto;
import com.geist_chamber.geist_service.entity.Collective;
import com.geist_chamber.geist_service.entity.Vocation;
import com.geist_chamber.geist_service.exception.RestError;
import com.geist_chamber.geist_service.repository.CollectiveRepository;
import com.geist_chamber.geist_service.repository.VocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class VocationService {
    private final VocationRepository vocationRepository;
    private final CollectiveRepository collectiveRepository;

    public VocationService(VocationRepository vocationRepository, CollectiveRepository collectiveRepository) {
        this.vocationRepository = vocationRepository;
        this.collectiveRepository = collectiveRepository;
    }

    public void createAndUpdateVocation(VocationDto vocationDto) {
        Vocation vocation;
        Collective collective = collectiveRepository.findByCollectiveId(vocationDto.getCollectiveId()).orElseThrow();
        Vocation realmEx = vocationRepository.findByNameAndCollective(vocationDto.getName(), collective);
        if (vocationDto.getVocationId() != null) {
            vocation = vocationRepository.findByVocationId(vocationDto.getVocationId()).orElseThrow(() -> new RestError(HttpStatus.NOT_FOUND, "realm not found"));
            if (realmEx != null && !realmEx.getVocationId().equals(vocation.getVocationId()))
                throw new RestError(HttpStatus.IM_USED, "realm already exist");
        } else {
            if (realmEx != null) throw new RestError(HttpStatus.IM_USED, "realm already exist");
            vocation = new Vocation();
            vocation.setCollective(collective);
        }
        vocation.setName(vocationDto.getName());
        vocation.setDescription(vocationDto.getDescription());
        vocationRepository.save(vocation);
    }
}
