package seeya.insight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seeya.insight.entity.MbrAccountTb;

import java.util.Optional;

@Repository
public interface MBR_ACCOUNT_TB_Repository extends JpaRepository<MbrAccountTb,Integer> {
    Optional<MbrAccountTb> findByMbrId(String MbrId);
    Optional<MbrAccountTb> findByMbrNm(String MbrId);
    Optional<MbrAccountTb> findByMbrEm(String MbrId);
    Optional<MbrAccountTb> findByMbrCertificationNumber(String MbrCertificationNumber);

}
