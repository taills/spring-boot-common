package com.gxmafeng.jpa.service;

import com.gxmafeng.jpa.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @ClassName BaseService
 * @Description
 * @Author nil
 * @Date 2021/10/18 10:59 下午
 **/

@NoRepositoryBean
//照理说，这个类不在jpa的repo扫描路径下，但它竟然报错，只能加上面这行注释给它
// by 2021-10-20 nil
public interface BaseService<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    public Class<T> getEntityClass();

    /**
     * 更新记录，并且忽略空值字段
     * @param entity
     * @param <S>
     * @return
     */
    public <S extends T> S update(S entity);
}
