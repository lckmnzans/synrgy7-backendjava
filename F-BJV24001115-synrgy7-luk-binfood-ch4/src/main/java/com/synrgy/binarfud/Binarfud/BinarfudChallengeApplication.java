package com.synrgy.binarfud.Binarfud;

import com.synrgy.binarfud.Binarfud.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BinarfudChallengeApplication {

	public static void main(String[] args) {
		MainController mainController = SpringApplication.run(BinarfudChallengeApplication.class, args).getBean(MainController.class);
		mainController.init();
	}

}
