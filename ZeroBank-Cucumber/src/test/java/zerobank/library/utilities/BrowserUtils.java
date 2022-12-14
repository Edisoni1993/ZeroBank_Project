package zerobank.library.utilities;

import com.google.common.base.Function;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertTrue;

public class BrowserUtils {

    public static void wait(int secs) {

        try {
            Thread.sleep(1000 * secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void switchToWindow(String targetTitle) {

        String origin = Driver.getDriver().getWindowHandle();

        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }

        Driver.getDriver().switchTo().window(origin);
    }

    public static void hover(WebElement element) {

        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    public static List<String> getElementsText(List<WebElement> list) {

        List<String> elemTexts = new ArrayList<>();

        for (WebElement el : list) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    public static List<String> getElementsText(By locator) {

        List<WebElement> element = Driver.getDriver().findElements(locator);
        List<String> elementText = new ArrayList<>();

        for (WebElement el : element) {
            elementText.add(el.getText());
        }
        return elementText;
    }

    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout) {

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(WebElement element, int timeout) {

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickability(By locator, int timeout) {

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement fluentWait(final WebElement webElement, int timeinsec) {

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(timeinsec))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {

            public WebElement apply(WebDriver driver) {

                return webElement;
            }
        });
        return element;
    }

    public static void verifyElementDisplayed(By by) {

        try {
            assertTrue("Element is not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException nse) {
            Assert.fail("Element not found: " + by);
        }
    }

    public static void verifyElementDisplayed(WebElement element) {

        try {
            assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + element);

        }
    }

    public void waitForStaleElement(WebElement element) {

        int y = 0;

        while (y <= 15) {
            if (y == 1)
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException st) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (WebDriverException we) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public WebElement selectRandomTextFromDropdown(Select select) {

        Random rnd = new Random();

        List<WebElement> weblist = select.getOptions();

        int optionIndex = 1 + rnd.nextInt(weblist.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }

    public void clickWithJS(WebElement element) {

        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void doubleClick(WebElement element) {

        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    public void setAttribute(WebElement element, String attributeName, String attributeValue) {

        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    public void selectCheckBox(WebElement element, boolean check) {

        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    public static List<String> getCommaList(String commaList){

        List<String> list =  Arrays.asList(commaList.split(","));
        for (int i = 0; i < list.size(); i++) {
            list.set(i,list.get(i).trim());
        }
        return list;
    }
}
