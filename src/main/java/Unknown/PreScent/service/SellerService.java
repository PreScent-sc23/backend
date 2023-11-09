package Unknown.PreScent.service;

import Unknown.PreScent.dto.SellerDto;
import Unknown.PreScent.entity.SellerEntity;
import Unknown.PreScent.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(SellerDto sellerDto){
        SellerEntity sellerEntity = SellerEntity.toSellerEntity(sellerDto);
        validateDuplicatedSeller(sellerEntity);

        String encoded;
        encoded = passwordEncoder.encode(sellerEntity.getSellerPassword());
        sellerEntity.setSellerPassword(encoded);

        sellerRepository.save(sellerEntity);
        return sellerEntity.getSellerKey();
    }

    private void validateDuplicatedSeller(SellerEntity seller) {
        sellerRepository.findBySellerKey(seller.getSellerKey())
                .ifPresent(s ->{
                    throw new IllegalStateException("이미 등록된 사업자입니다.");
                });
    }

    public SellerEntity saveSeller(SellerEntity sellerEntity){
        validateDuplicatedSeller(sellerEntity);
        return sellerRepository.save(sellerEntity);
    }

    public SellerDto login(String id, String password) {

        Optional<SellerEntity> byId = sellerRepository.findBySellerIdEmail(id);
        if (byId.isPresent()) {
            SellerEntity seller = byId.get();
            if (passwordEncoder.matches(password, seller.getSellerPassword())) {
                // 비밀번호 일치
                return SellerDto.toSellerDto(seller);
            } else {
                // 비밀번호 불일치
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            // ID가 데이터베이스에 없음
            throw new IllegalArgumentException("존재하지 않는 사용자 Email입니다.");
        }
    }
}
