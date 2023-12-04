package net.prescent.dto;

import lombok.Getter;
import lombok.Setter;
import net.prescent.entity.FinishedProductEntity;

@Getter
@Setter
public class CartItemResponseDto {
    private String flowerShopName;
    private Integer fpKey;
    private String fpImage;
    private String fpName;
    private String fpTag;
    private Integer fpPrice;
    private String fpDetail;
    private String[] fpFlowerList;
}