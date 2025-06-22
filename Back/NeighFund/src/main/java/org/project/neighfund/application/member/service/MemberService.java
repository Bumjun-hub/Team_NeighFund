package org.project.neighfund.application.member.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.neighfund.application.member.dto.LoginTypeResponse;
import org.project.neighfund.application.member.dto.RoleInfoResponse;
import org.project.neighfund.application.member.dto.SignupRequest;
import org.project.neighfund.application.member.dto.SignupResponse;
import org.project.neighfund.domain.Role.Role;
import org.project.neighfund.domain.Role.RoleRepository;
import org.project.neighfund.domain.member.Member;
import org.project.neighfund.domain.member.MemberRepository;
import org.project.neighfund.enums.RoleName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public SignupResponse insert(@Valid SignupRequest signupRequest) {
        if (memberRepository.existsByEmail(signupRequest.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        if (memberRepository.existsByUsername(signupRequest.getUsername())){
            throw new IllegalArgumentException("이미 존재하는 유저네임 입니다.");
        }

        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("입력하신 비밀번호가 서로 일치하지 않습니다.");
        }

        String enPass = passwordEncoder.encode(signupRequest.getPassword());

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("기본 USER 역할이 DB에 없습니다."));

        Member member = Member.builder()
                .email(signupRequest.getEmail())
                .password(enPass)
                .username(signupRequest.getUsername())
                .address(signupRequest.getAddress())
                .dongName(signupRequest.getDongName())
                .phone(signupRequest.getPhone())
                .role(userRole)
                .build();

        Member savedMember = memberRepository.save(member);

        return SignupResponse.builder()
                .email(savedMember.getEmail())
                .build();
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    public RoleInfoResponse getRoleInfo(Member m) {
        Optional<Member> opUser =  memberRepository.findById(m.getId());
        if (opUser.isPresent()) {
            Member member = opUser.get();
            String role = member.getRole().getName().name();
            RoleInfoResponse response = new RoleInfoResponse();
            response.setRoleName(role);
            return response;
        } else {
            throw new UsernameNotFoundException("해당 회원을 찾을 수 없습니다.");
        }
    }

    public LoginTypeResponse getTypeInfo(Member m) {
        if (m == null) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        String type = (m.getSocialProvider() == null) ? "LOCAL" : m.getSocialProvider().name();
        return new LoginTypeResponse(type);
    }

}
