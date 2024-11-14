package seeya.insight.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name ="mbr_account_tb")
@Builder
public class MbrAccountTb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mbrSeq;

    @Column(name="MBR_ID",nullable = false, length = 100, updatable = true)
    private String mbrId;

    @Column(name="MBR_PWD",nullable = false, length = 100)
    private String mbrPwd;

    @Column(name="MBR_PWD_CHECK",nullable = false, length = 100)
    private String mbrPwdCheck;

    @Column(name="MBR_NM",nullable = false, length = 100)
    private String mbrNm;

    @Column(name="MBR_EM",nullable = false, length = 100)
    private String mbrEm;

    @Column(name="MBR_CERTIFI_NB",nullable = true, length = 100)
    private String mbrCertificationNumber;

}
