package com.fangcm.common.base;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by FangCM on 2018/5/23.
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    public static final int DEL_FLAG_NORMAL = 0; //正常状态
    public static final int DEL_FLAG_DELETE = 1; //删除标志

    private static final long serialVersionUID = 1L;
    //唯一标识
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime; //创建时间

    @Column(name = "create_by", updatable = false)
    private String createBy;  //创建者

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime; //更新时间

    @Column(name = "update_by")
    private String updateBy; //更新者

    @Column(name = "del_flag")
    private Integer delFlag = DEL_FLAG_NORMAL; //删除标志 默认0

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
