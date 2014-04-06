package com.tengen.week3;

import java.net.UnknownHostException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class HW_3_1 {
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB("school");
		DBCollection collection = courseDB.getCollection("students");
		System.out.println("before count: " + collection.count());

		DBCursor cursor = collection.find();
		try {
			while (cursor.hasNext()) {
				DBObject cur = cursor.next();
				BasicDBList scores = (BasicDBList) cur.get("scores");

				BasicDBObject[] scoresArr = scores
						.toArray(new BasicDBObject[0]);
				Double minScore = 100d;
				BasicDBObject removeObj = null;
				for (BasicDBObject curScore : scoresArr) {
					if (curScore.get("type").equals("homework")) {
						Double sco = (Double) curScore.get("score");
						if (sco < minScore) {
							minScore = sco;
							removeObj = curScore;
						}
						System.out.println(sco);
					}
				}
				BasicDBObject[] resultArr = new BasicDBObject[scoresArr.length - 1];
				int index = 0;
				for (BasicDBObject curScore : scoresArr) {
					if (!curScore.equals(removeObj)) {
						resultArr[index++] = curScore;
					}
				}

				cur.put("scores", resultArr);
				BasicDBObject updateQuery = new BasicDBObject();
				updateQuery.put("_id", cur.get("_id"));

				BasicDBObject updateCommand = new BasicDBObject();
				updateCommand.put("$set",
						new BasicDBObject("scores", resultArr));
				collection.update(updateQuery, updateCommand, true, true);

				// collection.update(q, o)
				// System.out.println("cur: " + cur);
			}
			System.out.println("after count: " + collection.count());
		} finally {
			cursor.close();
		}
	}
}
