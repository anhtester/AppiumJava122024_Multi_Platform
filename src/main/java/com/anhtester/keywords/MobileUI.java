package com.anhtester.keywords;

import com.anhtester.constants.ConfigData;
import com.anhtester.drivers.DriverManager;
import com.anhtester.reports.AllureManager;
import com.anhtester.utils.LogUtils;
import io.qameta.allure.Step;
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

    private static final int DEFAULT_TIMEOUT = Integer.parseInt(ConfigData.TIMEOUT_EXPLICIT_DEFAULT);
    private static final double STEP_ACTION_TIMEOUT = Double.parseDouble(ConfigData.STEP_ACTION_TIMEOUT);

    public static void sleep(double second) {
        LogUtils.info("[MobileUI] Sleeping for " + second + " seconds.");
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void swipe(int startX, int startY, int endX, int endY, int durationMillis) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Executing swipe from (" + startX + "," + startY + ") to (" + endX + "," + endY + ") with duration " + durationMillis + "ms.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMillis), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(0));
        getDriver().perform(Collections.singletonList(swipe));
    }

    public static void swipeLeft() {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Executing swipeLeft.");
        Dimension size = getDriver().manage().window().getSize();
        int startX = (int) (size.width * 0.8);
        int startY = (int) (size.height * 0.3);
        int endX = (int) (size.width * 0.2);
        int endY = startY;
        int duration = 200;
        swipe(startX, startY, endX, endY, duration);
    }

    public static void swipeRight() {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Executing swipeRight.");
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
        return new Point(location.getX() + size.getWidth() / 2, location.getY() + size.getHeight() / 2);
    }

    public static void tap(WebElement element) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Executing tap on element: " + element);
        Point location = element.getLocation();
        Dimension size = element.getSize();
        Point centerOfElement = getCenterOfElement(location, size);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1).addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement)).addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())).addAction(new Pause(finger, Duration.ofMillis(500))) // Note: Default pause is 500ms here
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getDriver().perform(Collections.singletonList(sequence));
    }

    public static void tap(int x, int y) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Executing tap at coordinates (" + x + "," + y + ") with 200ms pause.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(200))); //Chạm nhẹ nhanh
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getDriver().perform(Arrays.asList(tap));
    }

    public static void tap(int x, int y, int milliSecondDuration) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Executing tap at coordinates (" + x + "," + y + ") with pause " + milliSecondDuration + "ms.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(milliSecondDuration))); //Chạm vào với thời gian chỉ định
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getDriver().perform(Arrays.asList(tap));
    }

    public static void zoom(WebElement element, double scale) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Executing zoom on element: " + element + " with approximate scale factor: " + scale + " (Note: Implementation may need review for accurate scaling)");
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
            LogUtils.info("[MobileUI] Zooming In");
            endDist1X = centerX - (int) (distance * scale); // Example: move further left
            endDist2X = centerX + (int) (distance * scale); // Example: move further right
        } else { // Thu nhỏ - Move fingers closer
            LogUtils.info("[MobileUI] Zooming Out");
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
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Executing scroll from (" + startX + "," + startY + ") to (" + endX + "," + endY + ") with duration " + durationMillis + "ms.");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMillis), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(0));
        getDriver().perform(Collections.singletonList(swipe));
    }

    public static void scrollGestureCommand() {
        sleep(STEP_ACTION_TIMEOUT);
        // Scroll gesture cho Android
        Map<String, Object> scrollParams = new HashMap<>();
        scrollParams.put("left", 670);
        scrollParams.put("top", 500);
        scrollParams.put("width", 200);
        scrollParams.put("height", 2000);
        scrollParams.put("direction", "down");
        scrollParams.put("percent", 1);

        LogUtils.info("[MobileUI] Executing scrollGesture command with params: " + scrollParams);
        // Thực hiện scroll gesture
        getDriver().executeScript("mobile: scrollGesture", scrollParams);
    }

    @Step("Click element: {0} within {1}s")
    public static void clickElement(By locator, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Clicking element located by: " + locator + " within " + second + "s.");
        waitForElementToBeClickable(locator, second).click();
    }

    @Step("Click element: {0}")
    public static void clickElement(By locator) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Clicking element located by: " + locator + " within default timeout (" + DEFAULT_TIMEOUT + "s).");
        waitForElementToBeClickable(locator).click();
    }

    @Step("Click element: {0} within {1}s")
    public static void clickElement(WebElement element, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Clicking element: " + element + " within " + second + "s.");
        waitForElementToBeClickable(element, second).click();
    }

    @Step("Click element: {0}")
    public static void clickElement(WebElement element) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Clicking element: " + element + " within default timeout (" + DEFAULT_TIMEOUT + "s).");
        waitForElementToBeClickable(element).click();
    }

    @Step("Set text '{1}' on element {0}")
    public static void setText(By locator, String text) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Setting text '" + text + "' on element located by: " + locator + " with default timeout.");
        WebElement element = waitForElementVisible(locator);
        element.click(); // Often needed before clear/sendKeys
        element.clear();
        element.sendKeys(text);
        LogUtils.info("[MobileUI] Set text completed for locator: " + locator);
    }

    @Step("Set text '{1}' on element {0} within {2}s")
    public static void setText(By locator, String text, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Setting text '" + text + "' on element located by: " + locator + " with timeout " + second + "s.");
        WebElement element = waitForElementVisible(locator, second);
        element.click();
        element.clear();
        element.sendKeys(text);
        LogUtils.info("[MobileUI] Set text completed for locator: " + locator);
    }

    @Step("Set text '{1}' on element {0}")
    public static void setText(WebElement element, String text) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Setting text '" + text + "' on element: " + element + " with default timeout.");
        WebElement elm = waitForElementVisible(element);
        elm.click();
        elm.clear();
        elm.sendKeys(text);
        LogUtils.info("[MobileUI] Set text completed for element: " + element);

    }

    @Step("Set text '{1}' on element {0} within {2}s")
    public static void setText(WebElement element, String text, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Setting text '" + text + "' on element: " + element + " with timeout " + second + "s.");
        WebElement elm = waitForElementVisible(element, second);
        elm.click();
        elm.clear();
        elm.sendKeys(text);
        LogUtils.info("[MobileUI] Set text completed for element: " + element);
    }

    @Step("Clear text on element {0}")
    public static void clearText(By locator) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Clearing text on element located by: " + locator + " with default timeout.");
        WebElement element = waitForElementVisible(locator);
        element.click();
        element.clear();
        LogUtils.info("[MobileUI] Clear text completed for locator: " + locator);
    }

    @Step("Clear text on element {0} within {1}s")
    public static void clearText(By locator, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Clearing text on element located by: " + locator + " with timeout " + second + "s.");
        WebElement element = waitForElementVisible(locator, second);
        element.click();
        element.clear();
        LogUtils.info("[MobileUI] Clear text completed for locator: " + locator);
    }

    @Step("Clear text on element {0}")
    public static void clearText(WebElement element) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Clearing text on element: " + element + " with default timeout.");
        WebElement elm = waitForElementVisible(element);
        elm.click();
        elm.clear();
        LogUtils.info("[MobileUI] Clear text completed for element: " + element);
    }

    @Step("Clear text on element {0} within {1}s")
    public static void clearText(WebElement element, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Clearing text on element: " + element + " with timeout " + second + "s.");
        WebElement elm = waitForElementVisible(element, second);
        elm.click();
        elm.clear();
        LogUtils.info("[MobileUI] Clear text completed for element: " + element);
    }

    @Step("Get text from element {0}")
    public static String getElementText(By locator) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Getting text from element located by: " + locator + " with default timeout.");
        WebElement element = waitForElementVisible(locator);
        String text = element.getText();
        LogUtils.info("[MobileUI] Retrieved text: '" + text + "'");
        AllureManager.saveTextLog("➡\uFE0F TEXT: " + text);
        return text;
    }

    @Step("Get text from element {0} within {1}s")
    public static String getElementText(By locator, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Getting text from element located by: " + locator + " with timeout " + second + "s.");
        WebElement element = waitForElementVisible(locator, second);
        String text = element.getText();
        LogUtils.info("[MobileUI] Retrieved text: '" + text + "'");
        AllureManager.saveTextLog("➡\uFE0F TEXT: " + text);
        return text;
    }

    public static String getElementText(WebElement element) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Getting text from element: " + element + " with default timeout.");
        WebElement elm = waitForElementVisible(element);
        String text = elm.getText();
        LogUtils.info("[MobileUI] Retrieved text: '" + text + "'");
        return text;
    }

    public static String getElementText(WebElement element, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Getting text from element: " + element + " with timeout " + second + "s.");
        WebElement elm = waitForElementVisible(element, second);
        String text = elm.getText();
        LogUtils.info("[MobileUI] Retrieved text: '" + text + "'");
        return text;
    }

    public static String getElementAttribute(By locator, String attribute) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Getting attribute '" + attribute + "' from element located by: " + locator + " with default timeout.");
        WebElement element = waitForElementVisible(locator);
        String value = element.getAttribute(attribute);
        LogUtils.info("[MobileUI] Retrieved attribute value: '" + value + "'");
        return value;
    }

    public static String getElementAttribute(By locator, String attribute, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Getting attribute '" + attribute + "' from element located by: " + locator + " with timeout " + second + "s.");
        WebElement element = waitForElementVisible(locator, second);
        String value = element.getAttribute(attribute);
        LogUtils.info("[MobileUI] Retrieved attribute value: '" + value + "'");
        return value;
    }

    public static String getElementAttribute(WebElement element, String attribute) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Getting attribute '" + attribute + "' from element: " + element + " with default timeout.");
        WebElement elm = waitForElementVisible(element);
        String value = elm.getAttribute(attribute);
        LogUtils.info("[MobileUI] Retrieved attribute value: '" + value + "'");
        return value;
    }

    public static String getElementAttribute(WebElement element, String attribute, int second) {
        sleep(STEP_ACTION_TIMEOUT);
        LogUtils.info("[MobileUI] Getting attribute '" + attribute + "' from element: " + element + " with timeout " + second + "s.");
        WebElement elm = waitForElementVisible(element, second);
        String value = elm.getAttribute(attribute);
        LogUtils.info("[MobileUI] Retrieved attribute value: '" + value + "'");
        return value;
    }

    public static boolean isElementPresentAndDisplayed(WebElement element) {
        LogUtils.info("[MobileUI] Checking if element is present and displayed: " + element);
        boolean result;
        try {
            result = element != null && element.isDisplayed();
            LogUtils.info("[MobileUI] Element present and displayed check result: " + result);
            return result;
        } catch (NoSuchElementException e) {
            LogUtils.info("[MobileUI] Element not found during presence/display check: " + element + " - " + e.getMessage());
            return false;
        } catch (Exception e) {
            LogUtils.info("[MobileUI] An error occurred checking presence/display for element: " + element + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementPresentAndDisplayed(By locator) {
        LogUtils.info("[MobileUI] Checking if element is present and displayed: " + locator);
        boolean result;
        try {
            WebElement element = getDriver().findElement(locator); // Find first, then check display
            result = element != null && element.isDisplayed();
            LogUtils.info("[MobileUI] Element present and displayed check result: " + result + " for locator: " + locator);
            return result;
        } catch (NoSuchElementException e) {
            LogUtils.info("[MobileUI] Element not found during presence/display check: " + locator + " - " + e.getMessage());
            return false;
        } catch (Exception e) {
            LogUtils.info("[MobileUI] An error occurred checking presence/display for locator: " + locator + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementEnabled(WebElement element) {
        LogUtils.info("[MobileUI] Checking if element is enabled: " + element);
        boolean result;
        try {
            result = element != null && element.isEnabled();
            LogUtils.info("[MobileUI] Element enabled check result: " + result);
            return result;
        } catch (Exception e) {
            LogUtils.info("[MobileUI] An error occurred checking enabled status for element: " + element + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementEnabled(By locator) {
        LogUtils.info("[MobileUI] Checking if element is enabled: " + locator);
        boolean result;
        try {
            WebElement element = waitForElementVisible(locator); // Ensure it's visible before checking enabled
            result = element != null && element.isEnabled();
            LogUtils.info("[MobileUI] Element enabled check result: " + result + " for locator: " + locator);
            return result;
        } catch (Exception e) {
            LogUtils.info("[MobileUI] An error occurred checking enabled status for locator: " + locator + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementSelected(WebElement element) {
        LogUtils.info("[MobileUI] Checking if element is selected: " + element);
        boolean result;
        try {
            result = element != null && element.isSelected();
            LogUtils.info("[MobileUI] Element selected check result: " + result);
            return result;
        } catch (Exception e) {
            LogUtils.info("[MobileUI] An error occurred checking selected status for element: " + element + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementSelected(By locator) {
        LogUtils.info("[MobileUI] Checking if element is selected: " + locator);
        boolean result;
        try {
            WebElement element = waitForElementVisible(locator); // Ensure it's visible before checking selected
            result = element != null && element.isSelected();
            LogUtils.info("[MobileUI] Element selected check result: " + result + " for locator: " + locator);
            return result;
        } catch (Exception e) {
            LogUtils.info("[MobileUI] An error occurred checking selected status for locator: " + locator + " - " + e.getMessage());
            return false;
        }
    }


    // Các hàm verify (sử dụng Assert và gọi lại các hàm is)

    public static void verifyElementPresentAndDisplayed(WebElement element, String message) {
        LogUtils.info("[MobileUI] Verifying element is present and displayed: " + element + ". Message if failed: " + message);
        Assert.assertTrue(isElementPresentAndDisplayed(element), message);
    }

    public static void verifyElementPresentAndDisplayed(By locator, String message) {
        LogUtils.info("[MobileUI] Verifying element is present and displayed: " + locator + ". Message if failed: " + message);
        Assert.assertTrue(isElementPresentAndDisplayed(locator), message);
    }

    public static void verifyElementEnabled(WebElement element, String message) {
        LogUtils.info("[MobileUI] Verifying element is enabled: " + element + ". Message if failed: " + message);
        Assert.assertTrue(isElementEnabled(element), message);
    }

    public static void verifyElementEnabled(By locator, String message) {
        LogUtils.info("[MobileUI] Verifying element is enabled: " + locator + ". Message if failed: " + message);
        Assert.assertTrue(isElementEnabled(locator), message);
    }

    public static void verifyElementSelected(WebElement element, String message) {
        LogUtils.info("[MobileUI] Verifying element is selected: " + element + ". Message if failed: " + message);
        Assert.assertTrue(isElementSelected(element), message);
    }

    public static void verifyElementSelected(By locator, String message) {
        LogUtils.info("[MobileUI] Verifying element is selected: " + locator + ". Message if failed: " + message);
        Assert.assertTrue(isElementSelected(locator), message);
    }

    public static void verifyElementText(WebElement element, String expectedText, String message) {
        LogUtils.info("[MobileUI] Verifying text of element: " + element + " equals '" + expectedText + "'. Message if failed: " + message);
        Assert.assertEquals(getElementText(element), expectedText, message);
    }

    public static void verifyElementText(By locator, String expectedText, String message) {
        LogUtils.info("[MobileUI] Verifying text of element: " + locator + " equals '" + expectedText + "'. Message if failed: " + message);
        Assert.assertEquals(getElementText(locator), expectedText, message);
    }

    public static void verifyElementAttribute(WebElement element, String attribute, String expectedValue, String message) {
        LogUtils.info("[MobileUI] Verifying attribute '" + attribute + "' of element: " + element + " equals '" + expectedValue + "'. Message if failed: " + message);
        Assert.assertEquals(getElementAttribute(element, attribute), expectedValue, message);
    }

    public static void verifyElementAttribute(By locator, String attribute, String expectedValue, String message) {
        LogUtils.info("[MobileUI] Verifying attribute '" + attribute + "' of element: " + locator + " equals '" + expectedValue + "'. Message if failed: " + message);
        Assert.assertEquals(getElementAttribute(locator, attribute), expectedValue, message);
    }

    public static void assertTrueCondition(boolean condition, String message) {
        LogUtils.info("[MobileUI] Asserting condition: " + condition + ". Message if failed: " + message);
        Assert.assertTrue(condition, message);
        LogUtils.info("[MobileUI] Assertion passed for condition: " + condition);
    }


    // --- Wait Methods ---

    public static WebElement waitForElementToBeClickable(By locator, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for element to be clickable: " + locator);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementToBeClickable(By locator) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be clickable: " + locator);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementToBeClickable(WebElement element, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for element to be clickable: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementToBeClickable(WebElement element) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be clickable: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementVisible(By locator, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for element to be visible: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementVisible(By locator) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be visible: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementVisible(WebElement element, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for element to be visible: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForElementVisible(WebElement element) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be visible: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static boolean waitForElementInvisible(By locator, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for element to be invisible: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForElementInvisible(By locator) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be invisible: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForElementInvisible(WebElement element, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for element to be invisible: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static boolean waitForElementInvisible(WebElement element) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be invisible: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static WebElement waitForElementPresent(By locator, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for element to be present in DOM: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForElementPresent(By locator) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for element to be present in DOM: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean waitForTextToBePresent(By locator, String text, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for text '" + text + "' to be present in element: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean waitForTextToBePresent(By locator, String text) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for text '" + text + "' to be present in element: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean waitForTextToBePresent(WebElement element, String text, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for text '" + text + "' to be present in element: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static boolean waitForTextToBePresent(WebElement element, String text) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for text '" + text + "' to be present in element: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static boolean waitForAttributeToBe(By locator, String attribute, String value, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for attribute '" + attribute + "' to be '" + value + "' in element: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.attributeToBe(locator, attribute, value));
    }

    public static boolean waitForAttributeToBe(By locator, String attribute, String value) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for attribute '" + attribute + "' to be '" + value + "' in element: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.attributeToBe(locator, attribute, value));
    }

    public static boolean waitForAttributeToBe(WebElement element, String attribute, String value, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for attribute '" + attribute + "' to be '" + value + "' in element: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public static boolean waitForAttributeToBe(WebElement element, String attribute, String value) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for attribute '" + attribute + "' to be '" + value + "' in element: " + element);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public static List<WebElement> waitForNumberOfElements(By locator, int expectedCount, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for number of elements to be " + expectedCount + " for locator: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.numberOfElementsToBe(locator, expectedCount));
    }

    public static List<WebElement> waitForNumberOfElements(By locator, int expectedCount) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for number of elements to be " + expectedCount + " for locator: " + locator);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.numberOfElementsToBe(locator, expectedCount));
    }

    public static boolean waitForUrlContains(String text, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for URL to contain: '" + text + "'");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.urlContains(text));
    }

    public static boolean waitForUrlContains(String text) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for URL to contain: '" + text + "'");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.urlContains(text));
    }

    public static boolean waitForNumberOfWindows(int expectedWindows, int timeout) {
        LogUtils.info("[MobileUI] Waiting up to " + timeout + "s for number of windows to be: " + expectedWindows);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindows));
    }

    public static boolean waitForNumberOfWindows(int expectedWindows) {
        LogUtils.info("[MobileUI] Waiting up to " + DEFAULT_TIMEOUT + "s (default) for number of windows to be: " + expectedWindows);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindows));
    }

}