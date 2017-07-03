package com.shsxt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 作用域
@Retention(RetentionPolicy.RUNTIME) // 生命周期
@Documented // 生成java doc
@Inherited // 继承
public @interface SystemLog {
	public String description() default "";
}
