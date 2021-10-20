package com.gxmafeng.jpa.entity;

import com.gxmafeng.service.common.utils.SnowFlake;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import javax.persistence.*;

/**
 * @ClassName BaseEntity
 * @Description
 * @Author nil
 * @Date 2021/10/18 10:34 下午
 **/
@MappedSuperclass
public class BaseEntity {

    /**
     * 主键id
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "主键id")
    private Long id;


    /**
     * 创建时间
     * nullable : true
     * default  : CURRENT_TIMESTAMP
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "gmt_create", nullable = true)
    private java.util.Date gmtCreate;

    /**
     * 修改时间
     * nullable : true
     * default  : CURRENT_TIMESTAMP
     */
    @ApiModelProperty(value = "修改时间")
    @Column(name = "gmt_modified", nullable = true)
    private java.util.Date gmtModified;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
//        return "BaseEntity{" +
//                "id=" + id +
//                ", gmtCreate=" + gmtCreate +
//                ", gmtModified=" + gmtModified +
//                '}';
        return "{id: " + id + " }";
    }

    @PrePersist
    void preInsert() {
        if (this.gmtCreate == null) {
            this.gmtCreate = new Date();
        }
        if (this.id == null) {
            this.id = SnowFlake.get().nextId();
        }
    }

    @PreUpdate
    void preUpdate() {
        this.gmtModified = new Date();
    }
}
