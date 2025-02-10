package com.suyuri.ttodokproject.dto;

import com.suyuri.ttodokproject.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {

    private Long id;
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberPhone;
    private String memberNick;
    private int memberPoint;
    private int year;
    private int month;
    private int day;



    //년, 월, 일 값을 설정하는 데 사용함.
    public void setYear(int year) {
        this.year = year;
    } //각각 값 설정

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LocalDate getBirthDate() {
        return LocalDate.of(year, month, day);
    }

    public void setBirthDate(LocalDate birthDate) {
        this.year = birthDate.getYear();
        this.month = birthDate.getMonthValue();
        this.day = birthDate.getDayOfMonth();
    }

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberPw(memberEntity.getMemberPw());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDTO.setMemberNick(memberEntity.getMemberNick());

        return memberDTO;
    }

    public MemberDTO(String memberId, int memberPoint) {
        this.memberId = memberId;
        this.memberPoint = memberPoint;
    }

}