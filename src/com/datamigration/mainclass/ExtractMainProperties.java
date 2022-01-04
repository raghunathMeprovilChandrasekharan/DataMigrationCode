package com.datamigration.mainclass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ExtractMainProperties{
	
	public String getPropValue(String propertiesNameVal) throws IOException{
		Properties prop = new Properties();
		String propertiesName= "config.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesName);

		if(inputStream != null ) {
			prop.load(inputStream);
			
		}else{
			throw new FileNotFoundException("propertiesFIle Cannort be located");
		}
		
		return prop.getProperty(propertiesNameVal);
	}

}
