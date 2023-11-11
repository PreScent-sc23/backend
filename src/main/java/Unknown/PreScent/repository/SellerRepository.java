package Unknown.PreScent.repository;

import Unknown.PreScent.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerEntity, Integer> {

    //SellerDto save(SellerDto seller);
    Optional<SellerEntity> findBySellerKey(Integer sellerKey);
    Optional<SellerEntity> findBySellerName(String sellerName);
    Optional<SellerEntity> findBySellerId(String sellerId);
    Optional<SellerEntity> findBySellerPassword(String sellerPassword);
    Optional<SellerEntity> findBySellerPhonenum(String sellerPhonenum);
    //List<SellerDto> findAll();

}
