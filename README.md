# ANDIE - A Non-Destructive Image Editor

Welcome to ANDIE! 

As the title above suggests, ANDIE is an image editor that allows for reversable changes. 
This means that you can make changes to the size, colour or apply filters to an image of your choice, while also being able to undo those changes.

# User Guide:

To get started with ANDIE, compile and run the Andie.java file.
Alternatively, run the Andie.jar program.

Once running, navigate to the menu item *'File > Open'* to search for the image that you wish to edit.

Now that you have selected an image opened and ready to edit, try making use of the many features available at the top of the window.

## File menu
    - Has menus that allow you to open, save and export files.

## Edit menu
    - Has options to undo and redo changes that you've made to an image.
    - Also contains the feature to record operations, so you can easily apply them across multiple images.

## View menu
    - This feature allows one to zoom in and out of their image.

## Filter menu
    - This menu contains all of the filters that modify the image with various convolutions.

## Color menu
    - Here, you will find ways to modify the color of your image, such as converting to greyscale.

## Transform menu
    - In this menu, there are features that allow you to modify the shape and orientation of your image.

## Mouse menu
    - Contains the option to crop and draw on your image.

## Language menu
    - In this menu, you will find options to select from the languages supported in Andie.

## Toolbar
    - The toolbar is host to a few of the most useful features, such as saving, zooming in and out, and cropping.


## License
Licenced under the creative commons license
<a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>

## Contributions

### Sam (godsa744)
    - Sharpen Filter
    - Gaussian Blur Filter
    - Image Export
    - Multilingual Support (Enlgish, French & Maori)
    - Cropping to Selection
    - Mouse Selection of region
    - Drawing Functions

### Tan (robta083)
    - Image Rotations
    - Image Flip

### Nick (garni988)
    - Brightness Adjustment
    - Contrast Adjustment
    - Image resize
    - Keyboard Shortcuts
    - Toolbar

### Luke (weblu938)
    - Median Filter
    - Unit Testing
    - Emboss Filter
    - Sobel Filter
    - Operation Macros
    - Themes
    - GUI Testing 
    - User Guide

All contributors responsible for:
    - Other Error avoidance/prevention
    - Exception Handling

## Testing
The ANDIE GUI was tested by all contributors. 
As features were developed, contributors tested functionality via interaction with the GUI.
Once features were satisfactory, contributors pushed their feature to the repository.

Unit testing was completed by Luke using the JUnit testing platform in VSCode. See repository src/test/cosc202/andie for all unit tests. 
Unit tests ensured that the functions of each feature were consistent. 

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
