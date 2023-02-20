package com.somesystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somesystem.entity.Parameter;
import com.somesystem.repository.ParameterRepository;
@Service
public class ParameterService {
    @Autowired
    private ParameterRepository repo;

    public List<Parameter> findAll() {
        return repo.findAll();
    }

    public Optional<Parameter> getById(long id) {
        return repo.findById(id);
    }
    public Parameter createNew(Parameter param) {
        return repo.save(new Parameter(param.getKodas(), param.getPavadinimas(), param.getAprasymas(), param.getGaliojaNuo(), param.getGaliojaIki()));
    }

    public Parameter update(Parameter param) {
        return repo.save(param);
    }
    public void deleteParameter(Parameter param) {
        repo.deleteById(param.getId());
    }
}
