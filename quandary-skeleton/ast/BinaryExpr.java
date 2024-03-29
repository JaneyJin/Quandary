package ast;

import interpreter.Env;
import interpreter.Interpreter;

public class BinaryExpr extends Expr {

    public static final int PLUS = 1;
    public static final int MINUS = 2;
    public static final int TIMES = 3;
    public static final int DOT = 4;

    final Expr expr1;
    final int operator;
    final Expr expr2;

    public BinaryExpr(Expr expr1, int operator, Expr expr2) {
        this.expr1 = expr1;
        this.operator = operator;
        this.expr2 = expr2;
    }

    @Override
    public String toString() {
        return "(" + simpleString() + ")";
    }

    public String simpleString() {
        String s = null;
        switch (operator) {
            case PLUS:  s = "+"; break;
            case MINUS: s = "-"; break;
            case TIMES: s = "*"; break;
            case DOT:   s = "."; break;
        }
        return expr1 + " " + s + " " + expr2;
    }

    @Override
    Object eval(Env env) {
        return doOperation(expr1.eval(env), operator, expr2.eval(env), env);
    }

    static Object doOperation(Object value1, int operator, Object value2, Env env) {
        switch (operator) {
            case PLUS:  return (long)value1 + (long)value2;
            case MINUS: return (long)value1 - (long)value2;
            case TIMES: return (long)value1 * (long)value2;
            case DOT:
                Object returnValue = Interpreter.getInterpreter().cons(value1, value2, env);
                return returnValue;
        }
        throw new RuntimeException("Unreachable");
    }
}
