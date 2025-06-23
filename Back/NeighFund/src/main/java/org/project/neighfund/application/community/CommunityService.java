package org.project.neighfund.application.community;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.neighfund.config.CustomUserDetails;
import org.project.neighfund.domain.community.Community;
import org.project.neighfund.domain.community.CommunityRepository;
import org.project.neighfund.domain.member.Member;
import org.project.neighfund.domain.member.MemberRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    //작성
    @Transactional
    public void createPost(CommunityDto communityDto, Member loginUser) {
        validateMember(loginUser);
        validateCreate(communityDto);
        Community community = Community.builder()
                .member(loginUser)
                .category(communityDto.getCategory())
                .title(communityDto.getTitle())
                .content(communityDto.getContent())
                .build();
        communityRepository.save(community);
    }

    //수정
    @Transactional
    public void editPost(Long id, CommunityDto communityDto, Member loginUser) {
        Community community = validatePost(id);
        validateMember(loginUser, community.getMember());
        validateCreate(communityDto);

        community.setCategory(communityDto.getCategory());
        community.setTitle(communityDto.getTitle());
        community.setContent(communityDto.getContent());
    }

    // 사용자 정보 확인
    public void validateMember (Member loginUser){
        Member foundMember = memberRepository.findById(loginUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당사용자가 존재하지 않습니다"));

        if (!foundMember.getEmail().equals(loginUser.getEmail())) {
            throw new AccessDeniedException("사용자 정보가 일치하지 않습니다.");
        }
    }

    // 글작성 공백확인
    public void validateCreate (CommunityDto communityDto){
        if (communityDto.getTitle() == null || communityDto.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목을 입력하세요");
        }
        if (communityDto.getContent() == null || communityDto.getContent().isBlank()) {
            throw new IllegalArgumentException("내용을 입력하세요");
        }
    }

    //작성자확인
    public void validateMember (Member loginUser, Member writer){
        if (!loginUser.getId().equals(writer.getId())) {
            throw new AccessDeniedException("작성자만 가능합니다");
        }
    }

    //글존재유무확인
    public Community validatePost (Long id){
        return communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다"));
    }


}
