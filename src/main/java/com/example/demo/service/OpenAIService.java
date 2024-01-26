package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

	private String apiKey = "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";
	private String modelId= "gpt-3.5-turbo";
	private String url="https://platform.openai.com/api-keys";


	private final RestTemplate restTemplate;
	@Autowired
	public OpenAIService(RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}



	public String openAIServiceCall(String userInput)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + apiKey);

		String requestBody = " {\"model\": \"" + modelId
				+"\" ,\"messages\" : [{\"role\": \"user\", \"content\" : \""
				+userInput + "\"}]}";


		HttpEntity<String> request =new HttpEntity<>(requestBody,headers);
		String responseFromAI = restTemplate.postForObject(url,request,String.class);




		return responseFromAI;
	}


}