package com.ase.test.controller;

import com.ase.test.logic.Verifier;
import com.ase.test.model.Request;
import com.ase.test.repository.RequestRepository;
import com.ase.test.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    SolutionRepository solutionRepository;

    @RequestMapping("test")
    public String test() {
        return "CONNECTED";
    }

    @PostMapping("/puzzle/verify")
    public ResponseEntity verify(@RequestBody Request playerRequest) {
        Verifier verifierObj = new Verifier();
        verifierObj.buildAndVerifyRequest(playerRequest);

        return new ResponseEntity(HttpStatus.OK);
    }
}
