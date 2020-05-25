package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PostView extends PO_NavView {
	public static void fillForm(WebDriver driver, String titlep, String textp) {
		WebElement email = driver.findElement(By.name("title"));
		email.click();
		email.clear();
		email.sendKeys(titlep);
		WebElement password = driver.findElement(By.name("text"));
		password.click();
		password.clear();
		password.sendKeys(textp);
		By boton = By.className("btn");
		driver.findElement(boton).click();

	}
}
