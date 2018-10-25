// Paige Weber, L22812475
// reads input from user and changes it from infix to postfix form  
import java.util.Scanner;
 
class Stack<Item>
{
    Node first = new Node(); // make a node
    //Node tail = new Node();

    private class Node
    {
        Item value;
        Node next;

        public Node()
        {
            value = null;
            next = null;
        }

        public Node(Item val, Node n)
        {
            value = val;
            next = n;
        }

    }

    public void push(Item item)
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

    public Item pop()
    {   
        Item val = null;
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

     public Item peek()
    {
        Item val = null;
        if(this.isEmpty())
        {
            return val;
        }
        val = first.value;
        return val;
    }

}

class Queue<Item>
{
    Node last = new Node();
    Node first = new Node();

    private class Node
    {
        Item value;
        Node next;

    }

    void enqueue(Item item)
    {
        Node oldlast = last;
        last = new Node();
        last.value = item;
        oldlast.next = last;


        //Item val = null;
        //if(this.isEmpty())
        //{
            //return val;
        //}
        //val = first.value;
        //if(first.next == null)
        //{
            //first = null;
        //}
        //else
        //{
            //first = first.next;
        //}
        //return val;


    }

    Item dequeue()
    {
        Item item = first.value;
        first = first.next;
        if(isEmpty())
        {
            last = null;
        }
        return item;
    }

    boolean isEmpty()
    {
        return first == null;
    }

    public Item peek()
    {
        Item val = null;
        if(this.isEmpty())
        {
            return val;
        }
        val = first.value;
        return val;
    }
} 

class InfixToPostfix
{

    public static boolean isNumber(String str)
    {
      
            char[] a = str.toCharArray();
            for(char c:a)
            {
                if(!Character.isDigit(c))
                {
                    return false;
                }
            }
            return true;
        
    }

    
    public static boolean isFunction(String str)
    {
        if(str == null)
        {
            return false;
        }
        if(str.equals("sin") || str.equals("cos") || str.equals("exp") || str.equals("min") || str.equals("max") || str.equals("sqrt") || str.equals("tan") || str.equals("tanh"))//sin, cos, exp, min, max, sqrt, tan, and tanh
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isId(String str)
    {
    
        if(isNumber(str) || isFunction(str) || isOperator(str) || str.equals("(") || str.equals(")"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean isOperator(String str)
    {
        if(str == null)
        {
            return false;
        }
        if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("^"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
  
    private static int precedence(String check)
    {
        if(check == null)
        {
            return 0;
        }
        int returnVal;
        switch (check)
        {
            case "+":
            case "-":
                returnVal = 1;
                break;
            case"*":
            case"/":   
                returnVal = 2;
                break;
            case "^":
                returnVal = 3;
                break;
            default:
                returnVal = 0;
                break;         
        }
        return returnVal;
        
    }
    
    

    public static void main(String[] args)
    {
        Stack<String> operator = new Stack<String>();
        String queue = "";

        Scanner scanner = new Scanner(System.in);
        String tmpLine = scanner.nextLine();
        String[] tmp = tmpLine.split(" ");
        
    
        //read entire line and split it by space
        //while there are tokens to be read:
    
        for(int i = 0; i < tmp.length; i++)
        {
            String token = tmp[i];
            //read a token.
            if(isFunction(token))
            {
                operator.push(token);
            }
            
            else if(isOperator(token)) 
            {
                //the token is an operator, then:  
                while ((isFunction(operator.peek()) || 
                precedence(operator.peek()) > precedence(token) || 
                (precedence(operator.peek()) == precedence(token)) && precedence(token) != 3) && !operator.peek().equals("("))
                {

                    queue += " " + operator.pop();// operators from the operator stack onto the output queue.
                }
                operator.push(token); //it onto the operator stack.

                 
            }
            else if(token.equals("("))
            {
                operator.push(token);//push it onto the operator stack.
            }
            else if(token.equals(")"))
            {
                while(!operator.peek().equals("(")) //the operator at the top of the operator stack is not a left bracket:
                {
                    queue += " " + operator.pop();
                }
                operator.pop();//pop the left bracket from the stack.
                /* if the stack runs out without finding a left bracket, then there are mismatched parentheses. */
           
            }
            else
            {
                if(queue.equals(""))
                {
                    queue +=token;
                }
                else
                {
                    queue += " " + token;
                }
            }
            
            
        }

        while(!operator.isEmpty())
        {
            queue += " " + operator.pop();

        }
        System.out.print(queue);
        scanner.close();


    }
}    


    