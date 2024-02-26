package dev.chipichapa.memestore.service;

import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import dev.chipichapa.memestore.service.ifc.ImageProcessingService;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiImageProcessingService implements ImageProcessingService {

    private final Predictor<Image, DetectedObjects> predictor;

    @Override
    @SneakyThrows
    public List<String> predictTags(byte[] image) {
        @Cleanup ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
        Image img = getImageFromInputStream(inputStream);

        DetectedObjects detectedObject = predictor.predict(img);
        return extractTags(detectedObject);
    }

    @SneakyThrows
    private Image getImageFromInputStream(InputStream stream) {
        return ImageFactory.getInstance().fromInputStream(stream);
    }

    private List<String> extractTags(DetectedObjects objects) {
        List<String> result = new ArrayList<>(objects.getNumberOfObjects());

        for (int i = 0; i < objects.getNumberOfObjects(); i++) {
            String detectedTag = objects.item(i).getClassName();
            result.add(detectedTag);
        }
        return result;
    }
}
