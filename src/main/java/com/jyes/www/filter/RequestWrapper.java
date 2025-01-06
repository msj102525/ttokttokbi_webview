package com.jyes.www.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

public final class RequestWrapper extends HttpServletRequestWrapper {

	public RequestWrapper(HttpServletRequest servletRequest) {

		super(servletRequest);

	}

	public String[] getParameterValues(String parameter) {

		String[] values = super.getParameterValues(parameter);

		if (values == null) {

			return null;

		}

		int count = values.length;

		String[] encodedValues = new String[count];

		for (int i = 0; i < count; i++) {

			encodedValues[i] = cleanXSS(values[i]);

		}

		return encodedValues;

	}

	public String getParameter(String parameter) {

		String value = super.getParameter(parameter);

		if (value == null) {

			return null;

		}

		return cleanXSS(value);

	}

	public String getHeader(String name) {

		String value = super.getHeader(name);

		if (value == null)

			return null;

		return cleanXSS(value);

	}
	
	@Override
	public Part getPart(String name) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return super.getPart(name);
	}
	
	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		// TODO Auto-generated method stub
		return super.getParts();
	}

	public static String cleanXSS(String value) {

//		System.out.println("pre:"+value);

		/*value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("#", "&#35;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");*/
		
//		System.out.println("post:"+value);
		return value;

	}

}
