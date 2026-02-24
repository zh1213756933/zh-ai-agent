package com.zhou.common.exception;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 断言工具类
 */
public abstract class Assert {

    /**
     * 断言表达式值为true
     */
    public static void isTrue(boolean expression, String message) {
        isTrue(expression, () -> new BusinessException(message));
    }
    public static void isTrue(boolean expression, ErrorCode errorCode) {
        isTrue(expression, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void isTrue(boolean expression, Supplier<X> supplier) throws X {
        if (!expression) {
            throw supplier.get();
        }
    }

    /**
     * 断言表达式值为false
     */
    public static void isFalse(boolean expression, String message) {
        isFalse(expression, () -> new BusinessException(message));
    }
    public static void isFalse(boolean expression, ErrorCode errorCode) {
        isFalse(expression, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void isFalse(boolean expression, Supplier<X> supplier) throws X {
        if (expression) {
            throw supplier.get();
        }
    }

    /**
     * 断言对象为null
     */
    public static void isNull(Object object, String message) {
        isNull(object, () -> new BusinessException(message));
    }
    public static void isNull(Object object, ErrorCode errorCode) {
        isNull(object, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void isNull(Object object, Supplier<X> supplier) throws X {
        if (null != object) {
            throw supplier.get();
        }
    }

    /**
     * 断言对象不为null
     */
    public static void notNull(Object object, String message) {
        notNull(object, () -> new BusinessException(message));
    }
    public static void notNull(Object object, ErrorCode errorCode) {
        notNull(object, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void notNull(Object object, Supplier<X> supplier) throws X {
        if (null == object) {
            throw supplier.get();
        }
    }

    /**
     * 断言collection集合为空集合||null
     */
    public static <X extends Throwable> void isEmpty(Collection<?> collection, String message) {
        isEmpty(collection, () -> new BusinessException(message));
    }
    public static <X extends Throwable> void isEmpty(Collection<?> collection, ErrorCode errorCode) {
        isEmpty(collection, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void isEmpty(Collection<?> collection, Supplier<X> supplier) throws X {
        if (!CollectionUtils.isEmpty(collection)) {
            throw supplier.get();
        }
    }

    /**
     * 断言collection集合为非空集合
     */
    public static <X extends Throwable> void notEmpty(Collection<?> collection, String message) {
        notEmpty(collection, () -> new BusinessException(message));
    }
    public static <X extends Throwable> void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        notEmpty(collection, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void notEmpty(Collection<?> collection, Supplier<X> supplier) throws X {
        if (CollectionUtils.isEmpty(collection)) {
            throw supplier.get();
        }
    }

    /**
     * 断言map集合为空集合
     */
    public static <X extends Throwable> void isEmpty(Map<?, ?> map, String message) {
        isEmpty(map, () -> new BusinessException(message));
    }
    public static <X extends Throwable> void isEmpty(Map<?, ?> map, ErrorCode errorCode) {
        isEmpty(map, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void isEmpty(Map<?, ?> map, Supplier<X> supplier) throws X {
        if (!CollectionUtils.isEmpty(map)) {
            throw supplier.get();
        }
    }

    /**
     * 断言map集合为非空集合
     */
    public static <X extends Throwable> void notEmpty(Map<?, ?> map, String message) {
        notEmpty(map, () -> new BusinessException(message));
    }
    public static <X extends Throwable> void notEmpty(Map<?, ?> map, ErrorCode errorCode) {
        notEmpty(map, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void notEmpty(Map<?, ?> map, Supplier<X> supplier) throws X {
        if (CollectionUtils.isEmpty(map)) {
            throw supplier.get();
        }
    }

    /**
     * 断言字符串有内容--三判 不为Null，有内容且不为空格
     */
    public static <T extends CharSequence> void hasText(T text, String message) {
        hasText(text, () -> new BusinessException(message));
    }
    public static <T extends CharSequence> void hasText(T text, ErrorCode errorCode) {
        hasText(text, () -> new BusinessException(errorCode));
    }
    public static <T extends CharSequence, X extends Throwable> void hasText(T text, Supplier<X> supplier) throws X {
        if (!StringUtils.hasText(text)) {
            throw supplier.get();
        }
    }

    /**
     * 断言字符串为空白
     */
    public static <T extends CharSequence> void isBlank(T text, String message) {
        isBlank(text, () -> new BusinessException(message));
    }
    public static <T extends CharSequence> void isBlank(T text, ErrorCode errorCode) {
        isBlank(text, () -> new BusinessException(errorCode));
    }
    public static <T extends CharSequence, X extends Throwable> void isBlank(T text, Supplier<X> supplier) throws X {
        if (StringUtils.hasText(text)) {
            throw supplier.get();
        }
    }

    /**
     * 断言对象为指定类型
     */
    public static void isInstanceOf(Object object, Class<?> type, String message) {
        isInstanceOf(object, type, () -> new BusinessException(message));
    }
    public static void isInstanceOf(Object object, Class<?> type, ErrorCode errorCode) {
        isInstanceOf(object, type, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void isInstanceOf(Object object, Class<?> type, Supplier<X> supplier) throws X {
        if (!type.isInstance(object)) {
            throw supplier.get();
        }
    }

    /**
     * 断言两个对象相等
     */
    public static void equals(Object obj1, Object obj2, String message) {
        equals(obj1, obj2, () -> new BusinessException(message));
    }
    public static void equals(Object obj1, Object obj2, ErrorCode errorCode) {
        equals(obj1, obj2, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void equals(Object obj1, Object obj2, Supplier<X> supplier) throws X {
        if (!Objects.equals(obj1, obj2)) {
            throw supplier.get();
        }
    }

    /**
     * 断言两个对象不相等
     */
    public static void notEquals(Object obj1, Object obj2, String message) {
        notEquals(obj1, obj2, () -> new BusinessException(message));
    }
    public static void notEquals(Object obj1, Object obj2, ErrorCode errorCode) {
        notEquals(obj1, obj2, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable> void notEquals(Object obj1, Object obj2, Supplier<X> supplier) throws X {
        if (Objects.equals(obj1, obj2)) {
            throw supplier.get();
        }
    }

    /**
     * 断言列表是否包含某元素
     */
    public static <T> void contains(Collection<T> collection, T element, String message) {
        contains(collection, element, () -> new BusinessException(message));
    }
    public static <T> void contains(Collection<T> collection, T element, ErrorCode errorCode) {
        contains(collection, element, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable, T> void contains(Collection<T> collection, T element, Supplier<X> supplier) throws X {
        if (!collection.contains(element)) {
            throw supplier.get();
        }
    }

    /**
     * 断言列表是否不包含某元素
     */
    public static <T> void notContains(Collection<T> collection, T element, String message) {
        notContains(collection, element, () -> new BusinessException(message));
    }
    public static <T> void notContains(Collection<T> collection, T element, ErrorCode errorCode) {
        notContains(collection, element, () -> new BusinessException(errorCode));
    }
    public static <X extends Throwable, T> void notContains(Collection<T> collection, T element, Supplier<X> supplier) throws X {
        if (collection.contains(element)) {
            throw supplier.get();
        }
    }

}
