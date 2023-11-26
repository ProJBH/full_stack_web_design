package com.bohuajia.o2o.enums;

public enum HeadLineStateEnum {
	SUCCESS(0, "Created successfully"), INNER_ERROR(-1001, "Operation failed"), EMPTY(-1002, "HeadLine information is null");

	private int state;

	private String stateInfo;

	private HeadLineStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static HeadLineStateEnum stateOf(int index) {
		for (HeadLineStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
