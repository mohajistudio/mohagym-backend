package io.mohajistudio.mohagym.entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
//클래스 내부에 생성자가 존재할때 기본생성자 자동으로 생성//싱글톤 유지
@NoArgsConstructor

public class Member  {
    @Id
    /*원하면 SEQUENCE 로 변경 가능(창희형)//oracle 데이터베이스 등에서 사용가능하며 id 값이
    증가하는 IDENTITY 와 다르게 조정이 가능하지만 인스턴스가 생성되거나 삭제될때 아이디 값을 관리하는 별도의 로직이 필요함*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name="password")
    private String password;

    @Column(name = "role")
    private String role;

    //SHA256Util 클래스의 설명 참조
    @Column(name = "salt")
    private String salt;


    /*
    1.장기적인 인증 유지: 인증된 사용자나 클라이언트가 로그인 상태를 유지하거나, 액세스 토큰(Access Token)의 만료 시간이 지날 때 다시 로그인하지 않고도 자원에 접근할 수 있게 합니다.

    2.액세스 토큰 갱신: 주로 OAuth 2.0에서 사용되며, 액세스 토큰의 만료 시간이 지나면, 클라이언트는 Refresh token을 사용하여 새로운 액세스 토큰을 발급받을 수 있습니다.
     이렇게 하면 사용자에게 다시 로그인하지 않고도 서비스를 계속 사용할 수 있습니다.

    Refresh token의 작동 방식은 다음과 같습니다:

    1.사용자가 클라이언트 애플리케이션에 로그인하면, 인증 서버는 액세스 토큰과 함께 Refresh token을 발급합니다.

    2.액세스 토큰은 보통 짧은 유효 기간을 갖고 있으며, 이 시간이 지나면 만료됩니다. 그러나 Refresh token은 더 긴 유효 기간을 갖고 있습니다.

    3.액세스 토큰이 만료되면, 클라이언트 애플리케이션은 Refresh token을 사용하여 새로운 액세스 토큰을 인증 서버에 요청합니다.

    4.인증 서버는 Refresh token이 유효하면 새로운 액세스 토큰을 발급하고, 클라이언트에게 반환합니다.

    5.클라이언트 애플리케이션은 이제 새로운 액세스 토큰을 사용하여 자원에 접근할 수 있습니다.

    이러한 방식으로 Refresh token을 사용하면 사용자는 로그인 세션을 유지하고, 액세스 토큰의 만료로 인한 불편함 없이 서비스를 계속 이용할 수 있습니다.
     Refresh token은 보안적으로 중요한 개념이며, 안전하게 관리되어야 합니다.*/
    @Column(name ="refreshToken")
    private String refreshToken;

    //생성자//id는 IDENTITY로 자동생성//refreshToken은 changeRefreshToken 매서드로 받아옴//Builder 패턴으로 사용하는 다른 코드의 가독성 높임
    @Builder
    public Member(String userId, String password, String salt, String role){

        this.userId = userId;
        this.password = password;
        this.salt = salt;
        this.role = role;
    }


}
