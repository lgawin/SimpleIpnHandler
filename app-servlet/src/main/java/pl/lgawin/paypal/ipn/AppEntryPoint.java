package pl.lgawin.paypal.ipn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class AppEntryPoint extends SpringBootServletInitializer {

    public static final Class<AppEntryPoint> RUNNER_CLASS = AppEntryPoint.class;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RUNNER_CLASS);
    }

    public static void main(String[] args) {
        SpringApplication.run(RUNNER_CLASS, args);
    }
}
