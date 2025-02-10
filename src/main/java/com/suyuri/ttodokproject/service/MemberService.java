package com.suyuri.ttodokproject.service;

import com.suyuri.ttodokproject.dto.MemberDTO;
import com.suyuri.ttodokproject.entity.MemberEntity;
import com.suyuri.ttodokproject.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    //생성자 의존성 주입
    private final MemberRepository memberRepository;

    //회원가입
    public void save(MemberDTO memberDTO) {

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    //로그인
    public Map<String, String> login(MemberDTO memberDTO) {

        Map<String, String> resultMap = new HashMap<>();
        Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(memberDTO.getMemberId());

        if (byMemberId.isPresent()) {
            MemberEntity memberEntity = byMemberId.get();

            if (memberEntity.getMemberPw().equals(memberDTO.getMemberPw())) {

                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);

                resultMap.put("status", "success");
                resultMap.put("message", "로그인 성공");
                resultMap.put("data", dto.toString());
            } else {
                resultMap.put("status", "error");
                resultMap.put("message", "비밀번호가 일치하지 않습니다.");
            }
        } else {
            resultMap.put("status", "error");
            resultMap.put("message", "아이디가 일치하지 않습니다.");
        }
        return resultMap;
    }


    //아이디 중복확인 및 유효성 검사
    public String idCheck(String memberId) {

        Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(memberId);

        if(byMemberId.isPresent()) {
            return null;
        } else {
            return "ok";
        }
    }

    //패스워드 중복확인 및 유효성 검사
    public String passwordCheck(String memberPw) {

        Optional<MemberEntity> byMemberPw = memberRepository.findByMemberPw(memberPw);

        if(byMemberPw.isPresent()) {
            return null;
        } else {
            return "ok";
        }
    }


    // 회원 닉네임 조회
    public String getNickName(String memberId) {
        Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(memberId);
        return byMemberId.map(MemberEntity::getMemberNick).orElse(null);
    }

    // 포인트 조회
    public int getMemberPoint(String memberId) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberId(memberId);
        return optionalMember.map(MemberEntity::getPoint).orElse(0);
    }

    // 회원탈퇴
    public boolean deleteMember(String memberId) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberId(memberId);

        if (optionalMember.isPresent()) {
            memberRepository.delete(optionalMember.get());
            return true;
        } else {
            return false;
        }
    }

    // 퀴즈 후 포인트 갱신
    @Transactional
    public String updateMemberPoint(MemberDTO memberDTO) {

        String loginId = memberDTO.getMemberId();
        int updatedPoint = memberDTO.getMemberPoint();

        Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(loginId);

        if (byMemberId.isPresent()) {
            MemberEntity memberEntity = byMemberId.get();
            memberEntity.setPoint(updatedPoint);
            memberRepository.save(memberEntity);
            return "/main_ver2";
        } else {
            return "/main_ver2";
        }
    }

    // 상품 구매 후 포인트 갱신
    @Transactional
    public String updateProductMemberPoint(MemberDTO memberDTO) {
        String loginId = memberDTO.getMemberId();
        int updatedProductPoint = memberDTO.getMemberPoint();

        Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(loginId);

        if(byMemberId.isPresent()) {
            MemberEntity memberEntity = byMemberId.get();
            memberEntity.setPoint(updatedProductPoint);
            memberRepository.save(memberEntity);
            return "/pointshop";
        } else {
            return "/pointshop";
        }
    }
}