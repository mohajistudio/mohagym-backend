package io.mohajistudio.mohagym.util;
    /*
    1.Salt 생성: 새로운 사용자가 비밀번호를 설정할 때, 무작위의 Salt를 생성합니다. 이 Salt는 사용자마다 다르게 생성됩니다.

    2.Salt와 비밀번호 결합: 사용자의 비밀번호와 생성된 Salt를 결합합니다.

    3.해시 생성: 결합된 Salt와 비밀번호를 해시 함수(예: SHA-256)에 적용하여 최종 해시된 비밀번호를 생성합니다.

    4.Salt와 해시 저장: 데이터베이스에는 사용자의 Salt와 해시된 비밀번호만 저장됩니다. Salt는 일반적으로 해시된 비밀번호 앞이나 뒤에 저장됩니다.

    로그인 시, 시스템은 사용자가 제공한 비밀번호에 저장된 Salt를 결합하고 동일한 해시 함수를 사용하여 비밀번호를 해시합니다.
    그런 다음 데이터베이스에서 저장된 Salt와 함께 해시된 비밀번호와 비교합니다.
    만약 해시된 값이 일치하면 인증이 성공하고 사용자는 로그인할 수 있습니다.

    Salt를 사용함으로써 레인보우 테이블과 같은 해킹 기법을 어렵게 만들며, 비밀번호 보안을 향상시킵니다.
    Salt는 비밀번호 저장과 관련된 보안 규칙 및 권장 사항의 중요한 부분입니다*/

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SHA256Util {
    //비밀번호와 salt를 조합해 암호화 시킴
    public static String getEncrypt(String password, String salt) {

        return getEncrypt(password, salt.getBytes());
    }

    public static String getEncrypt(String password, byte[] salt) {

        String result = "";

        byte[] a = password.getBytes(); //비밀번호를 바이트 배열로 바꾸기//문자열 비밀번호를 바이트 배열로 변환하는 이유는 해싱을 수행하기 위해 데이터를 바이트 단위로 처리해야 하기 위함
        byte[] bytes = new byte[a.length + salt.length];//비밀번호 배열과 salt 배열의 크기를 합한 만큼의 배열 생성

        //비밀번호와 salt 값을 bytes 배열에 복사
        System.arraycopy(a, 0, bytes, 0, a.length);
        System.arraycopy(salt, 0, bytes, a.length, salt.length);

        try {
            // 암호화 방식 지정 메소드
            MessageDigest md = MessageDigest.getInstance("SHA-256");//SHA-256 해시 함수를 사용하여 메시지 다이제스트(Message Digest) 객체를 생성
            md.update(bytes);//bytes를 사용하여 메시지 다이제스트를 업데이트//비밀번호와 salt 값을 합친 데이터를 해싱하는 작업

            byte[] byteData = md.digest();

            StringBuffer sb = new StringBuffer(); //StringBuffer는 문자열을 동적으로 빌드하고 수정하는 데 사용
            for (int i = 0; i < byteData.length; i++) { //byteData 배열의 각 바이트를 반복하면서 다음 작업을 수행
                sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16)//바이트 값을 16진수 문자열로 변환하고, 변환된 문자열을 sb에 추가합니다. 이 코드를 각 바이트에 대해 반복하면 해시된 데이터의 각 바이트가 16진수 문자열로 변환되어 sb에 연결
                        .substring(1)); //문자열의 첫 번째 문자를 제거합니다. 이 작업은 두 자리 16진수 문자열로 만들기 위해 수행됩니다. 예를 들어, 16진수 "0A" 대신 "A"를 얻습니다.
            }

            result = sb.toString(); //문자열로 변환
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    //난수 salt 생성
    public static String generateSalt() {
        Random random = new Random(); //난수객체 생성

        byte[] salt = new byte[8];  //8바이트 크기의 salt 배열
        random.nextBytes(salt); //배열에 무작위 바이트 값을 채움

        StringBuffer sb = new StringBuffer(); //문자열을 구성하는 StringBuffer 객체  생성
        for (int i = 0; i < salt.length; i++) {
            sb.append(String.format("%02x",salt[i])); // byte 값을 Hex 값(16진수 문자열)으로 바꾸고 StringBuffer에 추가
        }

        return sb.toString(); //생성된 16진수 문자열을 반환
    }
}
