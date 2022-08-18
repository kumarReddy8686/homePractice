package com.practice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLinks {

	public static void main(String[] args) throws IOException {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.setAcceptInsecureCerts(true);
		WebDriver driver = new ChromeDriver(options);
		driver.get("http://www.deadlinkcity.com/");
		List<WebElement> links = driver.findElements(By.tagName("a"));
		int brokenLinks = 0;
		for (WebElement linkElements : links) {
			String url = linkElements.getAttribute("href");
			if (url == null || url.isEmpty()) {
				System.out.println("url is empty");
				continue;
			}
			URL link = new URL(url);
			try {

				HttpURLConnection httpCon = (HttpURLConnection) link.openConnection();
				httpCon.connect();
				if (httpCon.getResponseCode() >= 400) {
					System.out.println(httpCon.getResponseCode() + url + " is " + "link is broken");
					brokenLinks++;
				} else {
					System.out.println(httpCon.getResponseCode() + url + "is" + "link is valid");
				}
			} catch (Exception e) {

			}

		}
		System.out.println("Number of broken links:" + brokenLinks);
		driver.close();

	}

}
