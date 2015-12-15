package jiang.linz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LinzRequest {
	private String currentAction;
	private Map<String, String> parameters = new HashMap<String, String>();
	
	public LinzRequest(InputStream inputStream) {
		parse(inputStream);
	}
	
	public String getAction() {
		return this.currentAction;
	}
	
	public boolean hasParameters() {
		return parameters.size() > 0;
	}

	public String[] getParameters() {
		return parameters.values().toArray(new String[parameters.size()]);
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
		Pattern patt = Pattern.compile("[\\?|\\&](?<key>\\S+)=(?<value>\\S+)");
		Matcher matcher = patt.matcher(uri);

		if (matcher.find()) {
			String key = matcher.group("key");
			String value = matcher.group("value");
			parameters.put(key, value);
		}
	}
}
