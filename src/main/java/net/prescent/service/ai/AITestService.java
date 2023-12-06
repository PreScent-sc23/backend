package net.prescent.service.ai;

import lombok.extern.slf4j.Slf4j;
import net.prescent.entity.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class AITestService {

    private final AIs3Service aiModelService;

    @Autowired
    public AITestService(AIs3Service aiModelService) {
        this.aiModelService = aiModelService;
    }

    public List<Object> processAdditionalImages() throws IOException {
        //log.info("Processing additional images");
        List<Object> images = new ArrayList<>();

        //String image2Key = "predefined/159_2021042815384918.jpg";
        //aiModelService.uploadPredefinedFileToS3("/crops/159_2021042815384918.jpg", image2Key);
        //images.add(Map.of("url", aiModelService.getFileUrl(image2Key)));

        List<ImageInfo> additionalImages = Arrays.asList(
                new ImageInfo("159_20210428153849183.jpg", "Daisy", "Lovely"),
                new ImageInfo("159_20210428153849184.jpg", "Gerbera", "Mysterious"),
                new ImageInfo("159_20210428153849185.jpg", "Rose", "Love"),
                new ImageInfo("159_20210428153849186.jpg", "Lily", "Purity")
        );

        for (ImageInfo image : additionalImages) {
            String fileKey = "predefined/" + image.getUrl();
            String filePath = "src/main/resources/static/crops/" + image.getUrl(); // Adjust the path as needed
            aiModelService.uploadFileToS3(filePath, fileKey);
            image.setUrl(aiModelService.getFileUrl(fileKey));
            images.add(Map.of("url", image.getUrl(), "name", image.getName(), "meaning", image.getMeaning()));
        }
        log.info("Finished processing additional images");
        return images;
    }
}

//@Service
//public class AITestService {
//
//    private final AIModelService aiModelService;
//
//    public AITestService(AIModelService aiModelService) {
//        this.aiModelService = aiModelService;
//    }
//
//    public String uploadFileToS3(MultipartFile file) throws IOException {
//        // Generate a unique file key for each uploaded file
//        String fileKey = "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
//        // Upload the file and return its URL
//        aiModelService.uploadFileToS3(file, fileKey);
//        return aiModelService.getFileUrl(fileKey);
//    }
//
//    public List<ImageInfo> getImageUrlsAndDetails() {
//        // Generate dummy URLs for demonstration; replace with your actual logic
//        return Arrays.asList(
//                new ImageInfo(aiModelService.getFileUrl("uploads/" + UUID.randomUUID() + "-daisy.jpg"), "Daisy", "Lovely"),
//                new ImageInfo(aiModelService.getFileUrl("uploads/" + UUID.randomUUID() + "-gerbera.jpg"), "Gerbera", "Mysterious"),
//                new ImageInfo(aiModelService.getFileUrl("uploads/" + UUID.randomUUID() + "-white_rose.jpg"), "White Rose", "Purity")
//        );
//    }
//}

