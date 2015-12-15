package jiang.linz.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class LinzMethodCallHelper {

	
	public static void CallMethod(Object obj, Method method, Object[] args) {
		try {		
			if (method.getParameterCount() > 0) {
				if (args.length != method.getParameterCount()) {
					throw new IllegalArgumentException(
							"The number of parameters for method " + method.getName() + 
							" is " + args.length + " (expected " + method.getParameterCount() + ")");
				}

				Object[] newArgs = new Object[args.length];
				Parameter[] parameters = method.getParameters();
				for (int i = 0; i < parameters.length; i++) {
					Class<?> type = parameters[i].getType();
					if (type.equals(int.class) || type.equals(Integer.class)) {
						newArgs[i] = Integer.parseInt(args[i].toString());
					}
					else if (type.equals(long.class) || type.equals(Long.class)) {
						newArgs[i] = Long.parseLong(args[i].toString());
					}
					else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
						newArgs[i] = Boolean.parseBoolean(args[i].toString());
					}
					else {
						newArgs[i] = args[i];
					}
				}
				
				method.invoke(obj, newArgs);
			}
			else {
				method.invoke(obj);
			}
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException: " + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println("InvocationTargetException: " + e.getMessage());
		}
	}
}
