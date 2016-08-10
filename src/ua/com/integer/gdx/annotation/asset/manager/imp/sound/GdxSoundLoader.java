package ua.com.integer.gdx.annotation.asset.manager.imp.sound;

import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetLoader;
import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetManager;

public class GdxSoundLoader extends AnnotationAssetLoader {
	@Override
	public void performLoading(AnnotationAssetManager manager) throws Exception {
		GdxSound sound = (GdxSound) annotation;
		field.set(object, manager.getSound(sound.value()));
	}
}
