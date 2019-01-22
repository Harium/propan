package com.harium.propan.core.loader.mesh.collada.sax;

import org.xml.sax.Attributes;

public interface ParseListener {

    void startElement(String uri, String localName, String qName, Attributes attributes);

}
