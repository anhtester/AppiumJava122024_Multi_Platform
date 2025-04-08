package com.anhtester.keywords;

import com.anhtester.drivers.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;

import static com.anhtester.drivers.DriverManager.getDriver;

public class MobileUI {

    private static final int DEFAULT_TIMEOUT = 10;

    public static void sleep(double second) {
        System.out.println("[MobileUI] Sleeping for " + second + " seconds.");
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void swipe(int startX, int startY, int endX, int endY, int durationMillis) {
        System.out.println("[MobileUI] Executing swipe from (" + startX + "," + startY + ") to (" + endX + "," + endY + ") with duration " + durationMillis + "ms.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMillis), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(0));
        getDriver().perform(Collections.singletonList(swipe));
    }

    public static void swipeLeft() {
        System.out.println("[MobileUI] Executing swipeLeft.");
        Dimension size = getDriver().manage().window().getSize();
        int startX = (int) (size.width * 0.8);
        int startY = (int) (size.height * 0.3);
        int endX = (int) (size.width * 0.2);
        int endY = startY;
        int duration = 200;
        swipe(startX, startY, endX, endY, duration);
    }

    public static void swipeRight() {
        System.out.println("[MobileUI] Executing swipeRight.");
        Dimension size = getDriver().manage().window().getSize();
        int startX = (int) (size.width * 0.2);
        int startY = (int) (size.height * 0.3);
        int endX = (int) (size.width * 0.8);
        int endY = startY;
        int duration = 200;
        swipe(startX, startY, endX, endY, duration);
    }

    private static Point getCenterOfElement(Point location, Dimension size) {
        // No log needed for private helper, logging happens in the calling public method
        return new Point(location.getX() + size.getWidth() / 2,
                location.getY() + size.getHeight() / 2);
    }

    public static void tap(WebElement element) {
        System.out.println("[MobileUI] Executing tap on element: " + element);
        Point location = element.getLocation();
        Dimension size = element.getSize();
        Point centerOfElement = getCenterOfElement(location, size);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(500))) // Note: Default pause is 500ms here
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getDriver().perform(Collections.singletonList(sequence));
    }

