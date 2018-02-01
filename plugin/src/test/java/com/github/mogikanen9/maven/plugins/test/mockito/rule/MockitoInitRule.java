package com.github.mogikanen9.maven.plugins.test.mockito.rule;

import static org.mockito.MockitoAnnotations.initMocks;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class MockitoInitRule extends TestWatcher {

	private Object reference;
	private boolean mockInitialized = false;

	public MockitoInitRule(Object reference) {
		super();
		this.reference = reference;
	}

	@Override
	protected void starting(Description d) {
		if (!mockInitialized) {
			initMocks(reference);
			mockInitialized = true;
		}
	}
}