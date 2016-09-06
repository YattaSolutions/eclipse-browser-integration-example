package de.yatta.demo.jaxenter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * A view which contains nothing but an embedded browser.
 */
public class WikiView extends ViewPart {

	private static final String URL_WIKI_EXAMPLEFILE = "platform:/plugin/de.yatta.demo.jaxenter/testWikiView.html";
	private static final String URL_YATTA_LOGO = "platform:/plugin/de.yatta.demo.jaxenter/yatta-solutions-logo.png";

	public static final String VIEW_NAME = "de.yatta.demo.jaxenter.wikiview";

	/**
	 * The initialization of the embedded Browser view takes place here.
	 */
	@Override
	public void createPartControl(Composite parent) {

		try {
			Browser browser = new Browser(parent, SWT.NONE);

			Path tempFile = extractResources();

			browser.setUrl(tempFile.toString());
			browser.addProgressListener(new ProgressAdapter() {
				@Override
				public void completed(ProgressEvent event) {
					new CandidateClickedFunction(browser);
					browser.execute(loadScript("getClickedText.js"));
				}
			});
		} catch (IOException e) {
			Log.log(IStatus.ERROR, "Problem occured extracting the demo HTML file from the Eclipse bundle.", e);
		}

	}

	/**
	 * Extracts all resources which are needed to show the Wiki demo inside the
	 * embedded Browser component. This resources are only needed for demo
	 * purposes while the integration of a real Wiki would be done by setting
	 * the URL of the Browser to a Wiki's http(s) address.
	 * 
	 * @return the {@link Path} of the HTML file which will be set as the
	 *         Browser's URL
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	private Path extractResources() throws IOException, MalformedURLException {
		String tempDir = System.getProperty("java.io.tmpdir");

		Path htmlTempFile = Paths.get(tempDir, "testWikiViewJAXenterDemo.html");
		Files.copy(new URL(URL_WIKI_EXAMPLEFILE).openStream(), htmlTempFile, StandardCopyOption.REPLACE_EXISTING);

		Path logoTempFile = Paths.get(tempDir, "yatta-solutions-logo-demo-jaxenter.png");
		Files.copy(new URL(URL_YATTA_LOGO).openStream(), logoTempFile, StandardCopyOption.REPLACE_EXISTING);

		return htmlTempFile;
	}

	/**
	 * Reads a file from the de.yatta.demo.jaxenter bundle into a String.
	 * 
	 * @param filepath
	 *            the path to the file to read, relative to the bundle-root
	 * @return
	 */
	private String loadScript(String filepath) {
		String content = "";
		try {
			URL url = new URL("platform:/plugin/de.yatta.demo.jaxenter/" + filepath);
			try (Scanner scanner = new Scanner(url.openConnection().getInputStream())) {
				scanner.useDelimiter("\\Z+");
				content += scanner.next();
			}
		} catch (IOException e) {
			Log.log(IStatus.ERROR, "Problem occured while loading Javascript script from bundle resource.", e);
		}
		return content;
	}

	@Override
	public void setFocus() {
		// no-op
	}

}
