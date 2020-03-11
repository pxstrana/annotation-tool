package com.annotation.services;

import java.io.IOException;

public interface DocumentService {

	String readFileAsString(String uri) throws IOException;
	
}
