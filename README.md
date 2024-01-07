# XML/JSON Data Analysis Java Application

## Overview

This Java application provides a suite of functionalities for manipulating XML and JSON files and performing various data analyses. The application is divided into two levels, each contributing distinct features to enhance XML and JSON handling as well as data representation and analysis.

## Level 1 Functionalities

### 1. GUI (Graphical User Interface)

- Developed using SceneBuilder and JavaFX.
- SceneBuilder automatically updates the GUI.fxml file, and the corresponding Java controller file manages button, text field, and text area events.

### 2. Error Detection & Correction

- Implements a function to detect and correct errors in XML files.
- Utilizes a stack to determine the file's structure, identifying and correcting discrepancies in opening and closing tags.

### 3. Prettifying

- Formats XML files with proper indentation for improved readability.
- Iterates through file characters, adding tabs and new lines based on the file's structure.

### 4. Convert XML to JSON

- Converts XML files to JSON format by parsing the file, identifying opening and closing tags, and determining the content type (array or object).

### 5. Minifying

- Removes whitespaces and indentations from XML and JSON files for a more compact representation.

### 6. Compress XML/JSON

- Reduces file size by encoding repeated terms or removing white spaces.
- Creates an array list of repeated terms and replaces them with specific characters.

### 7. Decompress XML/JSON

- Restores compressed files to their original format by replacing encoded characters and handling special characters.

### 8. Undo/Redo

- Implements undo and redo functionality using two stacks: undoStack and redoStack. Each operation in Level 1 functionalities pushes the resulting string onto the undoStack.

## Level 2 Functionalities

### 1. Represent Data Using Graph

- Parses XML files to create a graph representation of user relationships.
- Users are vertices, and followers are edges.

### 2. Data Parsing

- Extracts user information from XML files and stores it in a User class.
- An iterator is used to iterate through file characters, creating User objects for each user.

### 3. Most Influencer

- Identifies the user with the most followers by iterating through an array list of linked lists containing user IDs and their followers.

### 4. Most Active

- Determines the user with the largest number of followers, considering them the most active user.

### 5. Mutual Followers

- Identifies mutual followers between two users by comparing their follower lists.

### 6. Suggest Followers

- Suggests users to follow based on the followers of their followers.

### 7. Post Search

- Iterates over posts to check for the existence of a specific word in the post body or topics.

### 8. Undo/Redo

- Undo and redo functionalities exclusive to Level 1 functionalities, as they involve altering the content of XML files.


## How to Use

1. Clone the repository.
2. Open the project in your preferred Java IDE.
3. Run the main application file.
4. Use the graphical interface to perform various XML and JSON manipulations and data analyses.

## Contributors

- **Martin Ashraf Ibrahim**
  - Error detection
  - Error correction
  - Post Search
  - Undo/Redo

- **Mina Hany Hanna**
  - Compress & decompress XML/JSON
  - Most Influencer
  - Most Active
  - Mutual followers
  - Suggest followers

- **Marten Ehab Fouad**
  - Convert XML to JSON
  - Data Parsing

- **Ereny Hany Hanna**
  - GUI
  - Minifying
  - Graph visualization

- **Lara Maurice Youssef**
  - Prettifying
  - Representing Data using Graph
