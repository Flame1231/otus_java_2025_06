package testFramework;

import annotations.After;
import annotations.Before;
import annotations.Test;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static void runTests(String className) {
        int total = 0;
        int passed = 0;
        int failed = 0;

        try {
            Class<?> clazz = Class.forName(className);

            List<Method> beforeMethods = getAnnotatedMethods(clazz, Before.class);
            List<Method> testMethods = getAnnotatedMethods(clazz, Test.class);
            List<Method> afterMethods = getAnnotatedMethods(clazz, After.class);

            for (Method testMethod : testMethods) {
                total++;
                Object testInstance = clazz.getDeclaredConstructor().newInstance();
                try {
                    invokeAll(beforeMethods, testInstance);
                    testMethod.invoke(testInstance);
                    passed++;
                } catch (Exception e) {
                    failed++;
                    System.out.println("Test " + testMethod.getName() + " failed: " + e.getCause());
                } finally {
                    try {
                        invokeAll(afterMethods, testInstance);
                    } catch (Exception e) {
                        System.out.println("Error in @After methods: " + e.getCause());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to run tests: " + e.getMessage());
            return;
        }
        printSummary(total, passed, failed);
    }

    private static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                method.setAccessible(true);
                methods.add(method);
            }
        }
        return methods;
    }

    private static void invokeAll(List<Method> methods, Object instance) throws Exception {
        for (Method method : methods) {
            method.invoke(instance);
        }
    }

    private static void printSummary(int total, int passed, int failed) {
        System.out.println("Test Results");
        System.out.println("Total: " + total);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }
}
