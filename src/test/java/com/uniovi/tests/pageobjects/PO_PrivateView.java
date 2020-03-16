package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.util.SeleniumUtils;

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

	public static void clickAdminOption(WebDriver driver, String textOption, String criterio, String textoDestino) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "@href", textOption, getTimeout()); //Tiene que haber un sólo elemento.
		assertTrue(elementos.size()==1); //Ahora lo clickamos
		elementos.get(0).click(); //Esperamos a que sea visible un elemento concreto 
//		elementos = SeleniumUtils.EsperaCargaPagina(driver, criterio, textoDestino, getTimeout()); //Tiene que haber un sólo elemento. 
//		assertTrue(elementos.size()==1); 
	}

}