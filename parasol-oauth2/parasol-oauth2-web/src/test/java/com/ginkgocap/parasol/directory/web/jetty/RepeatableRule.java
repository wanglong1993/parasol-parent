package com.ginkgocap.parasol.directory.web.jetty;

import java.util.Arrays;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RepeatableRule implements MethodRule {
	// Loop times
	int times = 1;
	// Loop methods
	String[] testMethods = null;

	public RepeatableRule(int times, String[] testMethods) {
		this.times = times;
		this.testMethods = testMethods;
	}

	@Override
	public Statement apply(final Statement base, final FrameworkMethod method, Object target) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				int loopTime = 1;
				if(Arrays.asList(testMethods).contains(method.getName())) {
					loopTime = times;
				}
				for (int i = 0; i < loopTime; i++) {
					base.evaluate();
				}
			}
		};
	}

}
