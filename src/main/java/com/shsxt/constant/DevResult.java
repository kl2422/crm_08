package com.shsxt.constant;

public enum DevResult {
	
	UN_DEV(0),
	
	DEVING(1),
	
	DEV_FINISHED(2),
	
	DEV_FAIL(3);
	
	private int devResult;
	
	DevResult(int devResult) {
		this.devResult = devResult;
	}

	public int getDevResult() {
		return devResult;
	}

	public void setDevResult(int devResult) {
		this.devResult = devResult;
	}
	
	public static DevResult findByDevResult(int devResult) {
		DevResult[] devResults = DevResult.values();
		for (DevResult devResultObj : devResults) {
			if (devResultObj.getDevResult() == devResult) {
				return devResultObj;
			}
		}
		return null;
	}
	
}
