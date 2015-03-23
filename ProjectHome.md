**Vote for [SwingX Issue 306](https://swingx.dev.java.net/issues/show_bug.cgi?id=306) if you want xswingx to be integrated into SwingX**


---



xswingx can help you...
  * Add **prompts** to any text component.
  * Add child components to any text field. (Called **buddies**. Wonder why? Read the [FAQ](FAQ.md).)
  * Add a **search field** to your UI (and let it look and behave like native - or not).

View GettingStarted to get started or try the [WebStart Demo](http://stud4.tuwien.ac.at/~e0304333/xswingx2/launch.jnlp) to get an idea of what this is all about.

Want to know how it works and why you should use it? Read the [FAQ](FAQ.md).

### Highlights ###
  * Super easy to use - clean, lightweight design; unit tested and documented
  * Real beans - use with your favorite GUI editor

**Prompt Support**
  * Customize colors, fonts and focus behavior (hide, highlight, or show the prompt when focused)
  * Everything's in the UI - don't worry about unwanted DocumentEvent's
  * Works with every JTextComponent - JTextField, JTextArea, JFormattedTextField

![http://xswingx.googlecode.com/svn/wiki/images/textfield_mac.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_mac.png)
![http://xswingx.googlecode.com/svn/wiki/images/textfield_vista.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_vista.png)
![http://xswingx.googlecode.com/svn/wiki/images/textfield_metal.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_metal.png)

**Buddy Support****<sup>new</sup>**
  * Add any component to the left or right side of any JTextField.

**Search Field****<sup>improved</sup>**
  * Looks like native - automatically takes the style appropriate for your current look and feel.
  * Easy search - just register an ActionListener.
  * Instant or Regular search - search while typing, when pressing enter, or clicking the search button.
  * Delayed search (while typing) - don't search after each keystroke.
  * Set a popup menu - and let the search field worry about the rest.
  * Built in recent searches popup menu.**<sup>improved</sup>**
  * Customizable - change icons, margins, layout style,... **<sup>improved</sup>** (because now based on the new buddy support.)
  * Native search field support on Mac OS X 10.5.**<sup>new</sup>**

![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac.png)
![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_popup.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_popup.png)

![http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista.png)
![http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista_popup.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista_popup.png)

![http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal.png)
![http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal_popup.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal_popup.png)

![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_native.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_native.png)
![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_native_searching.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_native_searching.png)

### Quick Start ###
To add a prompt to your existing text component simply call
```
PromptSupport.setPrompt("Prompt", textComponent);
```
To add a child component to your existing text component you can use
```
BuddySupport.addLeft(myBuddy, textComponent);
```

You can also use the `JXTextField`, `JXTextArea`, or `JXFormattedTextField` classes with your favorite GUI editor.

Use the `JXSearchField` class (not `JSearchField`) to create a search field which automatically takes the style appropriate for your current Look and Feel.

[Learn more](GettingStarted.md)