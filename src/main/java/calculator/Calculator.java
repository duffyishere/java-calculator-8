package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    public static final Pattern CUSTOM_SEPARATOR_PATTERN = Pattern.compile("//(.)\\\\n+(.*)", Pattern.DOTALL);
    public static final String DEFAULT_SEPARATORS = ",|:";
    public static final String SEPARATOR_PREFIX = "//";

    private String expression;

    public Calculator(String expression) {
        this.expression = expression;
    }

    public int sum() {
        return split().stream()
        .reduce(0, Integer::sum);
    }

    private List<Integer> split() {
        String[] splitExpression;
        if (this.expression.startsWith(SEPARATOR_PREFIX)) {
            Matcher matcher = CUSTOM_SEPARATOR_PATTERN.matcher(this.expression);
            if (!matcher.matches())
                throw new IllegalArgumentException("잘못된 구분자가 입력되었습니다. (\\는 구분자로 사용할 수 없습니다.)");
            String separators = DEFAULT_SEPARATORS + "|" + Pattern.quote(matcher.group(1));
            splitExpression = matcher.group(2).split(separators);
        } else {
            splitExpression = this.expression.split(DEFAULT_SEPARATORS);
        }
        return parseInt(splitExpression);
    }

    private List<Integer> parseInt(String[] splitExpression) {
        try {
            List<Integer> result = new ArrayList<>();
            for (String str: splitExpression) {
                Integer num = Integer.parseInt(str.trim());
                if (num < 0) throw new IllegalArgumentException("음수 값은 입력할 수 없습니다.");
                result.add(num);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자가 아닌 값을 입력하였습니다.");
        }
    }
}