package raivio.kaappo.koe;

import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class QuestionManager {
    private Reporter reporter;

    public QuestionManager (Reporter reporter) {
        this.reporter = reporter;
    }

    public List<List<String>> getBestOptions (int amountOfQuestions, int amountOfOptions) throws UserRecoverableAuthIOException{
//        List<Pair<String, Integer>> list = new ArrayList<>();
//
//        List<String> options = getOptions();
//        Map<String, Integer> amounts = getAmountOfOccurences();
//
//        for (String string : options) {
//            list.add(new Pair<>(string, amounts.getOrDefault(string, 0)));
//        }
//
//        list.sort((x, y) -> x.getV() - y.getV());
//
//        return list.subList(0, amount).stream().map(x -> x.getK()).collect(Collectors.toList());
        List<String> options = getOptions();
        List<List<String>> result = new ArrayList<>();

        Random random = new Random(56245);

        for (int i = 0; i < amountOfQuestions; i++) {
            List<String> temp = new ArrayList<>();
            for (int j = 0; j < amountOfOptions; j++) {
                String string = options.get(random.nextInt(options.size()));
                if (temp.contains(string)) {
                    j--;
                } else {
                    temp.add(string);
                }
            }
            result.add(temp);

            System.out.println("ALive");
        }

        return result;
    }

    private List<String> getOptions () throws UserRecoverableAuthIOException {
        System.out.println("Getoptions");
        List<List<String>> data;

        data = reporter.getData("D2:D8");

        System.out.println("Getoptions");

        return data.stream().map(list -> list.get(0)).collect(Collectors.toList());
    }

    private Map<String, Integer> getAmountOfOccurences () {
        List<List<String>> data;
        try {
            data = reporter.getData("A20:A");
            data.addAll(reporter.getData("C20:C"));
        } catch (UserRecoverableAuthIOException e) {
            throw new RuntimeException(e);
        }

        Map<String, Integer> map = new HashMap<>();
        for (List<String> list : data) {
            String string = list.get(0);
            if (map.containsKey(string)) {
                map.put(string, map.get(string) + 1);
            } else {
                map.put(string, 1);
            }
        }

        return map;
    }
}
