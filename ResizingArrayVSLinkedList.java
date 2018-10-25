// Paige Weber, L22812475
// Times and compares Resizing Arrays VS. Linked List
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

class Stack
{
    Node first = new Node(); // make a node
    //Node tail = new Node();

    private class Node
    {
        int value;
        Node next;

        public Node()
        {
            value = 0;
            next = null;
        }

        public Node(int val, Node n)
        {
            value = val;
            next = n;
        }

    }

    public void push(int item)
    {
        if(this.isEmpty())
        {
            Node tmp = new Node();
            tmp.value = item;
            first = tmp;
        }
        else
        {
            Node tmp = new Node(item, first);
            first = tmp;
        }   
    }

    public int pop()
    {   
        int val = 0;
        if(this.isEmpty())
        {
            return val;
        }
        val = first.value;
        if(first.next == null)
        {
            first = null;
        }
        else
        {
            first = first.next;
        }
        return val;
    }

    boolean isEmpty()
    {
        return first == null;
    }

     public int peek()
    {
        int val = 0;
        if(this.isEmpty())
        {
            return val;
        }
        val = first.value;
        return val;
    }

}
class ResizingArrayStack
{
    private int[] a;         // array of items
    private int n;            // number of elements on stack


    public ResizingArrayStack() 
    {
        a = new int[2];
        n = 0;
    }


    private void resize(int capacity) 
    {
        
        int[] temp = new int[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;

    }

    public void push(int item) {
        if (n == a.length) resize(2*a.length);    // double size of array if necessary
        a[n++] = item;                            // add item
    }

    public int pop() 
    {
        return a[n - 1];
    }


}



public class ResizingArrayVSLinkedList
{
     
    public static void main(String[] args)
    {
        //user input for max, min, how many
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the maximum integer: ");
        int max = scanner.nextInt();
        System.out.println("Enter the minimum integer: ");
        int min = scanner.nextInt();
        System.out.println("Enter how many integers: ");
        int howMany = scanner.nextInt();

        ResizingArrayStack Array = new ResizingArrayStack();
        Stack LinkedList = new Stack();

        Random random = new Random();


        final long startTime1 = System.currentTimeMillis();
        for(int i = 0; i < howMany; i++)
        {
            int randomVal = random.nextInt((max - min) + 1) + min;
            LinkedList.push(randomVal);
        }

        for(int i = 0; i < howMany; i++)
        {
            LinkedList.pop();
        }
        final long endTime1 = System.currentTimeMillis();
        final long finalTime1 = endTime1 - startTime1;
        System.out.println("Linked list execution time: " + finalTime1 + " milliseconds");


        final long startTime2 = System.currentTimeMillis();
        for(int i = 0; i < howMany; i++)
        {
            int randomVal = random.nextInt((max - min) + 1) + min;
            Array.push(randomVal);
        }

        for(int i = 0; i < howMany; i++)
        {
            Array.pop();
        }
        final long endTime2 = System.currentTimeMillis();
        final long finalTime2 = endTime2 - startTime2;
        System.out.println("Resizing array execution time: " + finalTime2 + " milliseconds");

        System.out.println("Linked list execution: " + (finalTime1/finalTime2) + " times slower");
    }



       
    
}