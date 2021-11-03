package com.ds.EnergyUtilityPlatform.service;


import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class CrudService<T> implements ICrudService<T>{
    private final CrudRepository<T> crudRepository;

    public List<T> findAll() {
        return crudRepository.findAll();
    }

    public T findById(Long id) {
        Optional<T> optionalT = crudRepository.findById(id);
        if (optionalT.isPresent()) {
            return optionalT.get();
        }
        throw new IllegalStateException("Cannot find entity with id=" + id);
    }

    public T create(T bean) {
        return crudRepository.save(bean);
    }

    public T modify(T bean) {
        return crudRepository.save(bean);
    }

    public void deleteById(Long id) {
        crudRepository.deleteById(id);
    }

    public void delete(T bean) {
        crudRepository.delete(bean);
    }


}
