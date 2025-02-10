package com.suyuri.ttodokproject.entity;

import com.suyuri.ttodokproject.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "tbl_member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20, nullable = false)
    private String memberId;

    @Column(length = 20, nullable = false)
    private String memberPw;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Column(length = 20, nullable = false)
    private String memberPhone;

    @Column(length = 20, nullable = false)
    private String memberNick;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int point;

    @Column
    private LocalDate birthDate;

    public String getMemberNick() {
        return memberNick;
    }


    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberPw(memberDTO.getMemberPw());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        memberEntity.setMemberNick(memberDTO.getMemberNick());

        LocalDate birthDate = LocalDate.of(memberDTO.getYear(), memberDTO.getMonth(), memberDTO.getDay());
        memberEntity.setBirthDate(birthDate);

        return memberEntity;
    }
}
