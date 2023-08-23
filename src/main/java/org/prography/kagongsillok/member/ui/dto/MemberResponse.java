package org.prography.kagongsillok.member.ui.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.member.application.dto.MemberDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

    private Long id;
    private String nickname;
    private String email;
    private String role;
    private String profileImage;
    private int loginCount;

    @Builder
    public MemberResponse(final Long id, final String nickname, final String email, final String role,
            final String profileImage, final int loginCount) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.profileImage = profileImage;
        this.loginCount = loginCount;
    }

    public static MemberResponse from(final MemberDto memberDto) {
        return MemberResponse.builder()
                .id(memberDto.getId())
                .nickname(memberDto.getNickname())
                .email(memberDto.getEmail())
                .role(memberDto.getRole())
                .profileImage(memberDto.getProfileImage())
                .loginCount(memberDto.getLoginCount())
                .build();
    }
}
