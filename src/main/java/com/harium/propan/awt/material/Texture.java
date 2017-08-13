package com.harium.propan.awt.material;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.layer.BufferedLayer;

import java.awt.image.BufferedImage;

public class Texture extends BufferedLayer {

    public Texture(BufferedImage image) {
        super(image);
        flipHorizontal();
    }

    public byte[][][] getAlphaBytes() {
        byte imagem2D[][][] = new byte[w][h][4];

        Color c;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {

                c = new Color(buffer.getRGB(j, i));

                imagem2D[j][i][0] = (byte) c.getRed();
                imagem2D[j][i][1] = (byte) c.getGreen();
                imagem2D[j][i][2] = (byte) c.getBlue();
                imagem2D[j][i][3] = (byte) c.getAlpha();
            }
        }

        return imagem2D;
    }

}
