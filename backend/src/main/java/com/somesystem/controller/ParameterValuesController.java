package com.somesystem.controller;


import java.util.List;

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
import com.somesystem.entity.ParameterValue;
import com.somesystem.exceptions.AlreadyExistsException;
import com.somesystem.exceptions.NotFoundException;
import com.somesystem.service.ParameterService;
import com.somesystem.service.ParameterValuesService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ParameterValuesController {
    @Autowired
    private ParameterService paramService;

    @Autowired
    private ParameterValuesService valuesService;

    @GetMapping("/parameter-values")
    public ResponseEntity<List<ParameterValue>> getAllValues() {
        return new ResponseEntity<>(valuesService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/parameters/{paramId}/values")
    public ResponseEntity<List<ParameterValue>> getAllByParameterId(@PathVariable(value = "paramId") Long paramId) throws NotFoundException {
        if (!paramService.getById(paramId).isPresent()) {
            throw new NotFoundException("Not found Parameter with id = " + paramId);
        }

        List<ParameterValue> values = valuesService.findAllByParametrasId(paramId);
        return new ResponseEntity<>(values, HttpStatus.OK);
    }

    @PostMapping("/parameters/{paramId}/values")
    public ResponseEntity<ParameterValue> saveParameterValue(@PathVariable(value = "paramId") Long paramId, @RequestBody ParameterValue newParam) throws AlreadyExistsException, NotFoundException {
        if (paramId == null || paramId <= 0) {
            throw new AlreadyExistsException("Unknown id of Parameter passed.");
        }
        Parameter param = paramService.getById(paramId)
                .orElseThrow(() -> new NotFoundException("Parent Parameter not found: " + paramId));
        var result = valuesService.createNew(param, newParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/parameters/{id}/values/{valueId}")
    public ResponseEntity<ParameterValue> updateParameter(@PathVariable("id") long id, @PathVariable("valueId") long valueId, @RequestBody ParameterValue newParam) throws NotFoundException {
        ParameterValue value = valuesService.getById(valueId)
                .orElseThrow(() -> new NotFoundException("ParameterValue not found: " + valueId));

        if (value.getParametras().getId() != id) {
            throw new IllegalStateException("Requested ParameterValue do not belong to Parameter with Id = " + value);
        }

        value.setReiksme(newParam.getReiksme());
        value.setGaliojaIki(newParam.getGaliojaIki());
        value.setGaliojaNuo(newParam.getGaliojaNuo());

        return new ResponseEntity<>(valuesService.update(value), HttpStatus.OK);
    }

    @DeleteMapping("/parameters/{id}/parameter-values/{valueId}")
    public ResponseEntity<?> deleteParameterValue(@PathVariable(value = "id") long id, @PathVariable(value = "valueId") long valueId) throws NotFoundException {
        Parameter param = paramService.getById(id)
                .orElseThrow(() -> new NotFoundException("Parameter not found: " + id));

        ParameterValue value = valuesService.getById(valueId)
                .orElseThrow(() -> new NotFoundException("ParameterValue not found: " + valueId));
        valuesService.deleteParameterValue(value);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(valueId);
    }
}