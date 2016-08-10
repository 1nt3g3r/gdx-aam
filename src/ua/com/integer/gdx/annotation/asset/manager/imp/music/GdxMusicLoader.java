package ua.com.integer.gdx.annotation.asset.manager.imp.music;

import com.badlogic.gdx.audio.Music;

import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetLoader;
import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetManager;

public class GdxMusicLoader extends AnnotationAssetLoader {
	@Override
	public void performLoading(AnnotationAssetManager manager) throws Exception {
		GdxMusic gdxMusic = (GdxMusic) annotation;
		Music music = manager.getMusic(gdxMusic.value());
		music.setLooping(gdxMusic.looping());
		field.set(object, music);
	}
}
