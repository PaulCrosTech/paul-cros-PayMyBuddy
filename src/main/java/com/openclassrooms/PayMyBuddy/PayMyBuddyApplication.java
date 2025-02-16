package com.openclassrooms.PayMyBuddy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayMyBuddyApplication {

	private static final Logger log = LoggerFactory.getLogger(PayMyBuddyApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
		log.info("==> Application PayMyBuddy started <==");
	}

}
