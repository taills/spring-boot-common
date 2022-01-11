package io.github.taills.common.jpa.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @ClassName BaseService
 * @Description
 * @Author nil
 * @Date 2021/10/18 10:59 下午
 * 照理说，这个类不在jpa的repo扫描路径下，但它竟然报错，只能加上面这行注释给它
 * by 2021-10-20 nil
 **/
@NoRepositoryBean
public interface BaseService<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * 获取实体类型
     *
     * @return
     */
    public Class<T> getEntityClass();

    /**
     * 更新记录，并且忽略空值字段
     *
     * @param entity
     * @param <S>
     * @return
     */
    public <S extends T> S update(S entity);
}
