package com.tengen.week2;

import java.util.Arrays;
import java.util.Date;

import com.mongodb.BasicDBObject;

public class DocumentRepresentationTest {
	public static void main(String[] args) {
		BasicDBObject doc = new BasicDBObject();
		doc.put("userName", "John");
		doc.put("birthDate", new Date());
		doc.put("programmer", true );
		doc.put("age", 30);
		doc.put("languages", Arrays.asList("Java", "JS") );
		doc.put("address", new BasicDBObject("street", "Haeshel").append("town", "Ramat-Gan").append("zip", "324") );

	}
}
