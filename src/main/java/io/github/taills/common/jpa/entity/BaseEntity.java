package io.github.taills.common.jpa.entity;


import io.github.taills.common.util.SnowFlake;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName BaseEntity
 * @Description
 * @Author nil
 * @Date 2021/10/18 10:34 下午
 **/
@MappedSuperclass
@Slf4j
public class BaseEntity implements Serializable {

    /**
     * 主键id
     */
    @Id
    @Column(name = "id", length = 20)
    @ApiModelProperty(value = "主键id", hidden = true)
    private String id;


    /**
     * 创建时间
     * nullable : true
     * default  : CURRENT_TIMESTAMP
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    @Column(name = "gmt_create", nullable = true)
    private java.util.Date gmtCreate;

    /**
     * 修改时间
     * nullable : true
     * default  : CURRENT_TIMESTAMP
     */
    @ApiModelProperty(value = "修改时间", hidden = true)
    @Column(name = "gmt_modified", nullable = true)
    private java.util.Date gmtModified;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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
        //JPA中间表的数据并不会过这个方法!!
        log.debug("preInsert {} {}", this.id == null, this);
        if (this.gmtCreate == null) {
            this.gmtCreate = new Date();
        }
        if (this.id == null) {
            this.id = SnowFlake.get().nextSid();
        }
    }

    @PreUpdate
    void preUpdate() {
        log.debug("preUpdate {}", this);
        this.gmtModified = new Date();
    }


}
