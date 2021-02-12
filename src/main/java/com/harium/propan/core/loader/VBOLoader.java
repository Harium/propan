package com.harium.propan.core.loader;

import com.harium.propan.core.model.Model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface VBOLoader {

    String DEFAULT_GROUP_NAME = "default";

    Model loadModel(URL url, String path) throws IOException;

    Model loadModel(String fpath, String path, InputStream stream) throws IOException;

}
