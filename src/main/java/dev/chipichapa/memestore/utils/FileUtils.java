package dev.chipichapa.memestore.utils;

import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
//import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {
    public final static Map<MediaType, String> ALLOWED_MIME_EXTENSIONS = new HashMap<MediaType, String>() {{
        put(MediaType.image("jpeg"), "jpg");
        put(MediaType.image("png"), "png");
    }};

    public static MediaType getRealMimeType(byte[] file) {
        AutoDetectParser parser = new AutoDetectParser();
        Detector detector = parser.getDetector();
        try {
            Metadata metadata = new Metadata();
            TikaInputStream stream = TikaInputStream.get(file);
            MediaType mediaType = detector.detect(stream, metadata);
            return mediaType;
        } catch (IOException e) {
            return MediaType.OCTET_STREAM;
        }
    }
}
