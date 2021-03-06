package com.harium.propan.core.loader;

import com.harium.etyl.loader.LoaderImpl;
import com.harium.etyl.util.StringUtils;
import com.harium.etyl.util.io.IOHelper;
import com.harium.propan.core.loader.motion.BVHLoader;
import com.harium.propan.core.loader.motion.MotionLoader;
import com.harium.propan.core.model.motion.Motion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnimationLoader extends LoaderImpl {

    private static AnimationLoader instance = null;

    private Map<String, MotionLoader> loaders = new HashMap<String, MotionLoader>();

    public static final String BVH = "bvh";

    public static AnimationLoader getInstance() {
        if (instance == null) {
            instance = new AnimationLoader();
        }

        return instance;
    }

    private AnimationLoader() {
        super();

        folder = "assets/animations/";

        loaders.put(BVH, new BVHLoader());
    }

    public Motion loadMotion(String path) {
        return loadMotion(path, false);
    }

    public Motion loadMotion(String path, boolean absolutePath) {

        URL dir = null;
        try {
            if (!absolutePath) {
                dir = getFullURL(path);
            } else {
                dir = new URL(IOHelper.FILE_PREFIX + path);
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        String ext = StringUtils.fileExtension(path);
        MotionLoader loader = getLoader(ext);

        if (loader == null) {
            System.out.println("Propan can't load " + ext + " files.");
        } else {
            try {
                return loader.loadMotion(dir, path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Asset " + path + " not found.");
            }
        }

        return null;
    }

    public MotionLoader getLoader(String extension) {
        return loaders.get(extension);
    }

    public void addLoader(String extension, MotionLoader loader) {
        loaders.put(extension, loader);
    }

    public Set<String> supportedExtensions() {
        return loaders.keySet();
    }

}