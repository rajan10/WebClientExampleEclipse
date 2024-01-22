package com.rajan.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.rajan.binding.Book;

@Service
public class BookClient {
	final String apiurl="https://fakerestapi.azurewebsites.net/api/v1/Books";
	
	//single book id retreival
	public void invokeBookTicket(int id)  {
		
		WebClient webClient = WebClient.create();//WebClient is the interface (interface
		//is like a blueprint that defines a set of methods without providing their implementation) of Spring WebFlux	
		//WebClient.create() method returns an instance of a class that implements the 'WebClient' interface
		String response = webClient.get()//Get the response
		.uri(apiurl+"/{id}",id) //Define the URI/endpoint with the id parameter
		.retrieve()//Get the response
		.bodyToMono(String.class)//bind the response body of a WebClient call into String as a Mono
		.block();//block the thread and wait for the result or make it syn client
		
		System.out.println(response);
	}	
	//multiple book id retreival
	public void invokeBookTickets()  {
		
		WebClient webClient = WebClient.create();//WebClient is the interface of Spring WebFlux	
		Book[] responseData = webClient.get()//Get the response
		.uri(apiurl) //Define the URI/endpoint with the id parameter
		.retrieve()//Get the response
		.bodyToMono(Book[].class)//bind that response to Book array
		.block();//block the thread and wait for the result or make it syn client
		
		//System.out.println(responseData);
		
		for (Book b : responseData) {
			System.out.println(b);
		}
	}	
	
	public void invokeGetBooksAsync() {
		WebClient webClient = WebClient.create();//WebClient is the interface of Spring WebFlux
		webClient.get()
		.uri(apiurl)
		.retrieve()
		.bodyToMono(Book[].class)
		.subscribe(BookClient::processResponse); // non blocking thread to process the response
		//processResponse is a handler function; u dont wait for the response, you continue. It will print 
		//the SOPLN below. And when the response is received from the server
		// it will invoke the processResponse function
		//Aysnc communication using subscribe
		//here the processResponse is a callback function which will be invoked asynchronously
		//when the response is received from the server
		//or when an exception is thrown
		//or when the response is not received from the server
		//processResponse a callback function is like a chef who is preparing the food and is subscribe to the customer orders
	
		System.out.println("Request Sent");
	}
	
	public static void processResponse(Book[] books) {//callback function
		for (Book b : books) {
			System.out.println(b);
		}
		System.out.println("Response Processed");
	}
	//post request book creation
	public void invokeSaveBook() {
		
		Book book = new Book();
		book.setId(1000);
		book.setTitle("Rajan WebClient WebClient rocks");
		book.setDescription("Autobiographical booksss");
		book.setPageCount(20202);
		book.setExcerpt("This is about the excerpt here of the book Rajan WebClient WebClient rocks");
		book.setPublishDate("2024-01-21T18:51:10.998Z");
		
		
		WebClient postBody = WebClient.create();//WebClient is the interface of Spring WebFlux
		postBody.post()//Post the request
		.uri(apiurl)//Define the URI
		.body(BodyInserters.fromValue(book))//Attach the requst body to the POST request. Here book is the payload
		.retrieve()//Get the response
		.bodyToMono(String.class)//bind that response to String
		.block();//block the thread and wait for the result or make it as sync client
		
	}
	public void updateBook(int id, Book updateBook) {
		
		WebClient webClient = WebClient.create();//WebClient is the interface of Spring WebFlux
		webClient.put()//Post the request
		.uri(apiurl+"/{id}",id)//Define the URI
		.body(BodyInserters.fromValue(updateBook))//Attach the requst body to the POST request. Here book is the payload
		.retrieve()//Get the response
		.bodyToMono(String.class)//bind that response to String
		.block();//block the thread and wait for the result or make it as sync client
		
		System.out.println("Book with id "+id+" updated");
		
	}
	public void deleteBook(int id) {
		WebClient webClient = WebClient.create();//WebClient is the interface of Spring WebFlux
		webClient.delete()
		.uri(apiurl+"/{id}",id)//Define the URI
		.retrieve()//Get the response
		.bodyToMono(Void.class)//bind that response to String
		.block();//block the thread and wait for the result or make it as sync client
	System.out.println("Book with id "+id+" deleted");
	}
	
	public static void main(String[] args) {
		BookClient bookClient = new BookClient();
		Book updatedBook = new Book();
		updatedBook.setId(1000);
		updatedBook.setDescription("Updated Description");
	    updatedBook.setPageCount(200);
	    updatedBook.setExcerpt("Updated Excerpt");
	    updatedBook.setPublishDate("2023-01-01");

	    bookClient.updateBook(1, updatedBook);
		
		
	}
}

