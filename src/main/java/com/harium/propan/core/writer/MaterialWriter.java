package com.harium.propan.core.writer;

import java.io.IOException;
import java.util.Map;

public interface MaterialWriter<T> {

	void writeMaterial(T material, String filename) throws IOException;

	void writeMaterials(Map<String, T> materials, String filename) throws IOException;
	
}
