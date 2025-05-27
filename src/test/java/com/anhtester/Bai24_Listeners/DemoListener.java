package com.anhtester.Bai24_Listeners;

import com.anhtester.common.BaseTest;
import com.anhtester.listeners.TestListener;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class DemoListener extends BaseTest {

    @Test
    public void test1() {
        System.out.println("Test 1 running...");
    }

    @Test
    public void test2() {
        System.out.println("Test 2 running...");
        throw new RuntimeException("Test 2 failed");
    }

    @Test
    public void test3() {
        System.out.println("Test 3 running...");
        throw new SkipException("Test 3 skipped");
    }

}
