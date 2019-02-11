package com.fangcm.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by FangCM on 2018/5/23.
 */
@Data
public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //创建时间

    private String createBy;  //创建者

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime; //更新时间

    private String updateBy; //更新者

    private Integer delFlag; //删除标志 默认0

}
