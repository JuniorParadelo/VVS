package es.udc.pojoapp.test.webtest;

import org.junit.Test;

import static es.udc.pojoapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pojoapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
public class AutenticateUserWebTest {
	
	private WebDriver driver = new FirefoxDriver();
	private String baseUrl = "http://localhost:9090";
	private String user = "user";
	
	@Test
	public void autenticacionTest() throws Exception {
		driver.get(baseUrl + "/pojo-app/");
		
		driver.findElement(By.cssSelector("button.navbar-toggle")).click();
	    driver.findElement(By.linkText("Authenticate")).click();
	    driver.findElement(By.id("loginName")).clear();
	    driver.findElement(By.id("loginName")).sendKeys("user");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("user");
	    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
	    driver.findElement(By.cssSelector("button.navbar-toggle")).click();
	    driver.findElement(By.linkText("user"));
	    
		WebElement nameUser = driver.findElement(By.linkText(user));
		
		assertEquals("user",nameUser.getText());
	}
	
	@Test
	public void apostarTest() {
		String evento = "Deportivo - Real Madrid";
		String option = "2";
		String user = "user";
		String cantidad = "50";
		driver.get(baseUrl + "/pojo-app/");
		
		driver.findElement(By.cssSelector("button.navbar-toggle")).click();
		driver.findElement(By.linkText("Search")).click();
		driver.findElement(By.linkText("Find events")).click();
		driver.findElement(By.cssSelector("p")).click();
		
		driver.findElement(By.id("nameEvent")).clear();
		driver.findElement(By.id("nameEvent")).sendKeys(evento);
		driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
		
		driver.findElement(By.linkText(evento)).click();
		driver.findElement(By.linkText(option)).click();
		
		driver.findElement(By.id("loginName")).clear();
		driver.findElement(By.id("loginName")).sendKeys(user);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(user);		
		driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
		
		driver.findElement(By.id("apuesta")).clear();
		driver.findElement(By.id("apuesta")).sendKeys(cantidad);
		driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
		
		driver.findElement(By.cssSelector("button.navbar-toggle")).click();
		driver.findElement(By.linkText("Search")).click();
		driver.findElement(By.linkText("Find bets")).click();
		
		assertTrue(driver.getPageSource().contains(evento));
		assertTrue(driver.getPageSource().contains(option));
		assertTrue(driver.getPageSource().contains(cantidad));
	}

}
