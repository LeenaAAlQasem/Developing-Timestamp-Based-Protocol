# Developing-Timestamp-Based-Protocol
Multi-version Timestamp Ordering technique


## Introduction

As we know in most multiprogramming environments, multiple transactions can be executed simultaneously, however, this can cause some issues. To avoid that, it's sufficient to control the concurrency of the transactions using the Multi-version timestamp ordering which is one of the techniques of the most common method, Timestamp Based Protocol. This protocol manages concurrent execution and determines serializability order, it's also known for Multi-version timestamp ordering to increase concurrency through versioning data. In this project, we aim to implement the Multi-version timestamp ordering technique.

## Specification

- Class MultiVersion (main class)
- Class Transaction
- Class Operation
- Class Version

## Design

In this project, we designed a Java programming language program using the graphical user interface (GUI) to make it more user-friendly. This program will implement the Multi-version timestamp ordering protocol. Firstly, when the user runs the program, it will ask to enter the path of the file with the schedule and click on the Multi-version timestamp ordering button. Then, the program will test whether the file succeeds or aborts (fails) by using Multi-version timestamp ordering rules. Finally, if the file succeeded, a serial schedule is printed, which is an ordered list of the transaction number. Otherwise, if it aborted, an error message will appear with the operation and transaction that aborted and the time where the abortion occurred.

