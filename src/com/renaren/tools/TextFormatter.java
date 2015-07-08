package com.renaren.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TextFormatter {
	public static String getDateTime(long duration) {
		return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE).format(new Date(
				duration));
	}
}

/**
 * SimpleDateFormat ��һ���Թ�����еķ�ʽ��ʽ���ͷ�����ݵľ����ࡣ 
 * �������ʽ�� (date -> text)���﷨���� (text -> date)�ͱ�׼��
 * */
