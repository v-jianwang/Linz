package jiang.linz;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jiang.linz.annotation.LinzAction;
import jiang.linz.annotation.LinzState;

public class LinzOutMetaData {

	private List<LinzOutAction> outActions = new ArrayList<LinzOutAction>();
	
	private List<LinzOutState> outStates = new ArrayList<LinzOutState>();
	
	public LinzOutMetaData(Class<?> handlerClass) {
		LoadOutActionsAndStates(handlerClass);
	}
	
	private void LoadOutActionsAndStates(Class<?> handlerClass) {
		Method[] methods = handlerClass.getMethods();
		
		for (final Method method : methods) {
			if (method.isAnnotationPresent(LinzAction.class)) {
				LinzOutAction action = new LinzOutAction(method);
				outActions.add(action);
			}
			
			if (method.isAnnotationPresent(LinzState.class)) {
				LinzOutState state = new LinzOutState(method);
				outStates.add(state);
			}
		}
	}
	
	public LinzOutAction getAction(String name) {
		LinzOutAction action = null;
		for (LinzOutAction a : outActions) {
			if (a.getMethod().getName().equalsIgnoreCase(name)) {
				action = a;
			}
		}
		
		return action;
	}

	public List<LinzOutState> getAllStates() {
		return outStates;
	}

	public List<LinzOutAction> getAllActions() {
		return outActions;
	}

}
