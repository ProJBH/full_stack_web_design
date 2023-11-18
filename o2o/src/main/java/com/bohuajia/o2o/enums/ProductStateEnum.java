package com.bohuajia.o2o.enums;

public enum ProductStateEnum {
	OFFLINE(-1, "Illegal product"), DOWN(0, "Off line"), SUCCESS(1, "Success"), INNER_ERROR(-1001, "fail"), EMPTY(-1002, "empty product");

	private int state;

	private String stateInfo;

	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static ProductStateEnum stateOf(int index) {
		for (ProductStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
