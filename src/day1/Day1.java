package day1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {

    public static void main(String[] args) throws IOException {
        int part1 = 0;
        int part2 = 0;
        try (Stream<String> stream = Files.lines(Paths.get("src/day1/input"))) {
            for (String line : stream.toList()) {
                part1 += process(line, "[1-9]", false);
                part2 += process(line, "(?=([1-9]|one|two|three|four|five|six|seven|eight|nine))", true);
            }
        }
        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);
    }

    private static int process(String line, String regex, boolean normalize) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        List<String> matches = new LinkedList<>();
        while (matcher.find()) {
            if (normalize) {
                matches.add(matcher.group(1));
            } else {
                matches.add(matcher.group(0));
            }
        }
        String numericStr;
        if (normalize) {
            numericStr = normalizeDigit(matches.get(0)) + normalizeDigit(matches.get(matches.size()-1));
        } else {
           numericStr  = matches.get(0) + matches.get(matches.size()-1);
        }
        return Integer.parseInt(numericStr);
    }

    private static String normalizeDigit(String digit) {
        Map<String, String> normalizeMap = Stream.of(new String[][] {
                { "1", "1" },
                { "2", "2" },
                { "3", "3" },
                { "4", "4" },
                { "5", "5" },
                { "6", "6" },
                { "7", "7" },
                { "8", "8" },
                { "9", "9" },
                { "one", "1" },
                { "two", "2" },
                { "three", "3" },
                { "four", "4" },
                { "five", "5" },
                { "six", "6" },
                { "seven", "7" },
                { "eight", "8" },
                { "nine", "9" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        return normalizeMap.get(digit);
    }
}
