package calculator;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public final class Summator {
    int sum = 0;
    int prevValue = 0;
    int prevPrevValue = 0;
    int sumLastThreeValues = 0;
    int someValue = 0;
    // !!! эта коллекция должна остаться. Заменять ее на счетчик нельзя.
    private final List<Data> listValues = new ArrayList<>(100_000);
    private final SecureRandom random = new SecureRandom();

    // !!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        listValues.add(data);
        if (listValues.size() % 100_000 == 0) {
            listValues.clear();
        }
        sum += data.value + random.nextInt();

        sumLastThreeValues = data.value + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = data.value;

        for (var idx = 0; idx < 3; idx++) {
            someValue += (sumLastThreeValues * sumLastThreeValues / (data.value + 1) - sum);
            someValue = Math.abs(someValue) + listValues.size();
        }
    }
}
