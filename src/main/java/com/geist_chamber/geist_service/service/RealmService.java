package com.geist_chamber.geist_service.service;

import com.geist_chamber.geist_service.dto.RealmDto;
import com.geist_chamber.geist_service.entity.Collective;
import com.geist_chamber.geist_service.entity.Geist;
import com.geist_chamber.geist_service.entity.Realm;
import com.geist_chamber.geist_service.exception.RestError;
import com.geist_chamber.geist_service.repository.CollectiveRepository;
import com.geist_chamber.geist_service.repository.RealmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RealmService {
    private final RealmRepository realmRepository;
    private final CollectiveRepository collectiveRepository;

    public RealmService(RealmRepository realmRepository, CollectiveRepository collectiveRepository) {
        this.realmRepository = realmRepository;
        this.collectiveRepository = collectiveRepository;
    }

    public void createAndUpdateRealm(Geist geist, RealmDto realmDto) {
        Realm realm;
        Collective collective = collectiveRepository.findByCollectiveId(realmDto.getCollectiveId()).orElseThrow();
        if(!collective.getGeists().contains(geist)) throw new RestError(HttpStatus.UNAUTHORIZED,"you are not member of this collective");
        Realm realmEx = realmRepository.findByNameAndCollective(realmDto.getName(),collective);
        if(realmDto.getId() != null){
            realm = realmRepository.findByRealmId(realmDto.getId()).orElseThrow(()->new RestError(HttpStatus.NOT_FOUND,"realm not found"));
            if(realmEx != null && !realmEx.getRealmId().equals(realm.getRealmId())) throw new RestError(HttpStatus.IM_USED,"realm already exist");
        }else{
            if(realmEx != null) throw new RestError(HttpStatus.IM_USED,"realm already exist");
            realm = new Realm();
            realm.setCollective(collective);
            collective.addRealm(realm);
        }
        realm.setName(realmDto.getName());
        realm.setDescription(realmDto.getDescription());
        if(realmDto.getParentId() != null){
            Realm parent = realmRepository.findByRealmId(realmDto.getParentId()).orElseThrow();
            realm.setParent(parent);
        }
        collectiveRepository.save(collective);
    }
}
