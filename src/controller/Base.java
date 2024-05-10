/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import managers.WebDriverManager;

public class Base {
    protected WebDriver driver;
    WebDriverManager webDriverManager;
    
    public Base (WebDriver driver){
        if(driver == null){
            driver = webDriverManager.getDriver();
            PageFactory.initElements(driver, this);
        }else{
            this.driver = driver;
            PageFactory.initElements(driver, this);
        }
            
      }
}
    
        

    
