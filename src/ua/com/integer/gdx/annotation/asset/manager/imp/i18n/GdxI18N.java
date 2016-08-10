package ua.com.integer.gdx.annotation.asset.manager.imp.i18n;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GdxI18N {
	public String value();
	public String bundle();
	public String language() default "";
	public String country() default "";
	public String variant() default "";
	public String[] format() default "";
	public String encoding() default "UTF-8";
}
