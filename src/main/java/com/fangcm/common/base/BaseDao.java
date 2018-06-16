package com.fangcm.common.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * Created by FangCM on 2018/5/23.
 */

// 自定义接口 不会创建接口的实例  必须加此注解
@NoRepositoryBean
public interface BaseDao<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
    @Modifying
    @Query(value = "update #{#entityName} set del_flag=1 where id=?1", nativeQuery = true)
    void deleteById(@NonNull ID id);
}