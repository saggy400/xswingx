### What is a 'prompt' and how should it be used? ###

See http://designinginterfaces.com/Input_Prompt for a description of the **Input Prompt** pattern or http://msdn2.microsoft.com/en-us/library/aa511494.aspx#prompts for guidelines of how to use prompts in Windows Vista.

### What does 'buddy' mean in terms of child components? ###
Because the child is the buddy of the text field. Anyway, I believe I read about it somewhere in an older version of the Vista UX Guidelines (_...make it the buddy of the text box..._), but can't find it anymore. If you know a better name, please let me know.

### Why should I use the prompt support provided by xswingx? I could easily write my own prompt support. ###

Well, that sounds pretty complicated, doesn't it? But it could be so easy: Just download [prompt.jar](http://xswingx.googlecode.com/files/prompt.jar) (<10kB), add it to your classpath and call:
```
PromptSupport.setPrompt("My Prompt", myTextComponent);
```
Writing your own "prompt support" certainly requires both, more code and effort.

The prompt support is also very flexible. You can change the **focus behavior** (hide prompt, show prompt or highlight prompt), the prompt's **color** and even the **font style** (Italic, like it the Windows Vista search box, if you like). Of course you can change the prompt text too.

Most importantly, the prompt support doesn't modify your text component's `Document`. Therefore no unwanted `DocumentEvent`s are fired and: `getText` always returns the real text - not the prompt!

### So prompt support doesn't modify the text component's Document. How does it work, then? ###

`PromptSupport` uses `JComponent`s [client properties](http://java.sun.com/j2se/1.4.2/docs/api/javax/swing/JComponent.html#putClientProperty(java.lang.Object,%20java.lang.Object)) to get and set it's properties.
Furthermore, the text component's UI delegate is wrapped by a custom delegate that reads these properties and renders the prompt accordingly.
Read the code to learn more - it's even well documented!