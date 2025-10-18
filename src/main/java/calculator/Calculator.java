package calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private String input;

    public Calculator(String input) {
        this.input = input;
    }

    public int sum() {
        return split().stream()
        .reduce(0, Integer::sum);
    }

    private List<Integer> split() {
        try {
            //TODO: 추후 커스텀 구분자가 정규 표현식에 사용되는 문자일경우 '\\'이 구분자 앞에 붙어야 함.
            String[] splitStr = this.input.split(",|:");
            List<Integer> result = new ArrayList<>();
            for (String str: splitStr) {
                Integer num = Integer.parseInt(str);
                // 입력된 값이 음수일 경우 예외처리
                if (num < 0) throw new NumberFormatException();
                result.add(num);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자가 아닌 값을 입력하였습니다.");
        }
    }
}