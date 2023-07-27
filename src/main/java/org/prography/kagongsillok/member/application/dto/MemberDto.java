package org.prography.kagongsillok.member.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {

    private Long id;
    private String nickname;
    private String email;
    private String role;
    private String profileImage;
    private int loginCount;
    private int totalStudyTime;

    @Builder
    public MemberDto(final Long id, final String nickname, final String email, final String role,
            final String profileImage, final int loginCount, final int totalStudyTime) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.profileImage = profileImage;
        this.loginCount = loginCount;
        this.totalStudyTime = totalStudyTime;
    }

    public static MemberDto from(final Member member, final int totalStudyTime) {
        return MemberDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(member.getRole().name())
                .profileImage(member.getProfileImage())
                .loginCount(member.getLoginCount())
                .totalStudyTime(totalStudyTime)
                .build();
    }
}
