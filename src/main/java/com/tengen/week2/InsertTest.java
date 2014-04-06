package com.tengen.week2;
import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class InsertTest {
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient(); 
		DB courseDB = client.getDB("course");
		DBCollection collection = courseDB.getCollection("insertTest");
		collection.drop();
		DBObject doc = new BasicDBObject("x", "1");
		DBObject doc2 = new BasicDBObject("x", "2");
		System.out.println(doc);
		collection.insert(Arrays.asList(doc,doc2));
		System.out.println(doc);

	}
}
