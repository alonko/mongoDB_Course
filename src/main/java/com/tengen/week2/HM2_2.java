package com.tengen.week2;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class HM2_2 {
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB("students");
		DBCollection collection = courseDB.getCollection("grades");
		System.out.println("before count: " + collection.count());
		DBCursor cursor = collection.find(new BasicDBObject("type", "homework"));
		cursor.sort(new BasicDBObject("student_id", 1).append("score", 1));
		System.out.println("count: " + cursor.count());
		
		//cursor.limit(10);
		int lastStudId = -1;
		try{
			while(cursor.hasNext()){
				DBObject cur = cursor.next();
				int currentStudId = (Integer)cur.get("student_id");
				if(currentStudId != lastStudId){
					collection.remove(cur);
					System.out.println(cur);
				}
				lastStudId = currentStudId;
				
			}
			System.out.println("after count: " + collection.count());
		}finally{
			cursor.close();
		}
	}
	
}
