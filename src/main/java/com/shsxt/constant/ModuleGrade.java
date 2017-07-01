package com.shsxt.constant;

public enum ModuleGrade {
	
	ROOT(0),
	
	FIRST(1),
	
	SECOND(2);
	
	private int grade;
	
	ModuleGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	
}
