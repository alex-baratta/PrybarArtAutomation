/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;
import dataProviders.ConfigFileReader;
import dataProviders.JsonReader;
/**
 *
 * @author alexb
 */
public class FileReaderManager {
    private static FileReaderManager fileReaderManager = new FileReaderManager();
    private static ConfigFileReader configFileReader;
    private static JsonReader jsonReader;
    
    private FileReaderManager(){
    }
    
    public static FileReaderManager getInstance(){
        return fileReaderManager;
    }
    
    public ConfigFileReader getConfigReader() {
        return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
    }
    public JsonReader getJsonReader() {
    	return (jsonReader == null) ? new JsonReader() : jsonReader;
    }
    
    
    
    
}
