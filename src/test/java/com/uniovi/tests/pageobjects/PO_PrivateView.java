package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_PrivateView extends PO_NavView {
	static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) {
		// Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);
		// Seleccionamos el alumnos
		new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
		// Rellenemos el campo de descripción
		WebElement description = driver.findElement(By.name("description"));
		description.clear();
		description.sendKeys(descriptionp);
		WebElement score = driver.findElement(By.name("score"));
		score.click();
		score.clear();
		score.sendKeys(scorep);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	public static void checkUserList(WebDriver driver, String string, String string2, String string3, String string4,
			String string5) {
		checkElement(driver, "//*[@id='element']");
		checkElement(driver, "//*[/html/body/div/div/table/tbody/tr[1]/td[1]='" + string2 + "'");
		checkElement(driver, "//*[/html/body/div/div/table/tbody/tr[1]/td[1]='" + string3 + "'");
		checkElement(driver, "//*[/html/body/div/div/table/tbody/tr[1]/td[1]='" + string4 + "'");
		checkElement(driver, "//*[/html/body/div/div/table/tbody/tr[1]/td[1]='" + string5 + "'");
	}

	public static void checkElement(WebDriver driver, String type) {
		PO_View.checkElement(driver, "free", type);

	}

	public static void clickLink(WebDriver driver, String link) {
		driver.findElement(By.xpath("//a[@href='" + link + "']")).click();
	}
}