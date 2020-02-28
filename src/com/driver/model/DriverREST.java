package com.driver.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.avps.model.Driver;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

// This class handles all the REST calls
public class DriverREST {

	// REST Service method
	public Driver restServiceMethod(Driver driver) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(
				"http://localhost:9090/DecisionService/rest/v1/LicenseEligibilityRulesApp/DriverDeployment");
		JSONObject driverjson = new JSONObject(driver);

		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("Driver", driverjson);

		System.out.println("request===" + jsonRequest);

		StringEntity input = new StringEntity(jsonRequest.toString());
		input.setContentType("application/json");
		post.setEntity(input);
		HttpResponse response = httpclient.execute(post);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		String jsonOutput = sb.toString();
		System.out.println("output===" + jsonOutput);
		JSONObject jsonObject = new JSONObject(jsonOutput);
		Gson gson = new Gson();
		driver = gson.fromJson(jsonObject.getJSONObject("Driver").toString(), Driver.class);
		System.out.println("completed");
		return driver;
	}

}