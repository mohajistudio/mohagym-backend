package io.mohajistudio.mohagym.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class requestProfile {
    protected Long Id;

    @Getter
    public static class name extends requestProfile{
        public String name;
    }
    @Getter
    public static class phoneNo extends requestProfile{
        public String phoneNo;
    }
}
