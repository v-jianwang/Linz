package jiang.linz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LinzOutState {
	
	private Method stateMethod;
	
	private String stateValue;

	public LinzOutState(Method method) {
		this.stateMethod = method;
	}

	private String invokeState(Object obj) {
		String retVal = "";
		try {
			retVal = stateMethod.invoke(obj).toString();
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException: " + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println("InvocationTargetException: " + e.getMessage());
		}
		return retVal;
	}
	
	
	public void setState(Object obj) {
		stateValue = invokeState(obj);
	}
	
	
	public String outHtml() {
		StringBuilder outBuilder = new StringBuilder();
		String lineEnd = "\r\n";
		outBuilder.append("<li>" + lineEnd);
		outBuilder.append(stateMethod.getName().replace("get",  "") + ": ");
		outBuilder.append(stateValue + lineEnd);
		outBuilder.append("</li>" + lineEnd);
		
		return outBuilder.toString();		
		
	}
}
