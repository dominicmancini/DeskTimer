# DeskTimer

A simple and efficient GUI Desktop Timer and Alarm application.

## Table of Contents

- [DeskTimer](#desktimer)
  - [Table of Contents](#table-of-contents)
  - [About the Project](#about-the-project)
    - [Key Learning Objectives](#key-learning-objectives)
  - [Technologies Used](#technologies-used)
  - [Features](#features)
  - [Installation](#installation)
  - [Usage](#usage)
    - [Setting Timers / alarms](#setting-timers--alarms)

## About the Project

DeskTimer is a GUI Desktop Timer application that allows you to set timers or alarms that can run in the background. Timers let you specify a duration of time to wait until the timer rings, while alarms notify you at a specific time. Upon setting the timer, a tray icon will be displayed in the corner of your screen with a with tooltip showing the remaining time until the timer rings. You can optionally attach a message to the notification if desired for reminders.

I couldn't find any built-in Windows timer app that offered this specific functionality, so I decided to develop DeskTimer. I use it regularly to set reminders and alarms for various tasks, and I hope others will find it just as simple and useful!

### Key Learning Objectives

- Working with Java Swing for GUI development
- Understanding event-driven programming and event handling
- Integrating with native OS APIs, such as the system tray and notifications
- Creating user interfaces with MigLayout and Swing components
- Utilizing and manipulating date-time formats

## Technologies Used

- Java
- Swing
- AWT
- MigLayout

## Features


- Set countdown timers
- Set specific time alarms
- Allows for multiple DeskTimer instances at once. 
- System tray icon with remaining time tooltip
- Notification alerts when the timer or alarm goes off

## Installation
To set up the project locally, follow these steps:

```bash
1. Clone the repository
    ```bash
    git clone https://github.com/dominicmancini/DeskTimer.git
    ```

2. Navigate to the project directory
    ```bash
    cd DeskTimer
    ```

3. Compile the Java program
    ```bash
    javac App.java
    ```
```

## Usage

### Setting Timers / alarms

When launching the program, a tabbed pane will allow you to set timers or alarms. The Spinners allow you to use either arrows or directly enter the field to set the time. To set a timer, navigate to the "Timer" tab and enter a duration in minutes. To set an alarm, navigate to the "Alarm" tab and enter a time in the format "hh:mm AM/PM".

