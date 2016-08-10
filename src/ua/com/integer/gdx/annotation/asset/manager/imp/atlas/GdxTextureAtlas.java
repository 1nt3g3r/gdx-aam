package ua.com.integer.gdx.annotation.asset.manager.imp.atlas;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GdxTextureAtlas {
	public String value();
}
