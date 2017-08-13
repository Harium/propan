package com.harium.propan.core.writer;

import java.io.IOException;

import com.harium.propan.core.model.Model;

public interface VBOWriter {

	public void writeVBO(Model vbo, String filename) throws IOException;
	
}
