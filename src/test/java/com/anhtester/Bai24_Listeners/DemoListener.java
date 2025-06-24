package com.anhtester.Bai24_Listeners;

import org.testng.SkipException;
import org.testng.annotations.Test;

//@Listeners({TestListener.class})
public class DemoListener {

    @Test
    public void test1() {
        System.out.println("Test 1 running...");
    }

    @Test
    public void test2() {
        System.out.println("Test 2 running...");
        throw new RuntimeException("Sai vì tui cố ý");
    }

    @Test
    public void test3() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Test 3 running...");
        throw new SkipException("Test 3 skipped");
    }

}
