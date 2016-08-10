package ua.com.integer.gdx.annotation.asset.manager.imp.ttf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GdxTTFFont {
	public String value();
	public int size() default 16;
	public boolean mono() default false;
	public Hinting hinting() default Hinting.Medium;
	public String color() default "ffffff";
	public float gamma() default 1.8f;
	public int renderCount() default 2;

	public int borderWidth() default 0;
	public String borderColor() default "000000";
	public boolean borderStraight() default false;
	public float borderGamma() default 1.8f;
	
	public int shadowOffsetX() default 0;
	public int shadowOffsetY() default 0;
	public String shadowColor() default "000000";
	public int spaceX() default 0;
	public int spaceY() default 0;
	
	public String characters() default "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯяabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>’";
	public boolean kerning() default true;
	public boolean flip() default false;
	public boolean genMipMaps() default false;
	
	public TextureFilter minFilter() default TextureFilter.Nearest;
	public TextureFilter magFilter() default TextureFilter.Nearest;
	public boolean incremental() default false;
}
