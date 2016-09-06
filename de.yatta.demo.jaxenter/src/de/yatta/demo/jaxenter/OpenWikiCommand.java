package de.yatta.demo.jaxenter;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class OpenWikiCommand extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView("de.yatta.demo.jaxenter.wikiview");
		} catch (PartInitException e) {
			Log.log(IStatus.ERROR, "Could not open the Wiki view", e);
		}
		return null;
	}

}
