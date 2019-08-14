package edu.udacity.selenium.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {WebSocketChatApplication.class})
public class WebSocketChatTest {
    WebDriver driverAdmin;
    WebDriver driverTester;

    String browser;
    String url;
    String port;

    String adminUser = "Admin";
    String testerUser ="Tester";

    String adminUrl;
    String testerUrl;

    @Before
    public void setUp() throws IOException {
        System.out.println("setUp..");
        Properties props= new Properties();
        props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        System.out.println(props.toString());

        browser = props.getProperty("browser");
        url = props.getProperty("url");
        port = props.getProperty("port");

        adminUrl = "http://"+ url + ":" + port + "/index?username=" + adminUser;
        testerUrl = "http://"+ url + ":" + port + "/index?username=" + testerUser;
        System.out.println("adminUrl=" + adminUrl);
        System.out.println("testerUrl=" + testerUrl);

        System.setProperty("webdriver.gecko.driver", browser);
        driverAdmin = new FirefoxDriver();
        driverTester = new FirefoxDriver();
    }

    @After
    public void tearDown(){
        System.out.println("..tearDown");
        driverAdmin.close();
        driverTester.close();
    }

    @Test
    public void joinAndOnlineCountTest() {
        driverAdmin.get(adminUrl);
        int chatNum = getIntFromElementID(driverAdmin, "chat-num");

        driverTester.get(testerUrl);
        int chatNumNew = getIntFromElementID(driverTester, "chat-num");

        System.out.println("joinAndOnlineCountTest: chatNum=" + chatNum + ", chatNumNew=" + chatNumNew);
        // Test case: join, online count
        Assert.assertTrue(chatNumNew==2);
    }

    @Test
    public void messagingTest() {
        driverAdmin.get(adminUrl);
        JavascriptExecutor js = (JavascriptExecutor) driverAdmin;
        WebElement msg = driverAdmin.findElement(By.id("msg"));
        msg.sendKeys("Hi there, it's me again");
        System.out.println("messagingTest: msg=" + msg.getAttribute("value"));

        WebElement element = driverAdmin.findElement(By.id("message-container"));
        List<WebElement> messageElements = element.findElements(By.className("mdui-card"));

        js.executeScript("sendMsgToServer();");

        driverTester.get(testerUrl);
        List<WebElement> messageElementsNew = element.findElements(By.className("mdui-card"));
        System.out.println("messagingTest: messageElements.size()=" + messageElements.size() + ", messageElementsNew.size()=" + messageElementsNew.size());
        // Test case: messaging
        Assert.assertTrue(messageElementsNew.size() == messageElements.size() +1);
    }

    @Test
    public void clearTest() {
        driverAdmin.get(adminUrl);
        JavascriptExecutor jsAdmin = (JavascriptExecutor) driverAdmin;
        WebElement msgAdmin = driverAdmin.findElement(By.id("msg"));
        msgAdmin.sendKeys("Hi there from Admin");
        jsAdmin.executeScript("sendMsgToServer();");

        driverTester.get(testerUrl);
        JavascriptExecutor jsTester = (JavascriptExecutor) driverTester;
        WebElement msgTester = driverTester.findElement(By.id("msg"));
        msgTester.sendKeys("Hi there from Tester");
        jsTester.executeScript("sendMsgToServer();");

        WebElement element = driverAdmin.findElement(By.id("message-container"));
        List<WebElement> messageElements = element.findElements(By.className("mdui-card"));

        jsAdmin.executeScript("clearMsg();");
        WebElement elementNew = driverAdmin.findElement(By.id("message-container"));
        List<WebElement> messageElementsNew = elementNew.findElements(By.className("mdui-card"));

        System.out.println("clearTest: " + "messageElements.size()=" + messageElements.size() + ", messageElementsNew.size()=" + messageElementsNew.size());
        // Test case: clear (no leaving implemented)
        Assert.assertTrue(messageElementsNew.size() == 0);
    }

    private String getStringFromElementID(WebDriver driver, String elementId){
        WebElement element = driver.findElement(By.id(elementId));
        return element.getText();
    }
    private int getIntFromElementID(WebDriver driver, String elementId){
        String elementText = getStringFromElementID(driver, elementId);
        return Integer.parseInt(elementText);
    }

    private String getAttributeValueFromElementID(WebDriver driver, String elementId){
        WebElement element = driver.findElement(By.id(elementId));
        return element.getAttribute("value");
    }
}
