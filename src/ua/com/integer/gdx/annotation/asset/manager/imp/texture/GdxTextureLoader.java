package ua.com.integer.gdx.annotation.asset.manager.imp.texture;

import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetLoader;
import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetManager;

public class GdxTextureLoader extends AnnotationAssetLoader {
	@Override
	public void performLoading(AnnotationAssetManager manager) throws Exception {
		GdxTexture texture = (GdxTexture) annotation;
		field.set(object, manager.getTexture(texture.value()));
	}

}
