package com.samsung.bookmanagerment;

import com.samsung.bookmanagerment.configuration.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(FileStorageProperties.class)
@SpringBootApplication
public class BookmanagermentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmanagermentApplication.class, args);
	}

}
