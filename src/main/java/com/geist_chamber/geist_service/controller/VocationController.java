package com.geist_chamber.geist_service.controller;

import com.geist_chamber.geist_service.dto.RealmDto;
import com.geist_chamber.geist_service.dto.VocationDto;
import com.geist_chamber.geist_service.security.facade.AuthenticationFacade;
import com.geist_chamber.geist_service.service.RealmService;
import com.geist_chamber.geist_service.service.VocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/vocations")
public class VocationController extends AbstractUtilController{
    private final AuthenticationFacade authenticationFacade;
    private final VocationService vocationService;

    public VocationController(AuthenticationFacade authenticationFacade, VocationService vocationService) {
        this.authenticationFacade = authenticationFacade;
        this.vocationService = vocationService;
    }


    @PostMapping("/add-update")
    public ResponseEntity<?> createAndUpdateCollective(@RequestHeader(value = "Authorization") String token, @RequestBody VocationDto vocationDto) {
        vocationService.createAndUpdateVocation(vocationDto);
        return successResponse("vocation updated");
    }
}
