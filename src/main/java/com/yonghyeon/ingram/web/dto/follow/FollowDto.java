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

}
