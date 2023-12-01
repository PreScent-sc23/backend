package Unknown.PreScent.controller;

import Unknown.PreScent.dto.FinishedProductDto;
import Unknown.PreScent.entity.FinishedProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Unknown.PreScent.service.FinishedProductService;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/finished-product")
public class FinishedProductController {

    private final FinishedProductService finishedProductService;
    private Logger log;
    @Autowired
    public FinishedProductController(FinishedProductService finishedProductService)
    {
        this.finishedProductService = finishedProductService;
    }

//    @PostMapping("/add")
//    public FinishedProductEntity addFinishedProduct(@RequestBody Integer shopKey, String fpName, String fpTag, String fpImage, Integer fpPrice, boolean fpState, String[] fpFlowerList)
//    {
//        return finishedProductService.addFinishedProduct(shopKey, fpName, fpTag, fpImage, fpPrice, fpState, fpFlowerList);
//    }

//    @PostMapping("/add")
//    public ResponseEntity<?> addFinishedProduct(@RequestBody FinishedProductEntity finishedProductEntity)
//    {
//        System.out.println("Result: " + finishedProductEntity.getFpFlowerList() + "// //" + finishedProductEntity.getFpDetail() + "\n");
//        finishedProductService.addFinishedProduct(finishedProductEntity.getShopKey(),
//                finishedProductEntity.getFpName(),
//                finishedProductEntity.getFpTag(),
//                finishedProductEntity.getFpPrice(),
//                finishedProductEntity.getFpFlowerList(),
//                finishedProductEntity.getFpDetail());
//
//        System.out.println(finishedProductService.getFinishedProductWithShopKey(0).get().get(0).getFpFlowerList());
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    } //old version

//    @PostMapping("/add")
//    public ResponseEntity<?> addFinishedProduct(@RequestParam String shopKey,
//                                                @RequestPart(name = "fpImage", required = false) MultipartFile fpImage,
//                                                @RequestParam("fpName") String fpName,
//                                                @RequestParam("fpTag") String fpTag,
//                                                @RequestParam("fpPrice") String fpPrice,
//                                                @RequestParam("fpDetail") String fpDetail,
//                                                @RequestParam("fpFlowerList") String fpFlowerList)
//    {
//        System.out.println("shopKey 값 : "+shopKey+"--------------------------------------------");
//        System.out.println("fpName 값 : "+fpName+"--------------------------------------------");
//        System.out.println("fpTag 값 : "+fpTag+"--------------------------------------------");
//        System.out.println("fpPrice 값 : "+fpPrice+"--------------------------------------------");
//        System.out.println("fpDetail 값 : "+fpDetail+"--------------------------------------------");
//        System.out.println("fpFlowerList 값 : "+fpFlowerList+"--------------------------------------------");
//
//        if(fpImage.isEmpty()) System.out.println("fpImage is Empty!!!-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
//        if(fpImage == null) System.out.println("fpImage is Null!!!-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
//        System.out.println("-*-*-*-*-*-*-*this is fpImage filename: " + fpImage.getOriginalFilename());
//
//        String[] fpFlowerListToStringArray = fpFlowerList.split(",");
//        FinishedProductDto finishedProductDto = new FinishedProductDto(fpImage, fpName, fpTag, Integer.parseInt(fpPrice), fpDetail, fpFlowerListToStringArray);
//        finishedProductService.addFinishedProduct(Integer.parseInt(shopKey), finishedProductDto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping("/add")
    public ResponseEntity<?> addFinishedProduct(@RequestPart(name = "fpImage", required = false) MultipartFile fpImage,
                                                @RequestPart("jsonData") String jsonData)
    {
//        if(fpImage.isEmpty()) System.out.println("fpImage is Empty!!!-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
//        System.out.println("-*-*-*-*-*-*-*this is fpImage filename: " + fpImage.getOriginalFilename());
        String fileName = fpImage.getOriginalFilename();
        System.out.println("FileName is: " + fileName + "++++++++++++++++++++++++++++++++");

        JSONObject jsonObject = new JSONObject(jsonData);
        Integer shopKey = Integer.parseInt(jsonObject.getString("shopKey"));
        String fpName = jsonObject.getString("fpName");
        String fpTag = jsonObject.getString("fpTag");
        Integer fpPrice = Integer.parseInt(jsonObject.getString("fpPrice"));
        String fpDetail = jsonObject.getString("fpDetail");
        String fpFlowerList = jsonObject.getString("fpFlowerList");

        String[] fpFlowerListToStringArray = fpFlowerList.split(",");
        FinishedProductDto finishedProductDto = new FinishedProductDto(fpImage, fpName, fpTag, fpPrice, fpDetail, fpFlowerListToStringArray);
        finishedProductService.addFinishedProduct(shopKey, finishedProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/testadd")
    public ResponseEntity<?> handleFileUpload(
            @RequestPart("fpImage") MultipartFile fpImage,
            @RequestPart(value = "description", required = false) String description) {

        // 파일 처리 로직
        if (!fpImage.isEmpty()) {
            // 파일이 비어 있지 않은 경우에만 처리
            try {
                byte[] fileBytes = fpImage.getBytes();
                // 여기에서 파일을 저장하거나 추가 로직을 수행할 수 있습니다.
                System.out.println("File Name: " + fpImage.getOriginalFilename());
                System.out.println("File Size: " + fpImage.getSize());
                System.out.println("Description: " + description);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Error while processing the file.");
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/{fpKey}")
    public Optional<FinishedProductEntity> getFinishedProductByFpKey(@PathVariable Integer fpKey)
    {
        return finishedProductService.getFinishedProductWithFpKey(fpKey);
    }
    @GetMapping("/{fpName}")
    public Optional<List<FinishedProductEntity>> getFinishedProductByFpName(@PathVariable String fpName)
    {
        return finishedProductService.getFinishedProductWithFpName(fpName);
    }
    @GetMapping("/{fpTag}")
    public Optional<List<FinishedProductEntity>> getFinishedProductByFpTag(@PathVariable String fpTag)
    {
        return finishedProductService.getFinishedProductWithFpTag(fpTag);
    }
//    @GetMapping("/{flower}")
//    public Optional<List<FinishedProduct>> getFinishedProductWithFlower(@PathVariable String flower)
//    {
//        return finishedProductService.getFinishedProductWithFlower(flower);
//    }
}