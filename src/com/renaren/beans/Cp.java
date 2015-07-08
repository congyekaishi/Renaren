package com.renaren.beans;

import java.util.Arrays;
import java.util.List;

public class Cp {
	public int type;
	public List<Question> question;
	public String[] answer;
	public String cptype;
	public String spendtime;
	@Override
	public String toString() {
		return "Cp [answer=" + Arrays.toString(answer) + "]";
	}
	
	
}
