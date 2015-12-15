package jiang.linz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class LinzOutAction {

	private Method actionMethod;

	public LinzOutAction(Method method) {
		this.actionMethod = method;
	}

	
	public Method getMethod() {
		return actionMethod;
	}
	
	
	public void doAction(Object obj, Object...args) {
		try {
			actionMethod.invoke(obj, args);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException when invoke method:" + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException when invoke method:" + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println("InvocationTargetException when invoke method:" + e.getMessage());
		}
	}
	
	
	public String outHtml() {
		StringBuilder outBuilder = new StringBuilder();
		String lineEnd = "\r\n";
		outBuilder.append("<li>" + lineEnd);
		outBuilder.append("  <form method=\"get\" action=\"" + actionMethod.getName() + "\">" + lineEnd);
		outBuilder.append("    <input type=\"submit\" value=\"    "+ actionMethod.getName() +"    \"/>" + lineEnd);
		
		// what if a web action has parameters
		if (actionMethod.getParameterCount() > 0) {
			Parameter[] parameters = actionMethod.getParameters();
			String space = "&nbsp;";
			for (Parameter p : parameters) {
				Class<?> t = p.getType();
				String type = "text";
				if (t.equals(int.class) || t.equals(Integer.class))
					type = "number";
				outBuilder.append(space + p.getName() + "=<input type=\""+ type +"\" name=\""+ p.getName() +"\" />");
			}
		}
		
		outBuilder.append("  </form>" + lineEnd);
		outBuilder.append("</li>" + lineEnd);
		outBuilder.append("<br/>" + lineEnd);
		
		return outBuilder.toString();
	}

	
	
	
}
