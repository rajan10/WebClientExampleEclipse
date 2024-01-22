package com.rajan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.rajan.client.BookClient;

@SpringBootApplication
public class WebClientExampleApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(WebClientExampleApplication.class, args);
		BookClient bean=context.getBean(BookClient.class);
		//bean.invokeBookTicket(1000);
//		bean.invokeBookTickets();
//		bean.invokeSaveBook();
//		bean.updateBook(1000, null);
//		bean.deleteBook(1000);
		bean.invokeGetBooksAsync();
	}

}
