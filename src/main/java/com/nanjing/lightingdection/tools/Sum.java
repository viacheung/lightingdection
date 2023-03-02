package com.nanjing.lightingdection.tools;

import java.text.NumberFormat;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sum {

  private static double doubleCal(double a1, double a2, char operator) throws Exception {
    switch (operator) {
      case '+':
        return a1 + a2;
      case '-':
        return a1 - a2;
      case '*':
        return a1 * a2;
      case '/':
        return a1 / a2;
      default:
        break;
    }
    throw new Exception("illegal operator!");
  }

  private static int getPriority(String s) throws Exception {
    if (s == null) return 0;
    else if (s.equals("(")) return 1;
    else if (s.equals("+") || s.equals("-")) return 2;
    else if (s.equals("*") || s.equals("/")) return 3;
    throw new Exception("illegal operator!");
  }

  public static String getResult(String expr) throws Exception {
    Stack<Double> number = new Stack<Double>();
    Stack<String> operator = new Stack<String>();
    operator.push(null);
    Pattern p = Pattern.compile("(?<!\\d)-?\\d+(\\.\\d+)?|[+\\-*/()]");
    Matcher m = p.matcher(expr);
    while (m.find()) {
      String temp = m.group();
      if (temp.matches("[+\\-*/()]")) {
        if (temp.equals("(")) {
          operator.push(temp);
        } else if (temp.equals(")")) {
          String b = null;
          while (!(b = operator.pop()).equals("(")) {
            double a1 = number.pop();
            double a2 = number.pop();
            number.push(doubleCal(a2, a1, b.charAt(0)));
          }
        } else {
          while (getPriority(temp) <= getPriority(operator.peek())) {
            double a1 = number.pop();
            double a2 = number.pop();
            String b = operator.pop();
            number.push(doubleCal(a2, a1, b.charAt(0)));
          }
          operator.push(temp);
        }
      } else {
        number.push(Double.valueOf(temp));
      }
    }

    while (operator.peek() != null) {
      double a1 = number.pop();
      double a2 = number.pop();
      String b = operator.pop();
      number.push(doubleCal(a2, a1, b.charAt(0)));
    }
    Double pop = number.pop();
    NumberFormat nf = NumberFormat.getInstance();
    nf.setGroupingUsed(false);
    String format = nf.format(pop);
    return format;
  }
}
