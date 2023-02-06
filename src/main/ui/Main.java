package ui;

import model.LogicComponent;
import model.LogicInput;
import model.LogicNot;

import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    static ArrayList<Character> GATE_SYMBOL = new ArrayList<Character>(List.of('V', '^'));
    static ArrayList<Character> RawInputs;
//    static ArrayList<LogicComponent> Inputs;
    static HashMap<String, LogicComponent> gateMap = new HashMap<>();

    public static void main(String[] args) {
        String expr = "(" + "(~(AV~B))^~C".replace(" ","").concat(")");
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
        System.out.println("AV~B :" + findOperator("AV~B"));

    }

//    public static ArrayList<LogicComponent> makeGates(ArrayList<LogicComponent> inputGates,
//                                                      ArrayList<String> gateTitleList) {
//        while (gateTitleList.size() != 0) {
//            for (String miniExpression: gateTitleList) {
//                // check if a gate's inputs are already in inputgates
//                // if yes then make the appropriate gate and add it to the final array
//                // and remove it from the title list
//                // else check the next item.
//                if (miniExpression.startsWith("~")) {
//                    // treat as not expression
//                } else {
//                    // treat as binary operator
//                    ArrayList<String> splitExpression = findOperator(miniExpression);
//                }
//            }
//        }
//        return ...;
//    }

    @SuppressWarnings("methodlength")
    // only works if the expression starts with bracket
    public static ArrayList<String> findOperator(String miniExpression) {
        ArrayList<String> splitExpression = new ArrayList<>();
        String tempString = "";
        if (miniExpression.charAt(0) == '(') {
            // separate mini expression based on brackets stack
            ArrayList<Boolean> stack = new ArrayList<>();
            // true for open bracket
    //        boolean foundOperator = false;
            for (int i = 0; i < miniExpression.length(); i++) {
                Character chr = miniExpression.charAt(i);

                if (chr.toString().equals("(")) {
                    // open bracket found
                    // if first open bracket then don't add to string
                    // else add to the string
                    if (stack.size() != 0) {
                        tempString += chr.toString();
                    }
                    stack.add(true);
                } else if (chr.toString().equals(")")) {
                    // close bracket found
                    // if last close bracket then don't add to string
                    // else add to the string
                    stack.remove(stack.size() - 1);
                    if (stack.size() > 0) {
                        // not last bracket
                        tempString += chr.toString();
                    } else {
                        // last bracket
                        splitExpression.add(tempString);
                        tempString = "";
                        // add operator and rest of expression
                        splitExpression.add(miniExpression.substring(i + 1,i + 2));
                        splitExpression.add(miniExpression.substring(i + 2, miniExpression.length()));
                        break;
                    }
                } else {
                    tempString += chr.toString();
                }
            }
        } else {
            // starts without bracket
            for (int i = 0; i < miniExpression.length(); i++) {
                Character chr = miniExpression.charAt(i);
                if (GATE_SYMBOL.contains(chr)) {
                    splitExpression.add(tempString);
                    tempString = "";
                    splitExpression.add(chr.toString());
                    splitExpression.add(miniExpression.substring(i + 1,miniExpression.length()));
                    // may contain outer brackets in the second operand, which may or may not be an issue
                    break;
                } else {
                    tempString += chr.toString();
                }
            }
        }

        return splitExpression;
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
