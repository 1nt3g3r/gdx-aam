package ua.com.integer.gdx.annotation.asset.manager.imp.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetLoader;
import ua.com.integer.gdx.annotation.asset.manager.AnnotationAssetManager;

public class GdxImageLoader extends AnnotationAssetLoader {
	@Override
	public void performLoading(AnnotationAssetManager manager) throws Exception {
		GdxImage gdxImage = (GdxImage) annotation;
		Image image = new Image(manager.getRegion(gdxImage.atlas(), gdxImage.region()));
		image.setScaling(gdxImage.scaling());
		if (gdxImage.fullScreen()) {
			image.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		field.set(object, image);
	}
}
