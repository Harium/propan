package com.harium.propan.core.writer;

import com.harium.propan.core.model.Model;

import java.io.IOException;
import java.io.OutputStream;

public interface VBOWriter {

	void writeVBO(Model vbo, String filename) throws IOException;
	
}
