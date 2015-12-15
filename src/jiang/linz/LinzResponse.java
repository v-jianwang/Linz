package jiang.linz;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;


public class LinzResponse implements Runnable {
	
	private byte[] content;
	
	private OutputStream output;
	
	private LinzRequest request;

	private LinzOutMetaData meta;
	
	public LinzResponse(OutputStream output, LinzRequest request, LinzOutMetaData meta) {
		this.output = output;
		this.request = request;
		this.meta = meta;
	}
	

	@Override
	public void run() {
		buildContent();
		
		DataOutputStream stream = new DataOutputStream(output);
		try {
			stream.write(this.content);
			stream.close();
		} catch (IOException e) {
			System.out.println("error: " + e.getMessage());
		}
	}
	

	private void buildContent() {
		StringBuilder builder = new StringBuilder();
		String lineEnd = "\r\n";
		String space = " ";
		
		builder.append("HTTP/1.1" + space + "200" + space + "OK" + lineEnd);
		builder.append("Date: " + new Date() + lineEnd);
		builder.append("Content-Type: text/html" + lineEnd);
		builder.append("Content-Length: {content-length}" + lineEnd);

		builder.append(lineEnd);
		
		builder.append("<!DOCTYPE html>" + lineEnd);
		builder.append("<html>" + lineEnd);
		builder.append("<head>" + lineEnd);
		builder.append("<meta charset=\"UTF-8\">" + lineEnd);
		builder.append("<title>Web Commander</title>" + lineEnd);
		builder.append("</head>" + lineEnd);
		builder.append("<body>" + lineEnd);
		
		// render output states of client app
		builder.append("<h1>Linz received command</h1>" + lineEnd);
		builder.append("<p>The command you sent to Linz is " + request.getAction() + "</p>" + lineEnd);
		builder.append("<ul>" + lineEnd);
		for (LinzOutState state : meta.getAllStates()) {
			builder.append(state.outHtml());
		}
		builder.append("</ul>" + lineEnd);
		
		// render web actions to client app
		builder.append("<p>You can do the following commands to your application:</p>" + lineEnd);
		builder.append("<ul>" + lineEnd);
		for (LinzOutAction action : meta.getAllActions()) {
			builder.append(action.outHtml());
		}
		builder.append("</ul>" + lineEnd);
		
		builder.append("</body>" + lineEnd);
		builder.append("</html>" + lineEnd);
		
		try {
			this.content = builder.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("error: " + e.getMessage());
		}
	}
}
