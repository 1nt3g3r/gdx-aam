package ua.com.integer.gdx.annotation.asset.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import ua.com.integer.gdx.annotation.asset.manager.imp.atlas.GdxTextureAtlas;
import ua.com.integer.gdx.annotation.asset.manager.imp.atlas.GdxTextureAtlasLoader;
import ua.com.integer.gdx.annotation.asset.manager.imp.image.GdxImage;
import ua.com.integer.gdx.annotation.asset.manager.imp.image.GdxImageLoader;
import ua.com.integer.gdx.annotation.asset.manager.imp.music.GdxMusic;
import ua.com.integer.gdx.annotation.asset.manager.imp.music.GdxMusicLoader;
import ua.com.integer.gdx.annotation.asset.manager.imp.region.GdxTextureRegion;
import ua.com.integer.gdx.annotation.asset.manager.imp.region.GdxTextureRegionLoader;
import ua.com.integer.gdx.annotation.asset.manager.imp.sound.GdxSound;
import ua.com.integer.gdx.annotation.asset.manager.imp.sound.GdxSoundLoader;
import ua.com.integer.gdx.annotation.asset.manager.imp.texture.GdxTexture;
import ua.com.integer.gdx.annotation.asset.manager.imp.texture.GdxTextureLoader;

public class AnnotationAssetManager implements Disposable {
	private StringBuilder tmpStringBuilder = new StringBuilder();

	private Array<String> textureFolders = new Array<>(new String[] {"textures"});
	private Array<String> textureExtensions = new Array<>(new String[] {"jpg", "png", "etc1"});
	
	private Array<String> soundFolders = new Array<>(new String[] {"sounds"});
	private Array<String> soundExtensions = new Array<>(new String[] {"mp3", "ogg"});
	
	private Array<String> musicFolders = new Array<>(new String[] {"music"});
	private Array<String> musicExtensions = new Array<>(new String[] {"mp3", "ogg"});
	
	private Array<String> atlasFolders = new Array<String>(new String[] {"atlases"});
	private Array<String> atlasExtensions = new Array<String>(new String[] {"atlas", "pack"});
	
	private AssetManager assetManager;
	private ObjectMap<Class<? extends Annotation>, AnnotationAssetLoader> loaders = new ObjectMap<>();
	
	public AnnotationAssetManager() {
		this(new InternalFileHandleResolver());
	}

	public AnnotationAssetManager(FileHandleResolver resolver) {
		assetManager = new AssetManager(resolver);

		addLoader(GdxTextureAtlas.class, new GdxTextureAtlasLoader());
		addLoader(GdxImage.class, new GdxImageLoader());
		addLoader(GdxMusic.class, new GdxMusicLoader());
		addLoader(GdxSound.class, new GdxSoundLoader());
		addLoader(GdxTextureRegion.class, new GdxTextureRegionLoader());
		addLoader(GdxTexture.class, new GdxTextureLoader());
	}
	
	public AnnotationAssetManager addTextureFolder(String folder) {
		textureFolders.add(folder);
		return this;
	}
	
	public AnnotationAssetManager addTextureExtension(String extension) {
		textureExtensions.add(extension);
		return this;
	}
	
	public AnnotationAssetManager addSoundFolder(String folder) {
		soundFolders.add(folder);
		return this;
	}
	
	public AnnotationAssetManager addSoundExtension(String extension) {
		soundExtensions.add(extension);
		return this;
	}
	
	public AnnotationAssetManager addMusicFolder(String folder) {
		musicFolders.add(folder);
		return this;
	}
	
	public AnnotationAssetManager addMusicExtension(String extension) {
		musicExtensions.add(extension);
		return this;
	}
	
	public AnnotationAssetManager addAtlasFolder(String folder) {
		atlasFolders.add(folder);
		return this;
	}
	
