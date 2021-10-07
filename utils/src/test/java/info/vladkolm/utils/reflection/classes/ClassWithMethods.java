package info.vladkolm.utils.reflection.classes;

import java.util.Arrays;

public class ClassWithMethods
{
	public static String msg;
	public String message;

	public void method(String text, Object obj) {
		msg = text+obj;
	}

	public int method(String text) {
		msg = "The text: "+ text;
		return 1;
	}

	public int methodWithPrimitiveParam(String text, int number) {
		msg = "The text: "+ text+number;
		return 1;
	}
	
	public int methodWithArrayParam(String text, int [] array) {
		msg = "The text: "+ Arrays.toString(array);
		return 1;
	}
	
	public int methodWithArrayOfArraysParam(String text, int [][] array) {
		StringBuilder sb = new StringBuilder();
		for(int [] x: array) {
			if(sb.length() == 0) {
				sb.append("Array of arrays: [");
			} else {
				sb.append(",");
			}
			sb.append(Arrays.toString(x));
		}
		sb.append("]");
		msg = sb.toString(); 
		return 1;
	}
	
	public static void staticMethod(String text) {
		msg = "testStaticMethod: "+ text;
	}
	
}
