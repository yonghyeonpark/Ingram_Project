package com.yonghyeon.ingram.web.dto.user;

import com.yonghyeon.ingram.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserProfileDto {

    private boolean PageOwnerState;
    private User user;
    private int imageCount;

    private boolean followState;
    private Long followingCount;
    private Long followerCount;

    private boolean userState;

    @Builder
    public UserProfileDto(boolean pageOwnerState, User user, int imageCount, boolean followState, Long followingCount, Long followerCount, boolean userState) {
        PageOwnerState = pageOwnerState;
        this.user = user;
        this.imageCount = imageCount;
        this.followState = followState;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
        this.userState = userState;
    }
}
