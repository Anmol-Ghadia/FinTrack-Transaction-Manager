package ui;

import model.LogicComponent;
import model.LogicInput;
import model.LogicNot;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static ArrayList<Character> RawInputs;
//    static ArrayList<LogicComponent> Inputs;
    static HashMap<String, LogicComponent> gateMap = new HashMap<>();

    public static void main(String[] args) {
        String expr = "(" + "(~ ( A V ~ B )) ^~C".replace(" ","").concat(")");
        System.out.println("Expression: " + expr);
        RawInputs = findInputs(expr);
        System.out.println("Input alphabets: " + RawInputs);
        inputAsGates(RawInputs);
        System.out.println("Input alphabet components: " + gateMap);
        singleNot(expr);
        System.out.println("After single nots: " + gateMap);
        // try creating gates: till final expression is made
//        ArrayList<String> tempMiniExpression = extractMiniExpressions(expr);
//        System.out.println("miniExpressions: " + tempMiniExpression);
        ArrayList<String> gateTitleList = extractAllExpressions(expr);
        System.out.println("expression list: " + gateTitleList);
    }

    public static ArrayList<Character> findInputs(String exp) {
        String alphabetsOnly = exp.replaceAll("[^A-UW-Za-uw-z]+","");
        ArrayList<Character> inputs = new ArrayList<Character>();
        for (char i: alphabetsOnly.toCharArray()) {
            if (! inputs.contains(i)) {
                inputs.add(i);
            }
        }
        return inputs;
    }

    public static void inputAsGates(ArrayList<Character> inputChar) {
        ArrayList<LogicComponent> gates = new ArrayList<>();
        for (Character i: inputChar) {
            LogicComponent current = new LogicInput(i);
            gates.add(current);
            gateMap.put(i.toString(),current);
        }
    }

    public static void singleNot(String exp) {

        String previous = exp.substring(0,1);
        for (Character i: exp.toCharArray()) {
            if (previous.equals("~") && RawInputs.contains(i)) {
                LogicComponent input = gateMap.get(i);
                gateMap.put("~" + i, new LogicNot(input));
            }
            previous = i.toString();
        }
    }

    // OLD Method: replaced by extract all
    @SuppressWarnings("methodlength")
    public static ArrayList<String> extractMiniExpressions(String exp) {
        ArrayList<String> stack = new ArrayList<>();
        String current = "";
        for (Character i : exp.toCharArray()) {
            if (i.toString().equals("(")) {
                if (current.equals("")) {
                    stack.add("(");
                } else {
                    stack.add(current);
                    stack.add("(");
                    current = "";
                }
            } else if (i.toString().equals(")")) {
                if (current.equals("")) {
                    stack.add(")");
                } else {
                    stack.add(current);
                    stack.add(")");
                    current = "";
                }
            } else {
                current += i.toString();
            }
        }
        stack.add(current);
        return stack;
    }

    public static ArrayList<String> extractAllExpressions(String expr) {
        ArrayList<String> stack = new ArrayList<>();
        ArrayList<String> finalStack = new ArrayList<>();
        for (Character i: expr.toCharArray()) {
            if (i.toString().equals("(")) {
                // new element in stack
                // add ( to every element then
                stack = addToEveryItem(stack,"(");
                stack.add("");
            } else if (i.toString().equals(")")) {
                // remove element from stack
                finalStack.add(stack.remove(stack.size() - 1));
                // add ) to every element
                stack = addToEveryItem(stack,")");
            } else {
                // add i to every element
                stack = addToEveryItem(stack,i.toString());
            }
//            finalStack.add((stack.remove(0)));
        }
        System.out.println("internal Stack(Should be empty): " + stack);
        return finalStack;
    }


    public static ArrayList<String> addToEveryItem(ArrayList<String> array, String item) {
        ArrayList<String> newArray = new ArrayList<>();
        for (String member: array) {
            newArray.add(member = member.concat(item));
        }
        return newArray;
    }
}
