# Chat Room
Complete the chat room application implementation using WebSocket.

## Background
WebSocket is a communication protocol that makes it possible to establish a two-way communication channel between a
server and a client.

## Instruction
### Implement the message model
Message model is the message payload that will be exchanged between the client and the server. Implement the Message
class in chat module. Make sure you cover all there basic actions.
1. ENTER
2. CHAT
3. LEAVE

### Complete WebSocketChatServer
Implement all TODOs inside WebSocketChatServer follow each method description.

### Build the application with command
- mvn clean
- mvn -Dmaven.test.skip=true package
### Run the application with command
- java -jar target\chatroom-starter-0.0.1-SNAPSHOT.jar
### Test the application with command
- mvn test

