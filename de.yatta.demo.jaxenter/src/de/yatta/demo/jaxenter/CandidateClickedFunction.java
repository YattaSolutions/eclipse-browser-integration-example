package de.yatta.demo.jaxenter;

import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

/**
 * Registers a global JavaScript function named
 * 'java__callback__candidateClicked' for the Browser-component passed to the
 * constructor.
 */
public class CandidateClickedFunction extends BrowserFunction {

	private static final String CANDIDATE_CLICKED_JS_NAME = "java__callback__candidateClicked";

	public CandidateClickedFunction(Browser browser) {
		super(browser, CANDIDATE_CLICKED_JS_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.browser.BrowserFunction#function(java.lang.Object[])
	 */
	@Override
	public Object function(Object[] arguments) {

		if (arguments == null || arguments.length != 1) {
			String message = MessageFormat.format(
					"BrowserFunction was invoked with invalid arguments. Did expect one String-typed argument but instead found: {0}",
					Arrays.toString(arguments));
			Log.log(IStatus.ERROR, message);
		}

		try {
			TypeFinder.openType(arguments[0].toString());
		} catch (CoreException e) {
			Log.log(IStatus.ERROR, "Problem occured while trying to open type from Wiki.", e);
		}

		return null;
	}

}