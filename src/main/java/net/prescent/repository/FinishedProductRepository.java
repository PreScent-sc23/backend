package net.prescent.repository;

import net.prescent.entity.FinishedProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinishedProductRepository extends JpaRepository<FinishedProductEntity, Integer>
{
    Optional<FinishedProductEntity> findByFpKey(Integer fpKey);
    Optional<List<FinishedProductEntity>> findByFpNameContaining(String fpName);
    //Optional<List<FinishedProductEntity>> findByShopKey(Integer shopKey); //Not working
    Optional<List<FinishedProductEntity>> findByFpTagContaining(String fpTag);
    Optional<List<FinishedProductEntity>> findByFpTagContaining(String fpTag, Sort sort);
    Page<Optional<FinishedProductEntity>> findByFpTagContaining(String fpTag, Pageable pageable);
//    @Query("SELECT e FROM FinishedProduct e WHERE :flower  MEMBER OF e.fpFlowerList")
//    Optional<List<FinishedProduct>> findByValue(@Param("flower") String flower);
}