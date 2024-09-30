package com.geist_chamber.geist_service.controller;

import com.geist_chamber.geist_service.security.facade.AuthenticationFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class GeistController extends AbstractUtilController {
    private final AuthenticationFacade authenticationFacade;

    public GeistController(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getMyUserData(@RequestHeader(value = "Authorization") String token) {
        return singleResponse(authenticationFacade.getAuthenticatedUser());
    }
}
