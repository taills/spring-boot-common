package com.gxmafeng.jpa.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

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
    @ApiModelProperty(value = "主键id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    /**
//     * 创建时间
//     * nullable : true
//     * default  : CURRENT_TIMESTAMP
//     */
//    @ApiModelProperty(value = "创建时间")
//    @Column(name = "gmt_create", nullable = true)
//    private java.util.Date gmtCreate = new Date();
//
//    /**
//     * 修改时间
//     * nullable : true
//     * default  : CURRENT_TIMESTAMP
//     */
//    @ApiModelProperty(value = "修改时间")
//    @Column(name = "gmt_modified", nullable = true)
//    private java.util.Date gmtModified;
//
//    /**
//     * 删除时间，用于软删除，为空是有效，有值时表示已经删除
//     * nullable : true
//     * default  : null
//     */
//    @ApiModelProperty(value = "删除时间，用于软删除，为空是有效，有值时表示已经删除")
//    @Column(name = "gmt_delete", nullable = true)
//    private java.util.Date gmtDelete;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
//
//    public Date getGmtCreate() {
//        return gmtCreate;
//    }
//
//    public void setGmtCreate(Date gmtCreate) {
//        this.gmtCreate = gmtCreate;
//    }
//
//    public Date getGmtModified() {
//        return gmtModified;
//    }
//
//    public void setGmtModified(Date gmtModified) {
//        this.gmtModified = gmtModified;
//    }
//
//    public Date getGmtDelete() {
//        return gmtDelete;
//    }
//
//    public void setGmtDelete(Date gmtDelete) {
//        this.gmtDelete = gmtDelete;
//    }
//
//    @Override
//    public String toString() {
//        return "BaseEntity{" +
//                "id=" + id +
//                ", gmtCreate=" + gmtCreate +
//                ", gmtModified=" + gmtModified +
//                ", gmtDelete=" + gmtDelete +
//                '}';
//    }
}
