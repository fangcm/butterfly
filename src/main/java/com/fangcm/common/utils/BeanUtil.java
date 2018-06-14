package com.fangcm.common.utils;


import com.fangcm.exception.ButterflyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

/**
 * Created by FangCM on 2018/6/13.
 */
public class BeanUtil {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    public static <T> T copy(Object source, Class<T> targetClass) {
        try {
            final BeanCopier copier = BeanCopier.create(source.getClass(), targetClass, false);
            T target = targetClass.newInstance();
            copier.copy(source, target, null);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("BeanUtil copy error", e);
            throw new ButterflyException(e.getMessage());
        }
    }

}
