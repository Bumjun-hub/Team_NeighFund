package org.project.neighfund.application.community;

import lombok.RequiredArgsConstructor;
import org.project.neighfund.config.CustomUserDetails;
import org.project.neighfund.domain.member.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    //작성
    @PostMapping("/write")
    public ResponseEntity<String> createPost(@RequestBody CommunityDto communityDto,
                                            @AuthenticationPrincipal CustomUserDetails userDetails){
            Member loginUser = userDetails.getMember();
            communityService.createPost(communityDto, loginUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 등록되었습니다.");
        }

    //수정
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editPost(@PathVariable Long id,
                                            @RequestBody CommunityDto communityDto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails){
        Member loginUser = userDetails.getMember();
        communityService.editPost(id, communityDto, loginUser);
        return ResponseEntity.ok("게시물이 수정되었습니다.");
    }

    //삭제




}
