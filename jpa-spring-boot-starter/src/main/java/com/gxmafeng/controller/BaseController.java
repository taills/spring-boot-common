package com.gxmafeng.controller;

import com.gxmafeng.jpa.entity.BaseEntity;
import com.gxmafeng.jpa.service.BaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BaseController
 * @Description
 * @Author nil
 * @Date 2021/10/20 11:47 下午
 **/
@Slf4j
@Api(tags = "BaseController")
public class BaseController<T extends BaseEntity, ID> {
    private BaseService<T, ID> baseService;
    private String entityName;

    /**
     * 初始化，必须传入 service
     *
     * @param baseService
     */
    public void init(BaseService baseService) {
        this.baseService = baseService;
        this.entityName = AnnotationUtils.getAnnotation(getService().getEntityClass(), ApiModel.class).value();
    }

    /**
     * 获取范型实体的名称
     *
     * @return
     */
    protected String getEntityName() {
        return AnnotationUtils.getAnnotation(getService().getEntityClass(), ApiModel.class).value();
    }

    /**
     * 获取默认 service
     *
     * @return
     */
    protected BaseService getService() {
        return this.baseService;
    }


    /**
     * 读取数据
     *
     * @param pageNo         页码
     * @param pageSize       页长
     * @param isOrderByIdAsc 是否按ID升序排列
     * @return
     */
    @GetMapping("/read")
    @ApiOperation(value = "读取数据", tags = "admin-read")
    @PreAuthorize("hasRole('ADMIN_READ')")
    public List<T> read(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                        @RequestParam(name = "pageSize", required = false, defaultValue = "100") Integer pageSize,
                        @RequestParam(name = "pageNo", required = false, defaultValue = "false") Boolean isOrderByIdAsc) {
        Sort sort = Sort.by("id");
        return this.baseService.findAll(
                PageRequest.of(pageNo,
                        pageSize,
                        isOrderByIdAsc ? sort.ascending() : sort.descending()
                )
        ).getContent();
    }

    /**
     * 更新实体
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "更新数据", tags = "admin-update")
    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN_UPDATE')")
    public T update(@RequestBody T entity) {
        return this.baseService.update(entity);
    }

    /**
     * 新建实体
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "新建数据", tags = "admin-create")
    @PutMapping("/create")
    @PreAuthorize("hasRole('ADMIN_CREATE')")
    public T create(@RequestBody T entity) {
        return this.baseService.save(entity);
    }

    /**
     * 删除单个实体
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除数据", tags = "admin-delete")
    @PreAuthorize("hasRole('ADMIN_DELETE')")
    public void delete(@PathVariable ID id) {
        this.baseService.deleteById(id);
    }
}
