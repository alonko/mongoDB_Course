package com.tengen.week1;

import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class SparkFormHandling {
	public static void main(String[] args) throws UnknownHostException {
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(
				HelloWorldSparkFreeMarkerStyle.class, "/");

		Spark.get(new Route("/") {
			@Override
			public Object handle(final Request request, Response response) {
				try {
					Map<String, Object> fruitsMap = new HashMap<String, Object>();
					fruitsMap.put("fruits",
							Arrays.asList("apple", "orange", "banana", "peach"));

					Template fruitPickerTemplate = configuration
							.getTemplate("fruitPicker.ftl");
					StringWriter writer = new StringWriter();
					fruitPickerTemplate.process(fruitsMap, writer);
					return writer;
				} catch (Exception e) {
					halt(500);
					e.printStackTrace();
					return null;
				}
			}
		});
		
		Spark.post(new Route("/favorite_fruit") {
			@Override
			public Object handle(final Request request, Response response) {
				final String fruit = request.queryParams("fruit");
				if(fruit == null){
					return "pick one";
				}else{
					return "fruit is : " + fruit;
				}
					
			}
		});
	}
}
