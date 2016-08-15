package ua.com.integer.gdx.annotation.asset.manager.imp.image;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GdxImage {
	public String atlas();
	public String region();
	public boolean fullScreen() default false;
	public int align() default Align.center;
	public Scaling scaling() default Scaling.fit;
}
