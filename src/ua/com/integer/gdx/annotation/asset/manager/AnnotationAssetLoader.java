package ua.com.integer.gdx.annotation.asset.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class AnnotationAssetLoader {
	protected Object object;
	protected Field field;
	protected Annotation annotation;
	protected AnnotationAssetManager manager;
	
	public void load(AnnotationAssetManager manager, Object object, Field field, Annotation annotation) {
		this.manager = manager;
		this.object = object;
		this.field = field;
		this.annotation = annotation;
		
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		
		try {
			performLoading(manager);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public abstract void performLoading(AnnotationAssetManager manager) throws Exception;
}
