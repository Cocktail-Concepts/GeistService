package com.geist_chamber.geist_service.controller;

import com.geist_chamber.geist_service.dto.CollectiveDto;
import com.geist_chamber.geist_service.security.facade.AuthenticationFacade;
import com.geist_chamber.geist_service.service.CollectiveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/collectives")
public class CollectiveController extends AbstractUtilController{
    private final CollectiveService collectiveService;
    private final AuthenticationFacade authenticationFacade;

    public CollectiveController(CollectiveService collectiveService, AuthenticationFacade authenticationFacade) {
        this.collectiveService = collectiveService;
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/add-update")
    public ResponseEntity<?> createAndUpdateCollective(@RequestHeader(value = "Authorization") String token, @RequestBody CollectiveDto collectiveDto) {
        collectiveService.createAndUpdateCollective(authenticationFacade.getAuthenticatedUser(),collectiveDto);
        return successResponse("collective updated");
    }

}
