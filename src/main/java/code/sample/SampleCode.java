package code.sample;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SampleCode {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("test"));
    }
}
