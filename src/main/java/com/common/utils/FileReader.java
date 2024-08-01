package com.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;



/**
 * FileReader is to set the environment variable declaration
 * mapping for config properties in the UI test
 */
public class FileReader {


	private static FileReader fileValues;

	private Properties properties;

	private FileReader() {
		properties = loadProperties();
	}

	private FileReader(String fileName) {
		properties = loadProperties(fileName);
	}

	private Properties loadProperties() {

		Properties props = new Properties();

		try {
			InputStream cpr = FileReader.class.getResourceAsStream("/DataValues");
			props.load(cpr);
			cpr.close();

		} catch (FileNotFoundException e) {
			System.out.println("config.properties is missing or corrupt : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("read failed due to: " + e.getMessage());
		}

		return props;
	}

	private Properties loadProperties(String fileName) {

		Properties props = new Properties();

		try {
			InputStream cpr = FileReader.class.getResourceAsStream("/"+fileName);
			props.load(cpr);
			cpr.close();

		} catch (FileNotFoundException e) {
			System.out.println("config.properties is missing or corrupt : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("read failed due to: " + e.getMessage());
		}

		return props;
	}

	public static FileReader getInstance() {
		if (fileValues == null) {
			fileValues = new FileReader();
		}
		return fileValues;
	}

	public static FileReader getInstance(String fileName) {

			fileValues = new FileReader(fileName);

		return fileValues;
	}

	public String getProperty(String key) {
		return properties.getProperty(key).trim();
	}

	public boolean hasProperty(String key) {
		return StringUtils.isNotBlank(properties.getProperty(key));
	}
}
