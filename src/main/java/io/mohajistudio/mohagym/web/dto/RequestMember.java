package io.mohajistudio.mohagym.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

public class RequestMember {

    /*
        내부 클래스를 사용하면 관련된 클래스와 타입을 논리적으로 구조화할 수 있습니다.
        RequestMember 클래스는 Member 클래스를 포함하는 부모 클래스로, Member 클래스는 RequestMember의 일부로 간주됩니다.
        이러한 구조화는 코드의 가독성을 향상시키며 관련된 클래스들을 함께 그룹화하는 데 도움이 됩니다.*/

    @Builder
    @Data
    public static class Member {
        @NonNull
        private String userId;
        @NonNull
        private String password;
    }



}
