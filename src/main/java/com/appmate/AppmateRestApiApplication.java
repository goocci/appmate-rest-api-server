package com.appmate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@MapperScan("com.appmate.mapper")
@SpringBootApplication
public class AppmateRestApiApplication extends WebMvcConfigurerAdapter {

//	@Bean
//	UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
//		UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
//		factory.addBuilderCustomizers(
//				builder -> builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
//		return factory;
//	}

	public static void main(String[] args) {
		SpringApplication.run(AppmateRestApiApplication.class, args);
	}

}

