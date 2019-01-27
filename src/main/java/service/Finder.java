package service;

import entity.HtmlElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Finder {

    private static final String ELEMENT_ID = "make-everything-ok-button";

    private WebDriver webDriverOrigin;
    private WebDriver webDriverOther;

    private HtmlElement origin;

    public Finder(String originInputFilePath, String otherInputFilePath) {
        try {
            if (!filesExist(originInputFilePath, otherInputFilePath)) {
                throw new FileNotFoundException();
            }
            webDriverOrigin = new ChromeDriver();
            webDriverOther = new ChromeDriver();
            webDriverOrigin.get(originInputFilePath);
            webDriverOther.get(otherInputFilePath);
        } catch (FileNotFoundException e) {
            System.err.println("Input file is not found\n" + e.getCause());
        }
    }

    public void dispose() {
        webDriverOrigin.close();
        webDriverOther.close();
    }

    private boolean filesExist(String inputFilePath, String otherInputFilePath) {
        File file = new File(inputFilePath);
        File otherFile = new File(otherInputFilePath);
        return file.exists() && otherFile.exists();
    }

    private String getElementXPath(WebDriver driver, WebElement element) {
        return (String) ((JavascriptExecutor) driver)
                .executeScript
                        (
                                "gPt=function(c)" +
                                        "{if(c.id!=='')" +
                                        "{return'id(\"'+c.id+'\")'}" +
                                        "if(c===document.body)" +
                                        "{return c.tagName}var a=0;var e=c.parentNode.childNodes;" +
                                        "for(var b=0;b<e.length;b++)" +
                                        "{var d=e[b];if(d===c)" +
                                        "{return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}" +
                                        "if(d.nodeType===1&&d.tagName===c.tagName){a++}}};" +
                                        "return gPt(arguments[0]).toLowerCase();",
                                element
                        );
    }

    public void process() {
        WebElement originElement = webDriverOrigin.findElement(By.id(ELEMENT_ID));

        origin = new HtmlElement(
                originElement.getAttribute("id"),
                Arrays.asList(originElement.getAttribute("class").split(" ")),
                originElement.getAttribute("href"),
                originElement.getAttribute("title"),
                originElement.getAttribute("onclick"),
                originElement.getText(),
                getElementXPath(webDriverOrigin, originElement)
        );

        List<WebElement> allElements = webDriverOther.findElements(By.xpath("*"));

    }
}
