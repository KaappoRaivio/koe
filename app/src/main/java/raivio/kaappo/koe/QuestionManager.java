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

import raivio.kaappo.koe.Reporter;

public class QuestionManager {
    private Reporter reporter;
    private Reporter reporter2;

    public QuestionManager (Reporter reporter, Reporter reporter2) {
        this.reporter = reporter;
        this.reporter2 = reporter2;
    }

    public List<List<String>> getBestOptionsM (int amountOfQuestions, int amountOfOptions) throws UserRecoverableAuthIOException {
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
        List<String> options = getOptions(reporter);
        List<List<String>> result = new ArrayList<>();

        Random random = new Random();

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
        }



        return result;
    }

    public List<List<String>> getBestOptionsL (int amountOfQuestions, int amountOfOptions) throws UserRecoverableAuthIOException {
        List<String> options = getOptions(reporter2);
        List<List<String>> result = new ArrayList<>();

        Random random = new Random();

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
        }



        return result;
    }

    private List<String> getOptions (Reporter reporter) throws UserRecoverableAuthIOException {
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
