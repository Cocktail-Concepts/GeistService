package com.geist_chamber.geist_service.service;

import com.geist_chamber.geist_service.repository.CollectiveRepository;
import com.geist_chamber.geist_service.dto.CollectiveDto;
import com.geist_chamber.geist_service.entity.Collective;
import com.geist_chamber.geist_service.entity.Geist;
import com.geist_chamber.geist_service.exception.RestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class CollectiveService {
    @Autowired
    private CollectiveRepository collectiveRepository;

    public void createAndUpdateCollective(Geist geist, CollectiveDto collectiveDto){
        Collective collective;
        Collective collectiveEx = collectiveRepository.findByName(collectiveDto.getName());
        if(collectiveDto.getCollectiveId() != null) {
            collective = collectiveRepository.findByCollectiveId(collectiveDto.getCollectiveId()).orElseThrow(()->new RestError(HttpStatus.NOT_FOUND,"collective not found"));
            if(collectiveEx != null && !collectiveEx.getCollectiveId().equals(collective.getCollectiveId())) throw new RestError(HttpStatus.IM_USED,"collective by this name already exists");
            if(!collective.getOrganizedBy().getGeistId().equals(geist.getGeistId())) throw new RestError(HttpStatus.UNAUTHORIZED,"you are not the organizer of this collective");
        }else {
            if (collectiveEx != null) throw new RestError(HttpStatus.IM_USED, "collective by this name already exists");
            collective = new Collective();
            collective.setOrganizedBy(geist);
            collective.addGeist(geist);
        }
        collective.setName(collectiveDto.getName());
        collective.setVision(collectiveDto.getVision());
        collectiveRepository.save(collective);
    }

}
