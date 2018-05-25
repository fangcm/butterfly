package com.fangcm.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */

//JDK8函数式接口注解 仅能包含一个抽象方法
@FunctionalInterface
public interface BaseService<E, ID extends Serializable> {

    BaseDao<E, ID> getRepository();

    /**
     * 根据ID获取
     */

    default E getOne(ID id) {
        return getRepository().getOne(id);
    }

    /**
     * 获取所有列表
     */
    default List<E> findAll() {
        return getRepository().findAll();
    }

    /**
     * 保存 or 修改
     */
    default E save(E entity) {

        return getRepository().save(entity);
    }

    /**
     * 批量保存与修改
     */
    default Iterable<E> saveAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }

    /**
     * 删除
     */
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    /**
     * 根据Id删除
     */
    default void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 批量删除
     */
    default void deleteAll(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    /**
     * 批量删除
     */
    default void delByIds(ID[] ids) {
        for (ID id : ids) {
            getRepository().deleteById(id);
        }
    }

    /**
     * 清空缓存，提交持久化
     */
    default void flush() {
        getRepository().flush();
    }

    /**
     * 根据条件查询获取
     */
    default List<E> findAll(Specification<E> spec) {
        return getRepository().findAll(spec);
    }

    /**
     * 分页获取
     */
    default Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    /**
     * 根据查询条件分页获取
     */
    default Page<E> findAll(Specification<E> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }

    /**
     * 获取查询条件的结果数
     */
    default long count(Specification<E> spec) {
        return getRepository().count(spec);
    }

}
