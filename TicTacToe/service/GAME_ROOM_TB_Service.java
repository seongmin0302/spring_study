package seeya.insight.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import seeya.insight.entity.GameRoomTb;
import seeya.insight.repository.GAME_ROOM_TB_Repository;
import seeya.insight.repository.MBR_ACCOUNT_TB_Repository;

import java.util.List;
import java.util.Optional;

@Service
public class GAME_ROOM_TB_Service {

    private MBR_ACCOUNT_TB_Repository mbrAccountTbRepository;
    private GAME_ROOM_TB_Repository gameRoomTbRepository;

    @Autowired
    public void setMbrAccountTbRepository(MBR_ACCOUNT_TB_Repository mbrAccountTbRepository) {
        this.mbrAccountTbRepository = mbrAccountTbRepository;
    }

    @Autowired
    public void setGameRoomTbRepository(GAME_ROOM_TB_Repository gameRoomTbRepository) {
        this.gameRoomTbRepository = gameRoomTbRepository;
    }

    //게임룸 만들기
    public Optional<GameRoomTb> creat(GameRoomTb gameRoomTb)
    {
        return Optional.of(gameRoomTbRepository.save(gameRoomTb));  //gameRoomTbRepository.save(gameRoomTb); 메서드는 JPA에서 엔티티를 저장하고, 저장된 엔티티를 반환
    }

    //특정 게임방 불러오기
    public Optional<GameRoomTb> viewByRoomSeq(Integer id)
    {
        return gameRoomTbRepository.findById(id);
    }

    //모든 게임룸 불러오기
    public Page<GameRoomTb> gameRoomList(Pageable pageable)
    {
        return gameRoomTbRepository.findAll(pageable);
    }


}
