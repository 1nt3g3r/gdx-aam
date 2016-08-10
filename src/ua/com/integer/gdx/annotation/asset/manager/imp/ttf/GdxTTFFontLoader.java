package ua.com.integer.gdx.annotation.asset.manager.imp.ttf;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetLoader;
import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetManager;

public class GdxTTFFontLoader extends AnnotationAssetLoader{
	@Override
	public void performLoading(AnnotationAssetManager manager) throws Exception {
		GdxTTFFont gdxFont = (GdxTTFFont) annotation;
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = gdxFont.size();
		param.mono = gdxFont.mono();
		param.hinting = gdxFont.hinting();
		param.color = Color.valueOf(gdxFont.color());
		param.gamma = gdxFont.gamma();
		param.renderCount = gdxFont.renderCount();
		param.borderWidth = gdxFont.borderWidth();
		param.borderColor = Color.valueOf(gdxFont.borderColor());
		param.borderStraight = gdxFont.borderStraight();
		param.borderGamma = gdxFont.borderGamma();
		param.shadowOffsetX = gdxFont.shadowOffsetX();
		param.shadowOffsetY = gdxFont.shadowOffsetY();
		param.shadowColor = Color.valueOf(gdxFont.shadowColor());
		param.spaceX = gdxFont.spaceX();
		param.spaceY = gdxFont.spaceY();
		param.characters = gdxFont.characters();
		param.kerning = gdxFont.kerning();
		param.flip = gdxFont.flip();
		param.genMipMaps = gdxFont.genMipMaps();
		param.minFilter = gdxFont.minFilter();
		param.magFilter = gdxFont.magFilter();
		param.incremental = gdxFont.incremental();

		manager.getFontManager().loadFont(gdxFont.value(), gdxFont.size(), gdxFont.characters(), param);
		field.set(object, manager.getFontManager().getFont(gdxFont.value(), gdxFont.size()));
	}
}