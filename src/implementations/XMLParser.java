package implementations;

import java.nio.file.Files;
import java.nio.file.Paths;

import exceptions.EmptyQueueException;

import java.io.IOException;

public class XMLParser {
    
    private MyStack<String> fileStructureStack = new MyStack<>();
    private MyQueue<String> errorQueue = new MyQueue<>();
    private MyQueue<String> extrasQueue = new MyQueue<>();
    private String filePath; 
    
    public XMLParser(String xmlFilePath) {
        this.filePath = xmlFilePath;
    }

    public void ParseFile() throws NullPointerException, EmptyQueueException {
        String xmlContent;
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            xmlContent = new String(bytes);
        } catch(IOException e) {
            System.out.println("Error reading file: " + e);
            return;
        }

        int index = 0;
        String[] lines = xmlContent.split("\n");

        // Main loop for checking the file
        while(index < lines.length) {
            String line = lines[index].trim();  // Trim whitespace

            // Skip empty lines
            if (line.isEmpty()) {
                index++;
                continue;
            }

            // Check for Self-Closing Tag
            if (isSelfClosingTag(line)) {
                //do nothing 
            } 
            // Check for Start Tag
            else if (isStartTag(line)) {
            	System.out.println(line);
                fileStructureStack.push(line);
            } 
            // Check for End Tag
            else if (isEndTag(line)) {
                handleEndTag(line);
            }
            
            index++;
        }

        handleLeftoversInStack();
        handleQueues();
    }


    private boolean isSelfClosingTag(String line) {
        return line.endsWith("/>");
    }

    private boolean isStartTag(String line) {
        return line.startsWith("<") && !line.startsWith("</");
    }

    private boolean isEndTag(String line) {
        return line.startsWith("</");
    }

    // Handle an end tag (match it with the stack)
    private void handleEndTag(String line) throws NullPointerException, EmptyQueueException {
        if (!fileStructureStack.isEmpty() && line.equals(fileStructureStack.peek())) {

            fileStructureStack.pop();

        } else if (!errorQueue.isEmpty() && line.equals(errorQueue.peek())) {

            errorQueue.dequeue();

        } else if (fileStructureStack.isEmpty()) {

            errorQueue.enqueue(line);

        } else {
            // Search for matching start tag in the stack
            boolean matchFound = false;
            while (!fileStructureStack.isEmpty()) {
                String top = fileStructureStack.pop();
                if (top.equals(line)) {
                    matchFound = true;
                    break;
                } else {
                    errorQueue.enqueue(top);
                }
            }

            if (!matchFound) {
                // No match found in the stack, add the end tag to the extras queue
                extrasQueue.enqueue(line);
            }
        }
    }


    private void handleLeftoversInStack() {
        while (!fileStructureStack.isEmpty()) {
            String leftoverTag = fileStructureStack.pop();
            errorQueue.enqueue(leftoverTag);
        }
    }


    private void handleQueues() throws EmptyQueueException {
        while (!errorQueue.isEmpty() || !extrasQueue.isEmpty()) {
            if (errorQueue.isEmpty()) {
                System.out.println("Extras queue has unmatched tags: ");
                while (!extrasQueue.isEmpty()) {
                    System.out.println("Extras queue: " + extrasQueue.dequeue());
                }
            } else if (extrasQueue.isEmpty()) {
                System.out.println("Error queue has unmatched tags: ");
                while (!errorQueue.isEmpty()) {
                    System.out.println("Error queue: " + errorQueue.dequeue());
                }
            } else {
                String errorTag = errorQueue.peek();
                String extraTag = extrasQueue.peek();

                if (errorTag.equals(extraTag)) {
                    errorQueue.dequeue();
                    extrasQueue.dequeue();
                    System.out.println("Matching error and extra tags removed: " + errorTag);
                } else {
                    System.out.println("Error found between error queue and extras queue: ");
                    System.out.println("Error: " + errorQueue.dequeue());
                }
            }
        }
    }
    

}
