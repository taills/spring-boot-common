package com.gxmafeng.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @ClassName BaseRepository
 * @Description
 * @Author nil
 * @Date 2021/10/18 10:57 下午
 **/


@NoRepositoryBean  // 该接口不是一个Repository，不需要生成代理实现
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}