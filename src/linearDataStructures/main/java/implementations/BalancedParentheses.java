package linearDataStructures.main.java.implementations;

import interfaces.Solvable;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        String checkParentheses = parentheses;
        String lookup = "({[";
        for (int i = 0; i < checkParentheses.length(); i++) {
            char symbol = checkParentheses.charAt(i);
            if (lookup.contains(symbol + "")) {
                stack.push(symbol);

            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                try {
                    char pop = stack.pop();
                    if (!(pop == '(' && symbol == ')'
                            || pop == '[' && symbol == ']'
                            || pop == '{' && symbol == '}')) {
                        return false;
                    }
                } catch (Exception ex) {

                }
            }
        }
        return true;
    }
}