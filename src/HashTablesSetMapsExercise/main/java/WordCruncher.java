package HashTablesSetMapsExercise.main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class WordCruncher {
    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));


        List<String> words = Arrays.stream(scanner.readLine()
                        .split(",\\s"))
                .collect(Collectors.toList());

        String target = scanner.readLine();
        words = words.stream().filter(target::contains).collect(Collectors.toList());

        Map<Integer, TreeSet<String>> tree = new LinkedHashMap<>();
        Map<String, Integer> wordsByCount = new LinkedHashMap<>();


        for (String word : words) {
            int index = target.indexOf(word);
            wordsByCount.putIfAbsent(word, 0);
            while (index != -1) {
                if (!tree.containsKey(index)) {
                    tree.put(index, new TreeSet<>());
                }
                wordsByCount.putIfAbsent(word, wordsByCount.get(word) + 1);
                tree.get(index).add(word);
                index = target.indexOf(word, index + 1);
            }
        }

        combine(0, wordsByCount, target, tree, new ArrayList<>());
    }

    private static void combine(Integer index, Map<String, Integer> wordsByCount, String target, Map<Integer, TreeSet<String>> tree, List<String> result) {

        if (index >= target.length()) {
            System.out.println(String.join(" ", result));
        } else {
            if (tree.containsKey(index)) {
                for (String wordAtIndex : tree.get(index)) {
                    if (wordsByCount.get(wordAtIndex) > 0) {
                        result.add(wordAtIndex);
                        wordsByCount.put(wordAtIndex, wordsByCount.get(wordAtIndex) - 1);
                        combine(index + wordAtIndex.length(), wordsByCount, target, tree, result);
                        result.remove(wordAtIndex);
                    }
                }
            }
        }
    }
}
