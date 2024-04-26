# Multi-Threaded Realtime, Collaborative Drawing Board & Chat Application
A Multi-Threaded Real-Time, Collaborative Shared Drawing Board where multiple users can draw on a shared canvas together at the same time and chat together at the same time. <br>
<br>
This distributed shared whiteboard application enables real-time collaboration among multiple users. Users can concurrently draw and modify shapes on the whiteboard, allowing for collaborative brainstorming, presentations, or creative work. <br>
<br>
Application leverages advanced multi-threading and concurrency mechanisms to ensure seamless real-time collaboration, enabling multiple users to simultaneously draw and interact without any lag or overlap issues.

Used Java, Multi-Threading/Concurrency principles, Remote Method Invocation.

## Feature Run-Through:
![](https://github.com/jaiphookan20/RealTime-Collaborative-DrawingBoard/blob/main/Collaborative%20Whiteboard.gif)

# Class Diagram:

![Screenshot 2024-04-24 at 10 45 54 pm](https://github.com/jaiphookan20/RealTime-Collaborative-DrawingBoard/assets/52240311/185c81db-37a1-414f-9065-e419b064addf)

## Key Operations:

Key Operations
## 1) Connect to the Whiteboard:
Users can connect to the whiteboard application by providing a username. The application validates the username and assigns a unique identifier (userId) to the user. The JoinWhiteboard class handles this operation.

## 2) Draw Shapes:
Users can draw various shapes on the whiteboard, such as lines, rectangles, circles, ovals or even text. The application provides drawing tools and options to choose colors from a list of 16 predefined colors.. The DrawFrame class and related shape classes (Shape, ShapeAdapter) are involved in handling shape drawing operations.


## 3) Synchronize Shape Updates:
When a user draws a certain shape, the changes need to be synchronized and reflected in real-time on the whiteboard for all connected users. The application employs a synchronization mechanism using Java RMI, to propagate shape updates to other clients. The IRemote interface and related server-side methods handle the synchronization of shape updates.

![image](https://github.com/jaiphookan20/Multi-Threaded-Shared-DrawingBoard/assets/52240311/d31ffd79-3676-4d16-800b-17ff9d0a3f1f)

## 4) Chat Functionality:
Users can engage in real-time chat communication within the whiteboard application. They can send and receive chat messages, which are displayed in the chat area of the user interface.

# Advanced Features:
5) User Removal:  
While all users are allowed to participate in the whiteboard activities, only the Manager i.e. the first user to join has the option to remove certain users from the userlist. 

6) Load Whiteboard State / Open File: 
Users can load a previously saved whiteboard state from a file. This operation allows users to retrieve and continue working on a previously saved whiteboard session. The application supports loading whiteboard state files in the appropriate format and updates the whiteboard interface with the loaded shapes and properties.

7) Clear Whiteboard: 
Users can choose to clear the entire whiteboard, removing all the drawn shapes and resetting the canvas. This operation provides a way to start afresh or remove unwanted content from the whiteboard. The application provides a ‘new’ menu option that triggers the necessary methods to clear the whiteboard.

8) Save Whiteboard State: The Manager i.e. the first user has the ability to save the current state of the whiteboard, including all the drawn shapes and associated properties. This operation allows for the preservation of all the user's work and allows it to be loaded later by saving it. 
