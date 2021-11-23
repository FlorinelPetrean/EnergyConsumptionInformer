package com.ds.EnergyUtilityPlatform.service;


import com.ds.EnergyUtilityPlatform.repository.CrudRepository;
import com.ds.EnergyUtilityPlatform.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class CrudService<T> implements ICrudService<T>{
    private final CrudRepository<T> crudRepository;
    private final BeanUtil<T> beanUtil;

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

    public boolean doesExist(Long id) {
        Optional<T> optionalT = crudRepository.findById(id);
        return optionalT.isPresent();
    }

    public T create(T bean) {
        return crudRepository.save(bean);
    }

    public T modify(Long id, T bean) {
        T currentBean = findById(id);
        beanUtil.copyNonNullProperties(currentBean, bean);
        return crudRepository.save(currentBean);
    }

    public void deleteById(Long id) {
        crudRepository.deleteById(id);
    }

    public void delete(T bean) {
        crudRepository.delete(bean);
    }


}
