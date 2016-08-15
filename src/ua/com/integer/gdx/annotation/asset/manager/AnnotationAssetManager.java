package ua.com.integer.gdx.annotation.asset.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

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
import ua.com.integer.gdx.annotation.asset.manager.imp.ttf.GdxTTFFont;
import ua.com.integer.gdx.annotation.asset.manager.imp.ttf.GdxTTFFontLoader;

public class AnnotationAssetManager implements Disposable {
	private StringBuilder tmpStringBuilder = new StringBuilder();

	private Array<String> textureFolders = new Array<>(new String[] {"textures"});
	private Array<String> textureExtensions = new Array<>(new String[] {"jpg", "png"});
	
	private Array<String> soundFolders = new Array<>(new String[] {"sounds"});
	private Array<String> soundExtensions = new Array<>(new String[] {"mp3", "ogg"});
	
	private Array<String> musicFolders = new Array<>(new String[] {"music"});
	private Array<String> musicExtensions = new Array<>(new String[] {"mp3", "ogg"});
	
	private Array<String> atlasFolders = new Array<String>(new String[] {"atlases"});
	private Array<String> atlasExtensions = new Array<String>(new String[] {"atlas", "pack"});
	
	private Array<String> i18nFolders = new Array<String>(new String[] {"i18n"});
	
	private AssetManager assetManager;
	private ObjectMap<Class<? extends Annotation>, AnnotationAssetLoader> loaders = new ObjectMap<>();
	
	private FreetypeFontManager fontManager;
	
	public AnnotationAssetManager() {
		this(new InternalFileHandleResolver());
	}

	public AnnotationAssetManager(FileHandleResolver resolver) {
		assetManager = new AssetManager(resolver);
		fontManager = new FreetypeFontManager();
		
		addLoader(GdxTextureAtlas.class, new GdxTextureAtlasLoader());
		addLoader(GdxImage.class, new GdxImageLoader());
		addLoader(GdxMusic.class, new GdxMusicLoader());
		addLoader(GdxSound.class, new GdxSoundLoader());
		addLoader(GdxTextureRegion.class, new GdxTextureRegionLoader());
		addLoader(GdxTexture.class, new GdxTextureLoader());
		addLoader(GdxTTFFont.class, new GdxTTFFontLoader());
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
	
	public AnnotationAssetManager addI18NFolder(String folder) {
		i18nFolders.add(folder);
		return this;
	}
	
	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public FreetypeFontManager getFontManager() {
		return fontManager;
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
	
	public Texture getTexture(String textureName) {
		String fullTexturePath = findExistingAssetPath(textureFolders, textureName, textureExtensions);
		if (fullTexturePath == null) {
			return null;
		} else {
			if (!assetManager.isLoaded(fullTexturePath, Texture.class)) {
				assetManager.load(fullTexturePath, Texture.class);
				assetManager.finishLoading();
			}
			
			return assetManager.get(fullTexturePath, Texture.class);
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
		fontManager.dispose();
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