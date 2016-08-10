package ua.com.integer.gdx.annotation.asset.manager.imp.atlas;

import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetLoader;
import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetManager;

public class GdxTextureAtlasLoader extends AnnotationAssetLoader {
	@Override
	public void performLoading(AnnotationAssetManager manager) throws Exception {
		GdxTextureAtlas atlas = (GdxTextureAtlas) annotation;
		field.set(object, manager.getAtlas(atlas.value()));
	}
}
