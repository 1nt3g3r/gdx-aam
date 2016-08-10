package ua.com.integer.gdx.annotation.asset.manager.imp.region;

import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetLoader;
import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetManager;

public class GdxTextureRegionLoader extends AnnotationAssetLoader {
	@Override
	public void performLoading(AnnotationAssetManager manager) throws Exception {
		GdxTextureRegion textureRegion = (GdxTextureRegion) annotation;
		field.set(object, manager.getRegion(textureRegion.atlas(), textureRegion.region()));
	}
}
