package de.yatta.demo.jaxenter;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

public class Log {

	public static final String BUNDLE_NAME = "de.yatta.demo.jaxenter";

	public static void log(int status, String message) {
		Platform.getLog(Platform.getBundle(BUNDLE_NAME)).log(new Status(status, BUNDLE_NAME, message));
	}

	public static void log(int status, String message, Throwable e) {
		Platform.getLog(Platform.getBundle(BUNDLE_NAME)).log(new Status(status, BUNDLE_NAME, message, e));
	}

}
