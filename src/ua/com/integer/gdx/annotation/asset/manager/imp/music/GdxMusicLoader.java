package ua.com.integer.gdx.annotation.asset.manager.imp.music;

import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetLoader;
import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetManager;

public class GdxMusicLoader extends AnnotationAssetLoader {
	@Override
	public void performLoading(AnnotationAssetManager manager) throws Exception {
		GdxMusic music = (GdxMusic) annotation;
		field.set(object, manager.getMusic(music.value()));
	}
}
