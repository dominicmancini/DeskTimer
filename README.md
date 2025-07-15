# DeskTimer

A simple desktop timer application that allows you to set timers or alarms with a system tray icon and notification alerts.

## Table of Contents

<!-- toc -->

- [Features](#features)
- [Motivation](#motivation)
  * [Key Learning Objectives](#key-learning-objectives)
  * [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  * [Requirements](#requirements)
  * [Installation & Usage](#installation--usage)

<!-- tocstop -->

## Features


- Set countdown timers
- Set specific time alarms
- Automatically minimizes to system tray.
- Hovering the tray icon shows remaining time tooltip
- Allows for multiple DeskTimer instances at once.
- Notification alerts when the timer or alarm goes off


## Motivation

I couldn't find any built-in Windows timer app that offered this specific functionality (minimize to tray), so I decided to develop DeskTimer. I use it regularly to set reminders and alarms for various tasks, and I hope others will find it just as simple and useful!

### Key Learning Objectives

- Working with Java Swing for GUI development
- Understanding event-driven programming and event handling
- Using AWT for system tray integration and notifications
- Creating user interfaces with MigLayout and Swing components
- Utilizing and manipulating date-time formats

### Technologies Used

- Java
- Swing
- MigLayout
- Java Date/Time API
- System Tray API
- Java Notification API
- Java AWT for system tray integration

## Getting Started

> [!NOTE]
> This project has only been tested on Windows. It may not work on other operating systems without modifications.


### Requirements

- Java Development Kit (JDK) 8 or higher
- Swing library (included in JDK)
- See [pom.xml](./pom.xml) for additional dependencies if using Maven

### Installation & Usage
To set up the project locally, follow these steps:

```bash
# 1. Clone the repository
git clone https://github.com/dominicmancini/DeskTimer.git

# 2. Navigate to the project directory
cd DeskTimer

# 3. Compile the Java program
javac App.java

# 4. Run the application
java App

```
