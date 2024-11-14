package seeya.insight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seeya.insight.entity.GameRoomTb;
import seeya.insight.entity.MbrAccountTb;

@Repository
public interface GAME_ROOM_TB_Repository  extends JpaRepository<GameRoomTb,Integer> {
}
