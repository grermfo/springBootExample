package com.grermfo.springBootExample;

        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        String [] arr = new String [2];
        arr[1] ="1";
        arr[0] ="0";

        SpringApplication.run(Application.class, arr);
    }
}
