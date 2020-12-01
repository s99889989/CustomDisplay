package com.daxton.customdisplay.api.character;

import java.util.Collections;
import java.util.Stack;

public class Calculator {
    private Stack<String> postfixStack = new Stack<String>();
    // 字尾式棧
    private Stack<Character> opStack = new Stack<Character>();
    // 運算子棧
    private int[] operatPriority = new int[] { 0, 3, 2, 1, -1, 1, 0, 2 };
    // 運用運算子ASCII碼-40做索引的運算子優先順序
    public static double conversion(String expression) {
        double result = 0;
        Calculator cal = new Calculator();
        try {
            expression = transform(expression);
            result = cal.calculate(expression);
        }
        catch (Exception e) {
// e.printStackTrace();
// 運算錯誤返回NaN
            return 0.0 / 0.0;
        }
// return new String().valueOf(result);
        return result;
    }
    /**
     * 將表示式中負數的符號更改
     *
     * @param expression
     *      例如-2 -1*(-3E-2)-(-1) 被轉為 ~2 ~1*(~3E~2)-(~1)
     * @return
     */
    private static String transform(String expression) {
        char[] arr = expression.toCharArray();
        for (int i = 0; i < arr.length; i++  ) {
            if (arr[i] == '-') {
                if (i == 0) {
                    arr[i] = '~';
                } else {
                    char c = arr[i - 1];
                    if (c == ' ' || c == '-' || c == '*' || c == '/' || c == '(' || c == 'E' || c == 'e') {
                        arr[i] = '~';
                    }
                }
            }
        }
        if(arr[0]=='~'||arr[1]=='('){
            arr[0]='-';
            return "0";
            //new String(arr);
        } else{
            return new String(arr);
        }
    }
    /**
     * 按照給定的表示式計算
     *
     * @param expression
     *      要計算的表示式例如:5 12*(3 5)/7
     * @return
     */
    public double calculate(String expression) {
        Stack<String> resultStack = new Stack<String>();
        prepare(expression);
        Collections.reverse(postfixStack);
// 將字尾式棧反轉
        String firstValue, secondValue, currentValue;
// 參與計算的第一個值，第二個值和算術運算子
        while (!postfixStack.isEmpty()) {
            currentValue = postfixStack.pop();
            if (!isOperator(currentValue.charAt(0))) {
// 如果不是運算子則存入運算元棧中
                currentValue = currentValue.replace("~", "-");
                resultStack.push(currentValue);
            } else {
// 如果是運算子則從運算元棧中取兩個值和該數值一起參與運算
                secondValue = resultStack.pop();
                firstValue = resultStack.pop();
// 將負數標記符改為負號
                firstValue = firstValue.replace("~", "-");
                secondValue = secondValue.replace("~", "-");
                String tempResult = calculate(firstValue, secondValue, currentValue.charAt(0));
                resultStack.push(tempResult);
            }
        }
        return Double.valueOf(resultStack.pop());
    }
    /**
     * 資料準備階段將表示式轉換成為字尾式棧
     *
     * @param expression
     */
    private void prepare(String expression) {
        opStack.push(',');
// 運算子放入棧底元素逗號，此符號優先順序最低
        char[] arr = expression.toCharArray();
        int currentIndex = 0;
// 當前字元的位置
        int count = 0;
// 上次算術運算子到本次算術運算子的字元的長度便於或者之間的數值
        char currentOp, peekOp;
// 當前操作符和棧頂操作符
        for (int i = 0; i < arr.length; i++  ) {
            currentOp = arr[i];
            if (isOperator(currentOp)) {
// 如果當前字元是運算子
                if (count > 0) {
                    postfixStack.push(new String(arr, currentIndex, count));
// 取兩個運算子之間的數字
                }
                peekOp = opStack.peek();
                if (currentOp == ')') {
// 遇到反括號則將運算子棧中的元素移除到字尾式棧中直到遇到左括號
                    while (opStack.peek() != '(') {
                        postfixStack.push(String.valueOf(opStack.pop()));
                    }
                    opStack.pop();
                } else {
                    while (currentOp != '(' && peekOp != ',' && compare(currentOp, peekOp)) {
                        postfixStack.push(String.valueOf(opStack.pop()));
                        peekOp = opStack.peek();
                    }
                    opStack.push(currentOp);
                }
                count = 0;
                currentIndex = i + 1;
            } else {
                count++  ;
            }
        }
        if (count > 1 || (count == 1 && !isOperator(arr[currentIndex]))) {
// 最後一個字元不是括號或者其他運算子的則加入字尾式棧中
            postfixStack.push(new String(arr, currentIndex, count));
        }
        while (opStack.peek() != ',') {
            postfixStack.push(String.valueOf(opStack.pop()));
// 將操作符棧中的剩餘的元素新增到字尾式棧中
        }
    }
    /**
     * 判斷是否為算術符號
     *
     * @param c
     * @return
     */
    private Boolean isOperator(char c) {
        return c == ' ' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }
    /**
     * 利用ASCII碼-40做下標去算術符號優先順序
     *
     * @param cur
     * @param peek
     * @return
     */
    public Boolean compare(char cur, char peek) {
// 如果是peek優先順序高於cur，返回true，預設都是peek優先順序要低
        Boolean result = false;
        if (operatPriority[(peek) - 40] >= operatPriority[(cur) - 40]) {
            result = true;
        }
        return result;
    }
    /**
     * 按照給定的算術運算子做計算
     *
     * @param firstValue
     * @param secondValue
     * @param currentOp
     * @return
     */
    private String calculate(String firstValue, String secondValue, char currentOp) {
        String result = "";
        switch (currentOp) {
            case ' ':
                result = String.valueOf(ArithHelper.add(firstValue, secondValue));
                break;
            case '-':
                result = String.valueOf(ArithHelper.sub(firstValue, secondValue));
                break;
            case '*':
                result = String.valueOf(ArithHelper.mul(firstValue, secondValue));
                break;
            case '/':
                result = String.valueOf(ArithHelper.div(firstValue, secondValue));
                break;
        }
        return result;
    }
}
