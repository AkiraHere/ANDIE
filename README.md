# ANDIE - A Non-Destructive Image Editor

Welcome to ANDIE! 

As the title above suggests, ANDIE is an image editor that allows for reversable changes. 
This means that you can make changes to the size, colour or apply filters to an image of your choice, while also being able to undo those changes.
To get started with ANDIE, launch the Andie.java file.
Once running, navigate to the menu item *'File > Open'* to search for the image that you wish to edit.

For a more in depth guide on how to use ANDIE, see the User Guide PDF document.

## License
Licenced under the creative commons license
<a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>

## Contributions

### Sam (godsa744)
    - Sharpen Filter
    - Gaussian Blur Filter
    - Image Export
    - Multilingual Support (Enlgish, French & Maori)

### Tan (robta083)
    - Image Rotations
    - Image Flip

### Nick (garni988)
    - Brightness Adjustment
    - Contrast Adjustment
    - Image resize

### Luke (weblu938)
    - Median Filter
    - Unit Testing
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
