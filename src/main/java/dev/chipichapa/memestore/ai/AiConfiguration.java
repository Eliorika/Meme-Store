package dev.chipichapa.memestore.ai;

import ai.djl.Application;
import ai.djl.engine.Engine;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfiguration {

    @Bean
    public ZooModel<Image, DetectedObjects> zooModel() throws Exception {
        String backbone;
        if ("TensorFlow".equals(Engine.getDefaultEngineName())) {
            backbone = "mobilenet_v2";
        } else {
            backbone = "resnet50";
        }
        Criteria<Image, DetectedObjects> criteria =
                Criteria.builder()
                        .optApplication(Application.CV.OBJECT_DETECTION)
                        .setTypes(Image.class, DetectedObjects.class)
                        .optFilter("backbone", backbone)
                        .optEngine(Engine.getDefaultEngineName())
                        .optProgress(new ProgressBar())
                        .build();

        return criteria.loadModel();
    }

    @Bean
    public Predictor<Image, DetectedObjects> predictor() throws Exception {
        return zooModel().newPredictor();
    }
}
