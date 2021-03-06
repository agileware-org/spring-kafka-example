package org.agileware.codename;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class IntegrationApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(IntegrationApplication.class).web(WebApplicationType.NONE).run(args);
	}
}