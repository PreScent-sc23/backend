package net.prescent.service;

import net.prescent.dto.FinishedProductDto;
import net.prescent.dto.FlowerShopDto;
import net.prescent.dto.SellerDto;
import net.prescent.entity.FinishedProductEntity;
import net.prescent.entity.FlowerShopEntity;
import net.prescent.repository.FinishedProductRepository;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class FinishedProductEntityServiceTest {
    @Autowired
    private FinishedProductService finishedProductService;

    @Autowired
    private FinishedProductRepository finishedProductRepository;

    @Autowired
    private SearchService searchService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private FlowerShopService flowerShopService;

    @BeforeEach
    public void setUp() {
        finishedProductRepository.deleteAllInBatch();
    }

    public FinishedProductDto createFinishedProductDto()
    {
        FinishedProductDto finishedProductDto = new FinishedProductDto();
        finishedProductDto.setFpName("장미꽃다발");
        finishedProductDto.setFpTag("연인");
        finishedProductDto.setFpImage(null);
        finishedProductDto.setFpPrice(20000);
        finishedProductDto.setFpFlowerList("장미 안개꽃");
        return finishedProductDto;
    }
    public FlowerShopDto createFlowerShopDto(){
        FlowerShopDto flowerShopDto = new FlowerShopDto();
        flowerShopDto.setSellerKey(12345678);
        flowerShopDto.setShopName("it's me");
        flowerShopDto.setShopPhoneNum("031-308-8223");
        flowerShopDto.setShopLocation("suwon city");
        flowerShopDto.setOpenHour(10);
        flowerShopDto.setOpenMinute(0);
        flowerShopDto.setCloseHour(20);
        flowerShopDto.setCloseMinute(0);
        flowerShopDto.setWorkday(new String[]{"월", "화", "수", "목", "금"});
        flowerShopDto.setDescription("안녕하세요 디스크립션 입니다.");
        return flowerShopDto;
    }
    public SellerDto createSellerDto(Integer sellerKey) {
        SellerDto sellerDto = new SellerDto();
        sellerDto.setSellerKey(sellerKey);
        sellerDto.setSellerName("suhyeon");
        sellerDto.setSellerPhonenum("010-1111-2222");
        sellerDto.setSellerIdEmail("ajou@gmail.com");
        sellerDto.setSellerPassword("04prescent");
        return sellerDto;
    }

    @Test
    @DisplayName("완제품 등록")
    public void testAddFinishedProduct()
    {
        SellerDto sellerDto = createSellerDto(12345678);
        SellerDto savedSellerDto = sellerService.signup(sellerDto);

        FlowerShopDto flowerShopDto = createFlowerShopDto();
        FlowerShopEntity addedShop = flowerShopService.addFlowerShop(flowerShopDto);

        FinishedProductDto finishedProductDto = createFinishedProductDto();
        finishedProductDto.setShopKey(addedShop.getShopKey());
        FinishedProductEntity addedFinishedProductEntity = finishedProductService.addFinishedProduct(finishedProductDto);

        //finishedProductRepository.findByShopKeyContaining(1);

        assertThat(addedFinishedProductEntity).isNotNull();

        Integer testFpIndex = addedFinishedProductEntity.getFpKey();

        FinishedProductEntity retrievedShop = finishedProductService.getFinishedProductWithFpKey(testFpIndex).orElse(null);
        assertThat(retrievedShop).isNotNull();
        assertThat(retrievedShop.getFpName()).isEqualTo("장미꽃다발");
    }

//    @Test
//    @DisplayName("완제품 복수 등록")
//    public void testAddManyFinishedProduct()
//    {
//        FinishedProductEntity addedFinishedProductEntity1 = finishedProductService.addFinishedProduct(1, "장미꽃다발1", "연인", null, 20000+1000, true, new String[]{"장미", "안개꽃"});
//
//        for(int i = 0; i < 10; i++) {
//            FinishedProductEntity addedFinishedProductEntity = finishedProductService.addFinishedProduct(i%3, "소국꽃다발"+i, "연인", null, 20000+(i*1000), true, new String[]{"장미", "안개꽃"});
//            assertThat(addedFinishedProductEntity).isNotNull();
//
//            Integer testFpIndex = addedFinishedProductEntity.getFpKey();
//
//            FinishedProductEntity retrievedShop = finishedProductService.getFinishedProductWithFpKey(testFpIndex).orElse(null);
//            assertThat(retrievedShop).isNotNull();
//            assertThat(retrievedShop.getFpName()).isEqualTo("소국꽃다발"+i);
//        }
//    }

//    @Test
//    @DisplayName("태그 검색 수동")
//    public void testTagSearch(){
//        this.testAddManyFinishedProduct();
//        String fpTag = "연인";
//        Optional<List<FinishedProductEntity>> fpList = finishedProductService.getFinishedProductWithFpTag(fpTag);
//
//        assertThat(fpList).isNotNull();
//
//        List<FinishedProductEntity> finishedProductEntities = fpList.get();
//
//        for(FinishedProductEntity fp : finishedProductEntities) System.out.println(fp.getFpPrice());
//    }

//    @Test
//    @DisplayName("태그 검색 Page")      //Optional 문제로 포기
//    public void testTagSearchPage(){
//        this.testAddManyFinishedProduct();
//
//        Page<Optional<FinishedProductEntity>> fpPage =  searchService.searchByTag("연인", "fpPrice", 0);
//
//        assertThat(fpPage).isNotNull();
//
//        System.out.println("---------Start---------Start---------");
//
//        List<Optional<FinishedProductEntity>> fpList = fpPage.getContent();
//
//        assertThat(fpList).isNotNull();
//
//        for(Optional<FinishedProductEntity> fp : fpList) System.out.println(fp);
//
//
//        System.out.println("---------End---------End---------");
//
//
//    }

//    @Test
//    @DisplayName("태그 검색 List")
//    public void testTagSearchSort(){
//        this.testAddManyFinishedProduct();
//
//        Optional<List<FinishedProductEntity>> searchResult =  searchService.searchByTagAsc("연인", "fpPrice");
//        Optional<List<FinishedProductEntity>> searchResult1 =  searchService.searchByTagDesc("연인", "fpPrice");
//
//        assertThat(searchResult).isNotNull();
//        assertThat(searchResult1).isNotNull();
//
//        System.out.println("---------Start---------Start---------");
//
//        List<FinishedProductEntity> fpList = searchResult.get();
//        List<FinishedProductEntity> fpList1 = searchResult1.get();
//
//        assertThat(fpList).isNotNull();
//        assertThat(fpList1).isNotNull();
//
//        for(FinishedProductEntity fp : fpList) System.out.println(fp.getFpName() + "\t" + fp.getFpPrice());
//        System.out.println("---------Asc---------Asc---------");
//        for(FinishedProductEntity fp : fpList1) System.out.println(fp.getFpName() + "\t" + fp.getFpPrice());
//
//
//        System.out.println("---------End---------End---------");
//
//
//    }

}