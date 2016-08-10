package ua.com.integer.gdx.annotation.asset.manager.imp.i18n;

import com.badlogic.gdx.utils.I18NBundle;

import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetLoader;
import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetManager;

public class GdxI18NLoader extends AnnotationAssetLoader {
	@Override
	public void performLoading(AnnotationAssetManager manager) throws Exception {
		GdxI18N gdxI18N = (GdxI18N) annotation;
		I18NBundle bundle = manager.getBundle(gdxI18N.bundle(), gdxI18N.language(), gdxI18N.country(), gdxI18N.language(), gdxI18N.encoding());
		String value = bundle.format(gdxI18N.value(), (Object[]) gdxI18N.format());
		field.set(object, value);
	}
}
