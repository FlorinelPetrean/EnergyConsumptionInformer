package com.ds.EnergyUtilityPlatform.controller;


import com.ds.EnergyUtilityPlatform.model.dto.DtoMapper;
import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import com.ds.EnergyUtilityPlatform.service.ICrudService;
import com.ds.EnergyUtilityPlatform.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = Constants.frontend)
@RequiredArgsConstructor
public abstract class CrudController<T, U> {
    protected final ICrudService<T> service;
    protected final DtoMapper dtoMapper;

    @GetMapping(path = "/all")
    public List<IDto<T>> findAll() {
        List<T> all = service.findAll();
        return all.stream().map(dtoMapper::getDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public IDto<T> findById(@PathVariable Long id) {
        return dtoMapper.getDto(service.findById(id));
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Void> create(@RequestBody U bean) {
        T entity = dtoMapper.getEntity((IDto<T>) bean);
        service.create(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/modify")
    public ResponseEntity<Void> modify(@RequestBody U bean) {
        T entity = dtoMapper.getEntity((IDto<T>) bean);
        service.modify(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
