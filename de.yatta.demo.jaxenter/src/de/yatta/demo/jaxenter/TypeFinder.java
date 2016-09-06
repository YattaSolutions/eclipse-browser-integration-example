package de.yatta.demo.jaxenter;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;

/**
 * Allows to open a Java interface or class by it's fully quallified name. Uses
 * the JDT Search API to allocate the classes/interfaces and the {@link JavaUI}
 * utilities to open them in an editor.
 */
public class TypeFinder extends SearchRequestor {

	private static TypeFinder instance;

	private TypeFinder() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.core.search.SearchRequestor#acceptSearchMatch(org.eclipse
	 * .jdt.core.search.SearchMatch)
	 */
	@Override
	public void acceptSearchMatch(final SearchMatch match) throws CoreException {
		if (match.getElement() instanceof IJavaElement) {
			final IJavaElement javaElement = (IJavaElement) match.getElement();
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						JavaUI.openInEditor(javaElement, true, true);
					} catch (PartInitException | JavaModelException e) {
						String message = MessageFormat.format("Could not open IJavaElement ''{0}'' in editor.",
								javaElement.getElementName());
						Log.log(IStatus.ERROR, message, e);
					}
				}
			});
		}
	}

	/**
	 * Can be used to open a class or an interface inside a Java-editor by it's
	 * fully qualified name.
	 * 
	 * @param fullyQualifiedTypeName
	 *            the name of the type to open
	 * @throws CoreException
	 */
	public static void openType(final String fullyQualifiedTypeName) throws CoreException {
		if (instance == null) {
			instance = new TypeFinder();
		}

		new Job("Searching for Wiki type to open ... ") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// prepare the search metadata
				SearchPattern searchPattern = SearchPattern.createPattern(fullyQualifiedTypeName,
						IJavaSearchConstants.CLASS_AND_INTERFACE, IJavaSearchConstants.DECLARATIONS,
						SearchPattern.R_EXACT_MATCH);
				IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
				SearchEngine searchEngine = new SearchEngine();
				try {
					SearchParticipant[] searchParticipants = new SearchParticipant[] {
							SearchEngine.getDefaultSearchParticipant() };
					// perform the search
					searchEngine.search(searchPattern, searchParticipants, scope, instance,
							SubMonitor.convert(monitor));
				} catch (CoreException e) {
					String message = MessageFormat.format("A problem occured while trying to find the type {0}",
							fullyQualifiedTypeName);
					return new Status(IStatus.ERROR, Log.BUNDLE_NAME, message, e);
				}
				return Status.OK_STATUS;
			}
		}.schedule();
	}

}
