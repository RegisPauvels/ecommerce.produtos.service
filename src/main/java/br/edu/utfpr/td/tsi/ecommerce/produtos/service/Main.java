package br.edu.utfpr.td.tsi.ecommerce.produtos.service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({ "file:./application.properties" })
@EnableRabbit
public class Main {
	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/ecommerce.produtos");
		SpringApplication.run(Main.class, args);
	}
}
