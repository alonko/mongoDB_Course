package com.tengen.week2;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class UpdateTest {
	public static void main(String[] args) throws UnknownHostException {
		DBCollection collection = createCollection();

		List<String> names = Arrays.asList("alice", "bobby", "cathy", "david",
				"ethan");
		for (String name : names) {
			collection.insert(new BasicDBObject("_id", name));
		}
		// add new field
		collection.update(new BasicDBObject("_id", "alice"), new BasicDBObject(
				"age", 24));
		// add additional field without removing the previously added field
		collection.update(new BasicDBObject("_id", "alice"), new BasicDBObject(
				"$set", new BasicDBObject("gender", "female")));

		// create a new document
		collection.update(new BasicDBObject("_id", "frank"), new BasicDBObject(
				"$set", new BasicDBObject("gender", "male")), true, false);

		// update all the documents
		collection.update(new BasicDBObject(), new BasicDBObject("$set",
				new BasicDBObject("title", "Dr.")), false, true);

		//remove one document
		collection.remove(new BasicDBObject("_id", "alice"));

		printCollection(collection);
	}

	private static void printCollection(DBCollection collection) {
		DBCursor cursor = collection.find();
		try {
			while (cursor.hasNext()) {
				DBObject cur = cursor.next();
				System.out.println(cur);
			}
		} finally {
			cursor.close();
		}
	}

	private static DBCollection createCollection() throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB("course");
		DBCollection collection = courseDB.getCollection("dotNotationTest");
		collection.drop();
		return collection;
	}
}