	public AnnotationAssetManager addAtlasExtension(String extension) {
		atlasExtensions.add(extension);
		return this;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public AnnotationAssetManager addLoader(Class<? extends Annotation> annotation, AnnotationAssetLoader loader) {
		loaders.put(annotation, loader);
		return this;
	}
	
	public void loadAnnotations(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		for(int i = 0; i < fields.length; i++) {
			checkField(o, fields[i]);
		}
	}
	
	private void checkField(Object o, Field field) {
		Annotation[] annotations = field.getDeclaredAnnotations();
		for(int i = 0; i < annotations.length; i++) {
			AnnotationAssetLoader loader = loaders.get(annotations[i].annotationType());
			if (loader != null) {
				loader.load(this, o, field, annotations[i]);
			}
		}
	}
	
	public TextureAtlas getAtlas(String atlasName) {
		String fullAtlasPath = findExistingAssetPath(atlasFolders, atlasName, atlasExtensions);
		if (fullAtlasPath == null) {
			return null;
		} else {
			if (!assetManager.isLoaded(fullAtlasPath, TextureAtlas.class)) {
				assetManager.load(fullAtlasPath, TextureAtlas.class);
				assetManager.finishLoading();
			}
			
			return assetManager.get(fullAtlasPath, TextureAtlas.class);
		}
	}
	
	public AnnotationAssetManager disposeAtlas(String atlasName) {
		String fullAtlasPath = findExistingAssetPath(atlasFolders, atlasName, atlasExtensions);
		if (fullAtlasPath != null) {
			if (assetManager.isLoaded(fullAtlasPath, TextureAtlas.class)) {
				assetManager.unload(fullAtlasPath);
			}
		}
		return this;
	}

	public TextureAtlas.AtlasRegion getRegion(String atlasName, String regionName) {
		TextureAtlas atlas = getAtlas(atlasName);
		if (atlas == null) {
			return null;
		} else {
			return atlas.findRegion(regionName);
		}
	}

	public Image createFitImage(String atlasName, String regionName) {
		return createImage(atlasName, regionName, Scaling.fit);
	}

	public Image createFillImage(String atlasName, String regionName) {
		return createImage(atlasName, regionName, Scaling.fill);
	}

	public Image createImage(String atlasName, String regionName, Scaling scaling) {
		Image result = new Image(getRegion(atlasName, regionName));
		result.setScaling(scaling);
		return result;
	}

	public Image createNinePatchImage(String atlasName, String regionName, int left, int right, int top, int bottom) {
		NinePatch ninePatch = new NinePatch(getRegion(atlasName, regionName), left, right, top, bottom);
		return new Image(ninePatch);
	}

	public Texture getTexture(String textureName) {
		return getTexture(textureName, Texture.TextureFilter.Linear);
	}

	public Texture getTexture(String textureName, Texture.TextureFilter filter) {
		String fullTexturePath = findExistingAssetPath(textureFolders, textureName, textureExtensions);
		if (fullTexturePath == null) {
			return null;
		} else {
			if (!assetManager.isLoaded(fullTexturePath, Texture.class)) {
				assetManager.load(fullTexturePath, Texture.class);
				assetManager.finishLoading();
			}
			
			Texture result = assetManager.get(fullTexturePath, Texture.class);
			result.setFilter(filter, filter);
			return result;
		}
	}
	
	public AnnotationAssetManager disposeTexture(String textureName) {
		String fullTexturePath = findExistingAssetPath(textureFolders, textureName, textureExtensions);
		if (fullTexturePath != null) {
			if (assetManager.isLoaded(fullTexturePath, Texture.class)) {
				assetManager.unload(fullTexturePath);
			}
		}
		return this;
	}
	
	public Sound getSound(String soundName) {
		String fullSoundPath = findExistingAssetPath(soundFolders, soundName, soundExtensions);
		if (fullSoundPath == null) {
			return null;
		} else {
			if (!assetManager.isLoaded(fullSoundPath, Sound.class)) {
				assetManager.load(fullSoundPath, Sound.class);
				assetManager.finishLoading();
			}
			
			return assetManager.get(fullSoundPath, Sound.class);
		}
	}
	
	public AnnotationAssetManager disposeSound(String soundName) {
		String fullSoundPath = findExistingAssetPath(soundFolders, soundName, soundExtensions);
		if (fullSoundPath != null) {
			if (assetManager.isLoaded(fullSoundPath, Sound.class)) {
				assetManager.unload(fullSoundPath);
			}
		}
		return this;
	}
	
	public Music getMusic(String musicName) {
		String fullMusicPath = findExistingAssetPath(musicFolders, musicName, musicExtensions);
		if (fullMusicPath == null) {
			return null;
		} else {
			if (!assetManager.isLoaded(fullMusicPath, Music.class)) {
				assetManager.load(fullMusicPath, Music.class);
				assetManager.finishLoading();
			}
			
			return assetManager.get(fullMusicPath, Music.class);
		}
	}
	
	public AnnotationAssetManager disposeMusic(String musicName) {
		String fullMusicPath = findExistingAssetPath(musicFolders, musicName, musicExtensions);
		if (fullMusicPath != null) {
			if (assetManager.isLoaded(fullMusicPath, Music.class)) {
				assetManager.unload(fullMusicPath);
			}
		}
		return this;
	}
	
	@Override
	public void dispose() {
		assetManager.dispose();
	}
	
	public String findExistingAssetPath(Array<String> searchFolders, String assetName, Array<String> assetExtensions) {
		for(int folderIndex = 0; folderIndex < searchFolders.size; folderIndex++) {
			for(int extensionIndex = 0; extensionIndex < assetExtensions.size; extensionIndex++) {
				if (assetExists(searchFolders.get(folderIndex), assetName, assetExtensions.get(extensionIndex))) {
					return getAssetFullPath(searchFolders.get(folderIndex), assetName, assetExtensions.get(extensionIndex));
				}
			}
		}
		return null;
	}
	
	public boolean assetExists(String folder, String assetName, String assetExtension) {
		return assetManager.getFileHandleResolver().resolve(getAssetFullPath(folder, assetName, assetExtension)).exists();
	}
	
	public String getAssetFullPath(String folder, String assetName, String assetExtension) {
		tmpStringBuilder.setLength(0);
		return tmpStringBuilder.append(folder).append("/").append(assetName).append(".").append(assetExtension).toString();
	}
}