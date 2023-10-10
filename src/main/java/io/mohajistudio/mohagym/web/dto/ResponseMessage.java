package io.mohajistudio.mohagym.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
public class ResponseMessage {
    @Builder.Default //기본값을 가지는 필드를 초기하하고 빌터 패턴을 사용할 수 있도록 만들어진 클래스//객체 생성 시 해당 필드값이 지정되지 않은 경우, 기본값 적용
    private String id = UUID.randomUUID().toString(); //무작위 UUID 문자열을 생성한 다음 id 필드에 할당
    @Builder.Default
    private Date dateTime = new Date(); //현재 날짜와 시간을 나타내는 Date 객체로 초기화
    private String message;
    private Object data;
}
