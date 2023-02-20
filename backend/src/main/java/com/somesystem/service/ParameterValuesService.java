package com.somesystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somesystem.entity.Parameter;
import com.somesystem.entity.ParameterValue;
import com.somesystem.repository.ParameterRepository;
import com.somesystem.repository.ParameterValuesRepository;

@Service
public class ParameterValuesService {
    @Autowired
    private ParameterRepository paramsRepo;

    @Autowired
    private ParameterValuesRepository valuesRepo;

    public List<ParameterValue> findAll() {
        return valuesRepo.findAll();
    }

    public Optional<ParameterValue> getById(long id) {
        return valuesRepo.findById(id);
    }

    public List<ParameterValue> findAllByParametrasId(long id) {
        return valuesRepo.findAllByParametrasId(id);
    }

    public ParameterValue createNew(Parameter param, ParameterValue paramValue) {
        return valuesRepo.save(new ParameterValue(
                paramValue.getReiksme(),
                paramValue.getGaliojaNuo(),
                paramValue.getGaliojaIki(),
                param
        ));
    }

    public void deleteParameterValue(ParameterValue param) {
        valuesRepo.delete(param);
    }

    public ParameterValue update(ParameterValue param) {
        return valuesRepo.save(param);
    }
}
