package com.demo.cs.login;


import com.google.common.collect.ImmutableMap;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ContextConfiguration(classes = TestApplication.class)
@TestPropertySource("/application.properties")
public class Stepdefs implements En {
    private static Logger logger = LoggerFactory.getLogger(Stepdefs.class);

    private static WebDriver driver;
    private static int DEFAULT_WAIT_TIME = 5;

    @Value("${server.port}")
    private Integer port;

    private static final String LOCAL_ADDRESS = "http://localhost:";

    private ImmutableMap map = ImmutableMap.of("成功", "SUCCESS");

    @Before
    public void setUp() {
        if ("Linux".equals(System.getProperty("os.name"))) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        }


        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);

        driver.get(LOCAL_ADDRESS + port);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }


    @Given("^输入账号(\\S+)$")
    public void inputUserName(String userName) throws Exception {
        WebElement input = waitForElement(By.id("userName"));
        input.sendKeys(userName);
    }

    @And("^输入密码(\\S+)$")
    public void inputPwd(String pwd) throws Exception {
        WebElement input = waitForElement(By.id("pwd"));
        input.sendKeys(pwd);
    }

    @When("^点击登录按钮$")
    public void clickButton() throws Exception {
        WebElement input = waitForElement(By.id("click"));
        input.click();
    }

    @Then("^显示登录(\\S+)$")
    public void result(String result) throws Exception {
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
        WebElement input = waitForElement(By.id("result"));
        //这个地方不能用getText()获取值
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String text = (String) js.executeScript("return arguments[0].value;", input);
        assertThat(text).isEqualTo(map.get(result));
    }

    public WebElement waitForElement(By by) {
        return (new WebDriverWait(driver, DEFAULT_WAIT_TIME)).until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
