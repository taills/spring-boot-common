package com.github.taills.common.jpa.service;

import com.github.taills.common.jpa.entity.BaseEntity;
import com.github.taills.common.jpa.repository.BaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @ClassName AbstractService
 * @Description
 * @Author nil
 * @Date 2021/10/18 11:02 下午
 **/

public abstract class AbstractService<T extends BaseEntity, ID> implements BaseService<T, ID> {

    /**
     * 泛型注入
     */
    @Autowired
    protected BaseRepository<T, ID> baseRepository;

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<T> findAll() {
        return this.baseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<T> findAll(Sort sort) {
        return this.baseRepository.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<T> findAllById(Iterable<ID> ids) {
        return this.baseRepository.findAllById(ids);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return this.baseRepository.saveAll(entities);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void flush() {
        this.baseRepository.flush();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <S extends T> S saveAndFlush(S entity) {
        return this.baseRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @Deprecated
    public void deleteInBatch(Iterable<T> entities) {
        this.baseRepository.deleteInBatch(entities);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteAllInBatch() {
        this.baseRepository.deleteAllInBatch();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    @Deprecated
    public T getOne(ID id) {
        return this.baseRepository.getOne(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public <S extends T> List<S> findAll(Example<S> example) {
        return this.baseRepository.findAll(example);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return this.baseRepository.findAll(example, sort);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Page<T> findAll(Pageable pageable) {
        return this.baseRepository.findAll(pageable);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <S extends T> S save(S entity) {
        return this.baseRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Optional<T> findById(ID id) {
        return this.baseRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public boolean existsById(ID id) {
        return this.baseRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public long count() {
        return this.baseRepository.count();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteById(ID id) {
        this.baseRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(T entity) {
        this.baseRepository.delete(entity);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteAll(Iterable<? extends T> entities) {
        this.baseRepository.deleteAll(entities);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteAll() {
        this.baseRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return this.baseRepository.findOne(example);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return this.baseRepository.findAll(example, pageable);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public <S extends T> long count(Example<S> example) {
        return this.baseRepository.count(example);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public <S extends T> boolean exists(Example<S> example) {
        return this.baseRepository.exists(example);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Optional<T> findOne(Specification<T> spec) {
        return this.baseRepository.findOne(spec);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<T> findAll(Specification<T> spec) {
        return this.baseRepository.findAll(spec);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return this.baseRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return this.baseRepository.findAll(spec, sort);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public long count(Specification<T> spec) {
        return this.baseRepository.count(spec);
    }

    @Override
    public <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
        return this.baseRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<T> entities) {
        this.baseRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<ID> ids) {
        this.baseRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public T getById(ID id) {
        return this.baseRepository.getById(id);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        this.baseRepository.deleteAllById(ids);
    }


    @Override
    public Class<T> getEntityClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    @Override
    public <S extends T> S update(S entity) {
        S original = (S) this.baseRepository.getById((ID) entity.getId());
        copyNonNullProperties(entity, original);
        return this.baseRepository.save(original);
    }

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


}
