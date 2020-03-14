package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PrivateView extends PO_NavView {
	public static void checkElement(WebDriver driver, String type) {
		PO_View.checkElement(driver, "free", type);
	}

	public static void clickLink(WebDriver driver, String link) {
		driver.findElement(By.xpath("//a[@href='" + link + "']")).click();
	}

	public static void fillSearch(WebDriver driver, String search_text) {
		WebElement searchtext = driver.findElement(By.name("searchText"));
		searchtext.click();
		searchtext.clear();
		searchtext.sendKeys(search_text);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

}