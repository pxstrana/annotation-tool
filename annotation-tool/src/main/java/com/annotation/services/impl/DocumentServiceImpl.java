package com.annotation.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.annotation.services.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{

		
		public String readFileAsString(String uri) throws IOException {
			String data = "";
			data = new String(Files.readAllBytes(Paths.get(uri)));
			
			return data;
		}
}
