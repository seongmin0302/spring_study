package seeya.insight.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name ="GAME_ROOM_TB")
@Builder
public class GameRoomTb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_SEQ")
    private Integer roomSeq;

    @Column(name="ROOM_TITLE",nullable = false, length = 100, updatable = true)
    private String roomTitle;

    @Column(name="ROOM_PLAYER1",nullable = false, length = 100)
    private String roomPlayer1;

    @Column(name="ROOM_PLAYER2",nullable = true, length = 100)
    private String roomPlayer2;

    @Column(name="ROOM_STATUS",nullable = false, length = 100)
    private String roomStatus;

    @Column(name="win",nullable = true, length = 100)
    private String win;

    @Column(name="lose",nullable = true, length = 100)
    private String lose;

    @Column(name="draw",nullable = true, length = 100)
    private String draw;
}
