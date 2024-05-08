package com.synrgy.binarfud.Binarfud;

import com.synrgy.binarfud.Binarfud.controller.MainController;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.ModelMap;

@SpringBootApplication
public class BinarfudChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinarfudChallengeApplication.class);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
