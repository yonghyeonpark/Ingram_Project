package com.yonghyeon.ingram.web.dto.follow;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FollowDto {

    private Long userId;
    private String username;
    private String profileImageUrl;
    private Long followState;
    private Long mirrorState;

    @Builder
    public FollowDto(Long userId, String username, String profileImageUrl, Long followState, Long mirrorState) {
        this.userId = userId;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.followState = followState;
        this.mirrorState = mirrorState;
    }
}
