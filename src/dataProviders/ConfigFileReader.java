/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import managers.FileReaderManager;

public class ConfigFileReader {
    private Properties properties;
    private final String propertyFilePath = System.getProperty("user.dir") + "\\Configs\\Config.properties";
    
    public ConfigFileReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
                }
            catch (IOException e){
                e.printStackTrace();
                }
            }
        catch (FileNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("configuration properties not found at "+ propertyFilePath);
        }
    }
     
    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if (implicitlyWait !=null){
            try {
                return Long.parseLong(implicitlyWait);
                }   
            catch (NumberFormatException e){
                 throw new RuntimeException("Unable to parse value: " + implicitlyWait + "in to a long");
                }
            }
        return 30;
        }
        
    public Boolean getBrowserWindowSize(){
        String windowSize = properties.getProperty("windowMaximize");
        if (windowSize!= null){
            return Boolean.valueOf(windowSize);
        }
        return true;    
    }
    public String getReportConfigPath() {
    	String reportConfigPath = System.getProperty("user.dir") +properties.getProperty("reportConfigPath");
    	if (reportConfigPath != null) {
    		return reportConfigPath;}
    	else {
    		throw new RuntimeException("Report Config path not set in Cofiguration.properties");
    	}
    	
    	
    }
    public String getHomePageUrl() {
    	return System.getProperty("URL");
    	
    }
    
    
}
