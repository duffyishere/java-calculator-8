package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private String input;
    public static final Pattern CUSTOM_SEPARATOR_PATTERN = Pattern.compile("//(.)\r?\n+(.*)", Pattern.DOTALL);

    public Calculator(String input) {
        this.input = input;
    }

    public int sum() {
        return split().stream()
        .reduce(0, Integer::sum);
    }

    private List<Integer> split() {
        try {
            String[] splitInput;
            if (this.input.startsWith("//")) {//커스텀 구분자를 사용하는 경우
                Matcher matcher = CUSTOM_SEPARATOR_PATTERN.matcher(this.input);
                if (!matcher.matches()) throw new IllegalArgumentException("잘못된 구분자가 입력되었습니다. (\\는 구분자로 사용할 수 없습니다.)");
                splitInput = matcher.group(2).split(",|:|" + Pattern.quote(matcher.group(1)));
            } else {
                splitInput = this.input.split(",|:");
            }
            List<Integer> result = new ArrayList<>();
            for (String str: splitInput) {
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