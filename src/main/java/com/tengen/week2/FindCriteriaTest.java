package com.tengen.week2;

import java.net.UnknownHostException;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

public class FindCriteriaTest {
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB("course");
		DBCollection collection = courseDB.getCollection("insertTest");
		collection.drop();

		for (int i = 0; i < 10; i++) {
			collection.insert(new BasicDBObject("x", new Random().nextInt(2))
					.append("y", new Random().nextInt(100)));
		}

		QueryBuilder builder = new QueryBuilder().start("x").is(0).and("y")
				.greaterThan(10).lessThan(70);

		DBObject query = new BasicDBObject("x", 0).append("y",
				new BasicDBObject("$gt", 10).append("$lt", 90));

		System.out.println("Count");
		// long count = collection.count(query);
		long count = collection.count(builder.get());
		System.out.println(count);

		System.out.println("Find All");
		// DBCursor cursor = collection.find(query);
		DBCursor cursor = collection.find(builder.get());
		try {
			while (cursor.hasNext()) {
				DBObject cur = cursor.next();
				System.out.println(cur);
			}
		} finally {
			cursor.close();
		}
	}
}
