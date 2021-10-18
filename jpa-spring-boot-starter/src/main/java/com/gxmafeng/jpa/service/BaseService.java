package com.gxmafeng.jpa.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName BaseService
 * @Description
 * @Author nil
 * @Date 2021/10/18 10:59 下午
 **/


public interface BaseService<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
