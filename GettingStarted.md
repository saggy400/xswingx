# Get the Library #
Download [xswingx](http://xswingx.googlecode.com/files/xswingx-0.2.zip) from the _Downloads_ section if you want to use all xswingx features, or download [prompt.jar](http://xswingx.googlecode.com/files/prompt.jar), if you just want to add a prompt to your existing text components.

# Add a Prompt to Your Existing Text Component #
Just invoke
```
PromptSupport.setPrompt("Prompt Text", textComponent);
```
Where `textComponent` is an instance of `JTextComponent`.

After that, `textComponent`, if it is a `JTextField`, should look like this:

| Metal | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_metal.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_metal.png) |
|:------|:----------------------------------------------------------------------------------------------------------------------------------------|
| Windows XP | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp.png) |
| Vista | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_vista.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_vista.png) |
| Mac | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_mac.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_mac.png) |
| Motif | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_motif.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_motif.png) |
| JGoodies Plastic XP | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_plasticxp.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_plasticxp.png) |

# Customize the Prompt #
## Focus Behavior ##
By default, the prompt text will be hidden, when the text component is focused:
![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_focused_hidden.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_focused_hidden.png)

This behavior can be customized with the `PromptSupport.setFocusBehavior()` method. The prompt text can be hidden, shown, or highlighted when the text component gets the focus (and no text is entered), as indicated in the screenshots below.

| **FocusBehavior.HIDE\_PROMPT** | **FocusBehavior.SHOW\_PROMPT** | **FocusBehavior.HIGHLIGHT\_PROMPT** |
|:-------------------------------|:-------------------------------|:------------------------------------|
| ![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_focused_hidden.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_focused_hidden.png) | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_focused_shown.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_focused_shown.png) | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_focused_highlighted.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_focused_highlighted.png) |

## Font Style and Color ##
The font of the prompt text by default is the same as the text component's font, but sometimes you want the prompt text to be different. You can make the prompt text plain, **bold**, _italic_, or both **_bold and italic_** with the `PromptSupport.setFontStyle()` method.

| **Plain** | **Italic** | **Bold** | **Bold and Italic** |
|:----------|:-----------|:---------|:--------------------|
| ![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp.png) | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_italic.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_italic.png) | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_bold.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_bold.png) | ![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_bolditalic.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_bolditalic.png) |

By default, the color used for the prompt text is the text component's disabled text color, but yout can change this with the `PromptSupport.setForeground()` method.
`PromptSupport.setForeground(Color.RED)` gives you: ![http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_red.png](http://xswingx.googlecode.com/svn/wiki/images/textfield_xp_red.png)

You can also set a background color for the promp by calling `PromptSupport.setBackground()`.

# Use the Extended Text Components #
If you are creating a new form for your application, you probably want to use a graphical editor like _JFormDesigner_ or _Matisse_ to change you text components properties. In this case it is handy to use one of the prebuilt text components, which support prompt customization via JavaBean properties: [JXTextField](http://xswingx.googlecode.com/svn/trunk/xswingx/src/java/org/jdesktop/xswingx/JXTextField.java), [JXTextArea](http://xswingx.googlecode.com/svn/trunk/xswingx/src/java/org/jdesktop/xswingx/JXTextArea.java), or [JXFormattedTextField](http://xswingx.googlecode.com/svn/trunk/xswingx/src/java/org/jdesktop/xswingx/JXFormattedTextField.java). These classes simply extend JTextField, JTextArea, or JFormattedTextField respectively, and add properties supported by [PromptSupport](http://xswingx.googlecode.com/svn/trunk/xswingx/src/java/org/jdesktop/xswingx/PromptSupport.java).

Note: You need the whole xswingx library to use these components.

# Add a Buddy to a Text Field #
Use
```
BuddySupport.addLeft(buddy, textField); 
//or
BuddySupport.addRight(buddy, textField); 
```

# Use the Search Field #
It's easy to create a search field:
```
JXSearchField searchField = new JXSearchField();
```
What you get is this:
| **LnF** | **Default** | **With Search Text** |
|:--------|:------------|:---------------------|
| Metal | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal.png) | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal_searching.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal_searching.png) |
| Motif | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_motif.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_motif.png) | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_motif_searching.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_motif_searching.png) |
| Windows XP | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_xp.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_xp.png) | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_xp_searching.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_xp_searching.png) |
| Vista | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista.png) | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista_searching.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista_searching.png) |
| Mac | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac.png) | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_searching.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_searching.png) |
| Mac Native (10.5 required) | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_native.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_native.png) | ![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_native_searching.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_native_searching.png) |

Notice that the search field _automatically_ takes the style of the current Look and Feel.
Everything is still customizable, of course. You can make the search field more _Vista_ like in any LnF by calling
```
searchField.setLayoutStyle(LayoutStyle.VISTA);
```
or more _Mac_ like (even in Windows) by calling
```
searchField.setLayoutStyle(LayoutStyle.MAC);
```

Also, notice the _Italic_ prompt in the Windows search field, compared to the Plain font in the other LnF's. You can of course change this too, like in any text component with prompt support. Read about the prompt support above to learn how.

## Searching ##
The preferred way to perform a search is to register an `ActionListener`:
```
searchField.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
        //search now
    }
});
```

The search field supports 2 search modes:

**Instant (default)
```
searchField.setSearchMode(SearchMode.INSTANT);
```
An action event is fired when the text changes.
In _Instant_ search mode you can set a search delay:
```
searchField.setInstantSearchDelay(100); //milliseconds. default: 180
```**

**Regular
```
searchField.setSearchMode(SearchMode.REGULAR);
```
An action event is fired when the _notify_ action is invoked (typically that is, when the _Enter_ key is pressed) and when the search button is pressed.**


## Adding a Popup Menu ##
Set a find popup menu by calling:
```
searchField.setFindPopupMenu(popupMenu);
```

To add a Mac style recent searches popup to your search field, simply call:
```
searchField.setRecentSearchesSaveKey("someUniqueKey");
```
The list of recent searches will automatically be persisted using the [Java Preferences API](http://java.sun.com/j2se/1.4.2/docs/guide/lang/preferences.html) under the given key.

The search field will automatically change its icon (the Windows LnF even uses a seperate popup button) and display the popup menu appropriately:
|**Metal**|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal_popup.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal_popup.png)|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal_popup_clicked.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_metal_popup_clicked.png)|
|:--------|:------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|**Motif**|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_motif_popup.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_motif_popup.png)|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_motif_popup_clicked.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_motif_popup_clicked.png)|
|**Windows XP**|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_xp_popup.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_xp_popup.png)|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_xp_popup_clicked.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_xp_popup_clicked.png)|
|**Vista**|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista_popup.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista_popup.png)|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista_popup_clicked.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_vista_popup_clicked.png)|
|**Mac**|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_popup.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_popup.png)|![http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_popup_clicked.png](http://xswingx.googlecode.com/svn/wiki/images/searchfield_mac_popup_clicked.png)|


