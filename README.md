# Operating System Process Scheduling Simulation

This Java-based GUI application simulates three classic process scheduling algorithms to compare their performance in terms of Average Wait Time and Average Turnaround Time. The application allows users to load process data from a text file and visualize the scheduling results of First Come First Served (FCFS), Shortest Job Next (SJN), and Round Robin (RR) algorithms.

## Features

- **GUI Interface**: Provides a user-friendly interface to interact with the application.
- **Load Input File**: Users can load process data from a text file specifying Process ID, Process Priority, Arrival Time, and Required Processor Time.
- **Scheduling Policies**: 
  - First Come First Served (FCFS)
  - Shortest Job Next (SJN)
  - Round Robin (RR) with a time quantum of 100 milliseconds.
- **Run All**: Executes all scheduling algorithms consecutively and displays comparative results.
- **Exit**: Closes the application.

## Bonus Feature

- **Variable Time Slices**: Enhances the Round Robin scheduling by varying the time slice from 50 to 250 in increments of 25, providing detailed insights into scheduling performance across different time slices.

## Input Format

The application expects a text file with the following format:
<Process ID> <Process Priority> <Arrival Time> <Required Processor Time>

Example:
```
4
1 3 0 20
2 2 1 18
3 4 2 25
4 5 3 15
```
Each line after the first specifies a process's ID, priority level, arrival time, and required processing time.

## Getting Started

To get started with this project, follow these steps:

1. **Clone the repository** to your local machine.
3. **Run `ProcessManagement.java`** to launch the GUI.
4. **Use the menu options** to load data and execute the scheduling algorithms.

