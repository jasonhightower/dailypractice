package com.hightower.dailypractice.core;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class NotImplementedCondition implements ExecutionCondition {

    private static final ConditionEvaluationResult ENABLED = ConditionEvaluationResult.enabled("@NotImplemented is not present in class under test");

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(final ExtensionContext context) {
        Class<?> testClass = context.getRequiredTestClass();
        try {
            Class<?> classUnderTest = testClass.getDeclaredField("classUnderTest").getType();
            for (Constructor c: classUnderTest.getDeclaredConstructors()) {
                if (c.isAnnotationPresent(NotImplemented.class)) {
                    return ConditionEvaluationResult.disabled(String.format("%s has constructor marked as NotImplemented", classUnderTest.getSimpleName()));
                }
            }
            Method[] methods = classUnderTest.getMethods();
            for (Method method: methods) {
                if (method.isAnnotationPresent(NotImplemented.class)) {
                    return ConditionEvaluationResult.disabled(String.format("%s.%s is not implemented", classUnderTest.getSimpleName(), method.getName()));
                }
            }
        } catch (NoSuchFieldException e) {
            return ConditionEvaluationResult.disabled(String.format("%s does not have a `classUnderTest` field", testClass.getSimpleName()));
        }
        return ENABLED;
    }
}
