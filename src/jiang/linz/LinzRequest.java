package jiang.linz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LinzRequest {
	private String currentAction;
	private List<String> parameters = new ArrayList<String>();
	
	public LinzRequest(InputStream inputStream) {
		parse(inputStream);
	}
	
	public String getAction() {
		return this.currentAction;
	}

	public Object[] getParameters() {
		return parameters.toArray();
	}
	
	
	private void parse(InputStream inputStream) {
		String requestLine;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			requestLine = reader.readLine();
		} catch (IOException e) {
			System.out.println("error when reading input from socket. " + e.getMessage());
			requestLine = "noset";
		}
	
		System.out.println("http request: " + requestLine);
		String uri = requestLine.split(" ")[1];
		
		Pattern pattern = Pattern.compile("/(?<method>\\w+[^\\?])");
		Matcher matcher = pattern.matcher(uri);
		
		if (matcher.find()) {
			this.currentAction = matcher.group("method");
			if (uri.contains("?") && uri.contains("=")) {
				parseParameters(uri);
			}
		}
		else {
			this.currentAction = "NOSET";
		}
	}

	
	private void parseParameters(String uri) { 
		Pattern patt = Pattern.compile("[\\?&](?<key>\\w+)=(?<value>\\w+)");
		Matcher matcher = patt.matcher(uri);

		while (matcher.find()) {
			//String key = matcher.group("key");
			String value = matcher.group("value");
			parameters.add(value);
		}
	}
}
