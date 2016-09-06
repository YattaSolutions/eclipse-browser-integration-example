# Sample Code for an Eclipse-Browser Integration

This project contains the Source Code for the JAXenter Article at <link>. It gives you an idea how to integrate a web application seamlessly into an Eclipse IDE using the Eclipse Browser component. The communication process between a Plug-In (Java) and the embedded Browser (JavaScript) is demonstrated by an embedded Wiki which allows direct navigation from the website to Java Source code in the developer's workspace.

### Test it with Yatta Profiles for Eclipse

With the Yatta Launcher for Eclipse you can download a ready-to-use Eclipse setup at .... You only need a few clicks to get started. After the Yatta Launcher has configured your newly installed Eclipse, use 'Run as &rarr; Eclipse Application' from the context-menu of the _de.yatta.demo.jaxenter_ project to start your demo Eclipse Runtime.

##### Alternative: Import directly into Eclipse workspace

To test this example, import the project _de.yatta.demo.jaxenter_ into your workspace. Ensure you have the Eclipse Tools for Plug-In Development installed. You can use the Eclipse IDE for Eclipse Committers or install the plug-in from the [Eclipse Marketplace]( https://marketplace.eclipse.org/content/eclipse-pde-plug-development-environment). Run the project as an Eclipse Application (Run as &rarr; Eclipse Application).

Hint: Be aware that for the demo-runtime of the embedded Wiki view, the clicked classes have to exist in your workspace in order to get opened.