package testFramework;

import annotations.After;
import annotations.Before;
import annotations.Test;

public class MyTestClass {

    @Before
    public void setUp() {
        System.out.println("Before each test");
    }

    @Test
    public void testSuccess() {
        System.out.println("Running successful test");
    }

    @Test
    public void testFailure() {
        System.out.println("Running failing test");
        throw new RuntimeException("Test failed");
    }

    @After
    public void after() {
        System.out.println("After each test");
    }
}
