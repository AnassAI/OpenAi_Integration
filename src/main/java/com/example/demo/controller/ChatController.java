package com.example.demo.controller;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;

@RestController
public class ChatController {

    @Autowired
    MyOpenAIService aiService;

    @GetMapping("/abc")
    public String indexPage() {
        return "index";
    }


    @GetMapping("/chat")
    @ResponseBody
    public String callingAI(@RequestParam("userInput") String userInput) {
        try {
            String responseFromAI = aiService.openAIServiceCall(userInput);
            return responseFromAI;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                return "Too many requests. Please check your OpenAI plan and billing details.";
            } else {
                return "An error occurred: " + e.getMessage();
            }
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }



    private void saveToFile(String code, String type, String userInput) {
        try {
            String fileName = userInput + "." + type.toLowerCase();
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(code);
            fileWriter.close();
            System.out.println("Code saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
