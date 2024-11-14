package seeya.insight.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import seeya.insight.entity.GameRoomTb;
import seeya.insight.entity.MbrAccountTb;
import seeya.insight.repository.MBR_ACCOUNT_TB_Repository;

import java.util.Optional;

@Service
@Transactional
public class MBR_ACCOUNT_TB_Service {


    private MBR_ACCOUNT_TB_Repository mbrAccountTbRepository;

    @Autowired
    public void setMbrAccountTbRepository(MBR_ACCOUNT_TB_Repository mbrAccountTbRepository) {
        this.mbrAccountTbRepository = mbrAccountTbRepository;
    }

    //회원가입
    public void join(MbrAccountTb mbrAccountTb)
    {
        mbrAccountTbRepository.save(mbrAccountTb);
    }

    //특정 회원불러오기
    public Optional<MbrAccountTb> viewById(String MbrId)
    {
        return mbrAccountTbRepository.findByMbrId(MbrId);
    }

    public Optional<MbrAccountTb> viewByNm(String MbrNm)
    {
        return mbrAccountTbRepository.findByMbrNm(MbrNm);
    }

    public Optional<MbrAccountTb> viewByCertificationNumber(String MbrCertificationNumber)
    {
        return mbrAccountTbRepository.findByMbrCertificationNumber(MbrCertificationNumber);
    }

    public Optional<MbrAccountTb> viewByEm(String MbrEm)
    {
        return mbrAccountTbRepository.findByMbrEm(MbrEm);
    }

    //모든 회원 불러오기
    public Page<MbrAccountTb> viewAllMbr(Pageable pageable)
    {
        return mbrAccountTbRepository.findAll(pageable);
    }



}
