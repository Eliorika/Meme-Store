package dev.chipichapa.memestore.utils;

import dev.chipichapa.memestore.exception.AppException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageUtils {
    public static BufferedImage byteArrayToBufferedImage(byte[] bytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
