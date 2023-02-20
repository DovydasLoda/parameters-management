package com.somesystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somesystem.entity.Parameter;
import com.somesystem.exceptions.AlreadyExistsException;
import com.somesystem.exceptions.NotFoundException;
import com.somesystem.service.ParameterService;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api")
public class ParameterController {
    @Autowired
    private ParameterService paramService;

    @GetMapping("/parameters")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(paramService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/parameters")
    public ResponseEntity<Parameter> saveParameter(@RequestBody Parameter parameter) throws AlreadyExistsException {
        if (parameter.getId() != null && parameter.getId() > 0) {
            throw new AlreadyExistsException("Parameter aldready exists! id: " + parameter.getId());
        }
        var param = paramService.createNew(parameter);
        return ResponseEntity.status(HttpStatus.CREATED).body(param);
    }

    @PutMapping("/parameters/{id}")
    public ResponseEntity<Parameter> updateTutorial(@PathVariable("id") long id, @RequestBody Parameter newParam) throws NotFoundException {
        Parameter param = paramService.getById(id)
                .orElseThrow(() -> new NotFoundException("Parameter not found with id = " + id));

        param.setAprasymas(newParam.getAprasymas());
        param.setKodas(newParam.getKodas());
        param.setPavadinimas(newParam.getPavadinimas());
        param.setGaliojaIki(newParam.getGaliojaIki());
        param.setGaliojaNuo(newParam.getGaliojaNuo());

        return new ResponseEntity<>(paramService.update(param), HttpStatus.OK);
    }

    @DeleteMapping("/parameters/{paramId}")
    public ResponseEntity<?> deleteParameter(@PathVariable long paramId) throws NotFoundException {
        Parameter param = paramService.getById(paramId)
                .orElseThrow(() -> new NotFoundException("Parameter not found: " + paramId));
        paramService.deleteParameter(param);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(param);
    }
}