    public static void tap(int x, int y) {
        System.out.println("[MobileUI] Executing tap at coordinates (" + x + "," + y + ") with 200ms pause.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(200))); //Chạm nhẹ nhanh
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getDriver().perform(Arrays.asList(tap));
    }

    public static void tap(int x, int y, int milliSecondDuration) {
        System.out.println("[MobileUI] Executing tap at coordinates (" + x + "," + y + ") with pause " + milliSecondDuration + "ms.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(milliSecondDuration))); //Chạm vào với thời gian chỉ định
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getDriver().perform(Arrays.asList(tap));
    }

    public static void zoom(WebElement element, double scale) {
        System.out.println("[MobileUI] Executing zoom on element: " + element + " with approximate scale factor: " + scale + " (Note: Implementation may need review for accurate scaling)");
        int centerX = element.getLocation().getX() + element.getSize().getWidth() / 2;
        int centerY = element.getLocation().getY() + element.getSize().getHeight() / 2;
        int distance = 100; // Khoảng cách giữa hai ngón tay

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

        Sequence zoom = new Sequence(finger1, 1);
        zoom.addAction(finger1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), centerX - distance, centerY));
        zoom.addAction(finger1.createPointerDown(0));

        Sequence zoom2 = new Sequence(finger2, 1);
        zoom2.addAction(finger2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), centerX + distance, centerY));
        zoom2.addAction(finger2.createPointerDown(0));

        // Simplified movement - Actual scaling might need more complex radial movement logic
        int moveDuration = 50;
        int steps = 10;
        int startDist1X = centerX - distance;
        int startDist2X = centerX + distance;
        int endDist1X, endDist2X;

        if (scale > 1) { // Phóng to - Move fingers further apart
            System.out.println("[MobileUI] Zooming In");
            endDist1X = centerX - (int) (distance * scale); // Example: move further left
            endDist2X = centerX + (int) (distance * scale); // Example: move further right
        } else { // Thu nhỏ - Move fingers closer
            System.out.println("[MobileUI] Zooming Out");
            endDist1X = centerX - (int) (distance * scale); // Example: move closer to center
            endDist2X = centerX + (int) (distance * scale); // Example: move closer to center
        }

        for (int i = 1; i <= steps; i++) {
            int currentX1 = startDist1X + (endDist1X - startDist1X) * i / steps;
            int currentX2 = startDist2X + (endDist2X - startDist2X) * i / steps;
            zoom.addAction(finger1.createPointerMove(Duration.ofMillis(moveDuration), PointerInput.Origin.viewport(), currentX1, centerY));
            zoom2.addAction(finger2.createPointerMove(Duration.ofMillis(moveDuration), PointerInput.Origin.viewport(), currentX2, centerY));
        }
        
        zoom.addAction(finger1.createPointerUp(0));
        zoom2.addAction(finger2.createPointerUp(0));

        getDriver().perform(Arrays.asList(zoom, zoom2));
    }

    public static void scroll(int startX, int startY, int endX, int endY, int durationMillis) {
        System.out.println("[MobileUI] Executing scroll from (" + startX + "," + startY + ") to (" + endX + "," + endY + ") with duration " + durationMillis + "ms.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMillis), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(0));
        getDriver().perform(Collections.singletonList(swipe));
    }

    public static void scrollGestureCommand() {
        // Scroll gesture cho Android
        Map<String, Object> scrollParams = new HashMap<>();
        scrollParams.put("left", 670);
        scrollParams.put("top", 500);
        scrollParams.put("width", 200);
        scrollParams.put("height", 2000);
        scrollParams.put("direction", "down");
        scrollParams.put("percent", 1);

        System.out.println("[MobileUI] Executing scrollGesture command with params: " + scrollParams);
        // Thực hiện scroll gesture
        getDriver().executeScript("mobile: scrollGesture", scrollParams);
    }

    public static void clickElement(By locator, int second) {
        System.out.println("[MobileUI] Clicking element located by: " + locator + " within " + second + "s.");
        waitForElementToBeClickable(locator, second).click();
    }

    public static void clickElement(By locator) {
        System.out.println("[MobileUI] Clicking element located by: " + locator + " within default timeout (" + DEFAULT_TIMEOUT + "s).");
        waitForElementToBeClickable(locator).click();
    }

    public static void clickElement(WebElement element, int second) {
        System.out.println("[MobileUI] Clicking element: " + element + " within " + second + "s.");
        waitForElementToBeClickable(element, second).click();
    }

    public static void clickElement(WebElement element) {
        System.out.println("[MobileUI] Clicking element: " + element + " within default timeout (" + DEFAULT_TIMEOUT + "s).");
        waitForElementToBeClickable(element).click();
    }

    public static void setText(By locator, String text) {
        System.out.println("[MobileUI] Setting text '" + text + "' on element located by: " + locator + " with default timeout.");
        WebElement element = waitForElementVisibe(locator);
        element.click(); // Often needed before clear/sendKeys
        element.clear();
        element.sendKeys(text);
        System.out.println("[MobileUI] Set text completed for locator: " + locator);
    }

    public static void setText(By locator, String text, int second) {
        System.out.println("[MobileUI] Setting text '" + text + "' on element located by: " + locator + " with timeout " + second + "s.");
        WebElement element = waitForElementVisibe(locator, second);
        element.click();
        element.clear();
        element.sendKeys(text);
        System.out.println("[MobileUI] Set text completed for locator: " + locator);
    }

    public static void setText(WebElement element, String text) {
        System.out.println("[MobileUI] Setting text '" + text + "' on element: " + element + " with default timeout.");
        WebElement elm = waitForElementVisibe(element);
        elm.click();
        elm.clear();
        elm.sendKeys(text);
        System.out.println("[MobileUI] Set text completed for element: " + element);

    }

    public static void setText(WebElement element, String text, int second) {
        System.out.println("[MobileUI] Setting text '" + text + "' on element: " + element + " with timeout " + second + "s.");
        WebElement elm = waitForElementVisibe(element, second);
        elm.click();
        elm.clear();
        elm.sendKeys(text);
        System.out.println("[MobileUI] Set text completed for element: " + element);
    }

    public static void clearText(By locator) {
        System.out.println("[MobileUI] Clearing text on element located by: " + locator + " with default timeout.");
        WebElement element = waitForElementVisibe(locator);
        element.click();
        element.clear();
        System.out.println("[MobileUI] Clear text completed for locator: " + locator);
    }

    public static void clearText(By locator, int second) {
        System.out.println("[MobileUI] Clearing text on element located by: " + locator + " with timeout " + second + "s.");
        WebElement element = waitForElementVisibe(locator, second);
        element.click();
        element.clear();
        System.out.println("[MobileUI] Clear text completed for locator: " + locator);
    }

    public static void clearText(WebElement element) {
        System.out.println("[MobileUI] Clearing text on element: " + element + " with default timeout.");
        WebElement elm = waitForElementVisibe(element);
        elm.click();
        elm.clear();
        System.out.println("[MobileUI] Clear text completed for element: " + element);
    }

    public static void clearText(WebElement element, int second) {
        System.out.println("[MobileUI] Clearing text on element: " + element + " with timeout " + second + "s.");
        WebElement elm = waitForElementVisibe(element, second);
        elm.click();
        elm.clear();
        System.out.println("[MobileUI] Clear text completed for element: " + element);
    }

    public static String getElementText(By locator) {
        System.out.println("[MobileUI] Getting text from element located by: " + locator + " with default timeout.");
        WebElement element = waitForElementVisibe(locator);
        String text = element.getText();
        System.out.println("[MobileUI] Retrieved text: '" + text + "'");
        return text;
    }

    public static String getElementText(By locator, int second) {
        System.out.println("[MobileUI] Getting text from element located by: " + locator + " with timeout " + second + "s.");
        WebElement element = waitForElementVisibe(locator, second);
        String text = element.getText();
        System.out.println("[MobileUI] Retrieved text: '" + text + "'");
        return text;
    }

    public static String getElementText(WebElement element) {
        System.out.println("[MobileUI] Getting text from element: " + element + " with default timeout.");
        WebElement elm = waitForElementVisibe(element);
        String text = elm.getText();
        System.out.println("[MobileUI] Retrieved text: '" + text + "'");
        return text;
    }

    public static String getElementText(WebElement element, int second) {
        System.out.println("[MobileUI] Getting text from element: " + element + " with timeout " + second + "s.");
        WebElement elm = waitForElementVisibe(element, second);
        String text = elm.getText();
        System.out.println("[MobileUI] Retrieved text: '" + text + "'");
        return text;
    }

    public static String getElementAttribute(By locator, String attribute) {
        System.out.println("[MobileUI] Getting attribute '" + attribute + "' from element located by: " + locator + " with default timeout.");
        WebElement element = waitForElementVisibe(locator);
        String value = element.getAttribute(attribute);
        System.out.println("[MobileUI] Retrieved attribute value: '" + value + "'");
        return value;
    }

    public static String getElementAttribute(By locator, String attribute, int second) {
        System.out.println("[MobileUI] Getting attribute '" + attribute + "' from element located by: " + locator + " with timeout " + second + "s.");
        WebElement element = waitForElementVisibe(locator, second);
        String value = element.getAttribute(attribute);
        System.out.println("[MobileUI] Retrieved attribute value: '" + value + "'");
        return value;
    }

    public static String getElementAttribute(WebElement element, String attribute) {
        System.out.println("[MobileUI] Getting attribute '" + attribute + "' from element: " + element + " with default timeout.");
        WebElement elm = waitForElementVisibe(element);
        String value = elm.getAttribute(attribute);
        System.out.println("[MobileUI] Retrieved attribute value: '" + value + "'");
        return value;
    }

    public static String getElementAttribute(WebElement element, String attribute, int second) {
        System.out.println("[MobileUI] Getting attribute '" + attribute + "' from element: " + element + " with timeout " + second + "s.");
        WebElement elm = waitForElementVisibe(element, second);
        String value = elm.getAttribute(attribute);
        System.out.println("[MobileUI] Retrieved attribute value: '" + value + "'");
        return value;
    }

    public static boolean isElementPresentAndDisplayed(WebElement element) {
        System.out.println("[MobileUI] Checking if element is present and displayed: " + element);
        boolean result;
        try {
            result = element != null && element.isDisplayed();
            System.out.println("[MobileUI] Element present and displayed check result: " + result);
            return result;
        } catch (NoSuchElementException e) {
            System.out.println("[MobileUI] Element not found during presence/display check: " + element + " - " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("[MobileUI] An error occurred checking presence/display for element: " + element + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementPresentAndDisplayed(By locator) {
        System.out.println("[MobileUI] Checking if element is present and displayed: " + locator);
        boolean result;
        try {
            WebElement element = getDriver().findElement(locator); // Find first, then check display
            result = element != null && element.isDisplayed();
            System.out.println("[MobileUI] Element present and displayed check result: " + result + " for locator: " + locator);
            return result;
        } catch (NoSuchElementException e) {
            System.out.println("[MobileUI] Element not found during presence/display check: " + locator + " - " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("[MobileUI] An error occurred checking presence/display for locator: " + locator + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementEnabled(WebElement element) {
        System.out.println("[MobileUI] Checking if element is enabled: " + element);
        boolean result;
        try {
            result = element != null && element.isEnabled();
            System.out.println("[MobileUI] Element enabled check result: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("[MobileUI] An error occurred checking enabled status for element: " + element + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementEnabled(By locator) {
        System.out.println("[MobileUI] Checking if element is enabled: " + locator);
        boolean result;
        try {
            WebElement element = waitForElementVisibe(locator); // Ensure it's visible before checking enabled
            result = element != null && element.isEnabled();
            System.out.println("[MobileUI] Element enabled check result: " + result + " for locator: " + locator);
            return result;
        } catch (Exception e) {
            System.out.println("[MobileUI] An error occurred checking enabled status for locator: " + locator + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementSelected(WebElement element) {
        System.out.println("[MobileUI] Checking if element is selected: " + element);
        boolean result;
        try {
            result = element != null && element.isSelected();
            System.out.println("[MobileUI] Element selected check result: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("[MobileUI] An error occurred checking selected status for element: " + element + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementSelected(By locator) {
        System.out.println("[MobileUI] Checking if element is selected: " + locator);
        boolean result;
        try {
            WebElement element = waitForElementVisibe(locator); // Ensure it's visible before checking selected
            result = element != null && element.isSelected();
            System.out.println("[MobileUI] Element selected check result: " + result + " for locator: " + locator);
            return result;
        } catch (Exception e) {
            System.out.println("[MobileUI] An error occurred checking selected status for locator: " + locator + " - " + e.getMessage());
            return false;
        }
    }


    // Các hàm verify (sử dụng Assert và gọi lại các hàm is)

    public static void verifyElementPresentAndDisplayed(WebElement element, String message) {
        System.out.println("[MobileUI] Verifying element is present and displayed: " + element + ". Message if failed: " + message);
        Assert.assertTrue(isElementPresentAndDisplayed(element), message);
    }

    public static void verifyElementPresentAndDisplayed(By locator, String message) {
        System.out.println("[MobileUI] Verifying element is present and displayed: " + locator + ". Message if failed: " + message);
        Assert.assertTrue(isElementPresentAndDisplayed(locator), message);
    }

    public static void verifyElementEnabled(WebElement element, String message) {
        System.out.println("[MobileUI] Verifying element is enabled: " + element + ". Message if failed: " + message);
        Assert.assertTrue(isElementEnabled(element), message);
    }

    public static void verifyElementEnabled(By locator, String message) {
        System.out.println("[MobileUI] Verifying element is enabled: " + locator + ". Message if failed: " + message);
        Assert.assertTrue(isElementEnabled(locator), message);
    }

    public static void verifyElementSelected(WebElement element, String message) {
        System.out.println("[MobileUI] Verifying element is selected: " + element + ". Message if failed: " + message);
        Assert.assertTrue(isElementSelected(element), message);
    }

    public static void verifyElementSelected(By locator, String message) {
        System.out.println("[MobileUI] Verifying element is selected: " + locator + ". Message if failed: " + message);
        Assert.assertTrue(isElementSelected(locator), message);
    }

    public static void verifyElementText(WebElement element, String expectedText, String message) {
        System.out.println("[MobileUI] Verifying text of element: " + element + " equals '" + expectedText + "'. Message if failed: " + message);
        Assert.assertEquals(getElementText(element), expectedText, message);
    }

    public static void verifyElementText(By locator, String expectedText, String message) {
        System.out.println("[MobileUI] Verifying text of element: " + locator + " equals '" + expectedText + "'. Message if failed: " + message);
        Assert.assertEquals(getElementText(locator), expectedText, message);
    }

    public static void verifyElementAttribute(WebElement element, String attribute, String expectedValue, String message) {
        System.out.println("[MobileUI] Verifying attribute '" + attribute + "' of element: " + element + " equals '" + expectedValue + "'. Message if failed: " + message);
        Assert.assertEquals(getElementAttribute(element, attribute), expectedValue, message);
    }

    public static void verifyElementAttribute(By locator, String attribute, String expectedValue, String message) {
        System.out.println("[MobileUI] Verifying attribute '" + attribute + "' of element: " + locator + " equals '" + expectedValue + "'. Message if failed: " + message);
        Assert.assertEquals(getElementAttribute(locator, attribute), expectedValue, message);
    }

    public static void assertTrueCondition(boolean condition, String message) {
        System.out.println("[MobileUI] Asserting condition: " + condition + ". Message if failed: " + message);
        Assert.assertTrue(condition, message);
        System.out.println("[MobileUI] Assertion passed for condition: " + condition);
    }


    // --- Wait Methods ---

    public static WebElement waitForElementToBeClickable(By locator, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for element to be clickable: " + locator);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementToBeClickable(By locator) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be clickable: " + locator);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementToBeClickable(WebElement element, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for element to be clickable: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementToBeClickable(WebElement element) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be clickable: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementVisibe(By locator, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for element to be visible: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementVisibe(By locator) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be visible: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementVisibe(WebElement element, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for element to be visible: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForElementVisibe(WebElement element) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be visible: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static boolean waitForElementInvisibe(By locator, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for element to be invisible: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForElementInvisibe(By locator) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be invisible: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForElementInvisibe(WebElement element, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for element to be invisible: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static boolean waitForElementInvisibe(WebElement element) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be invisible: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static WebElement waitForElementPresent(By locator, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for element to be present in DOM: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForElementPresent(By locator) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be present in DOM: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean waitForTextToBePresent(By locator, String text, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for text '" + text + "' to be present in element: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean waitForTextToBePresent(By locator, String text) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for text '" + text + "' to be present in element: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean waitForTextToBePresent(WebElement element, String text, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for text '" + text + "' to be present in element: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static boolean waitForTextToBePresent(WebElement element, String text) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for text '" + text + "' to be present in element: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static boolean waitForAttributeToBe(By locator, String attribute, String value, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for attribute '" + attribute + "' to be '" + value + "' in element: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.attributeToBe(locator, attribute, value));
    }

    public static boolean waitForAttributeToBe(By locator, String attribute, String value) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for attribute '" + attribute + "' to be '" + value + "' in element: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.attributeToBe(locator, attribute, value));
    }

    public static boolean waitForAttributeToBe(WebElement element, String attribute, String value, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for attribute '" + attribute + "' to be '" + value + "' in element: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public static boolean waitForAttributeToBe(WebElement element, String attribute, String value) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for attribute '" + attribute + "' to be '" + value + "' in element: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public static List<WebElement> waitForNumberOfElements(By locator, int expectedCount, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for number of elements to be " + expectedCount + " for locator: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.numberOfElementsToBe(locator, expectedCount));
    }

    public static List<WebElement> waitForNumberOfElements(By locator, int expectedCount) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for number of elements to be " + expectedCount + " for locator: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.numberOfElementsToBe(locator, expectedCount));
    }

    public static boolean waitForUrlContains(String text, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for URL to contain: '" + text + "'");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.urlContains(text));
    }

    public static boolean waitForUrlContains(String text) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for URL to contain: '" + text + "'");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.urlContains(text));
    }

    public static boolean waitForNumberOfWindows(int expectedWindows, int timeout) {
        System.out.println("[MobileUI] Waiting up to " + timeout + "s for number of windows to be: " + expectedWindows);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindows));
    }

    public static boolean waitForNumberOfWindows(int expectedWindows) {
        System.out.println("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for number of windows to be: " + expectedWindows);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindows));
    }

}