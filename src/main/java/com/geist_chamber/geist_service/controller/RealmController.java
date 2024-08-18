package com.geist_chamber.geist_service.controller;

import com.geist_chamber.geist_service.dto.CollectiveDto;
import com.geist_chamber.geist_service.dto.RealmDto;
import com.geist_chamber.geist_service.security.facade.AuthenticationFacade;
import com.geist_chamber.geist_service.service.CollectiveService;
import com.geist_chamber.geist_service.service.RealmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/realms")
public class RealmController extends AbstractUtilController{
    private final AuthenticationFacade authenticationFacade;
    private final RealmService realmService;

    public RealmController(AuthenticationFacade authenticationFacade, RealmService realmService) {
        this.authenticationFacade = authenticationFacade;
        this.realmService = realmService;
    }

    @PostMapping("/add-update")
    public ResponseEntity<?> createAndUpdateCollective(@RequestHeader(value = "Authorization") String token, @RequestBody RealmDto realmDto) {
        realmService.createAndUpdateRealm(authenticationFacade.getAuthenticatedUser(),realmDto);
        return successResponse("realm updated");
    }
}
