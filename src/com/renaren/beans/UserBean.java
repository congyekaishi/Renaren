package com.renaren.beans;


public class UserBean {

	public int type;
	public UserInfo msg;

	public static class UserInfo {

		public String username;
		public String realname;
		public String company;// 所在公司
		public String sign;
		public String email;
		public String gender;// 性别
		public String address;// 联系地址
		public String birth;
		public String avatar;
		public String position1;// 当前职位
		public String workExperience1;// 工作年限
		public String idcard;//身份证号
		public String mobilephone;

	}
}