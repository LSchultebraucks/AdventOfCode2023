package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day3 {

    public static void main(String[] args) throws IOException {
        int part1 = 0;
        int part2 = 0;
        try (Stream<String> stream = Files.lines(Paths.get("src/day3/input"))) {
            String[][] engine = new String[140][140];
            int rowIndex = 0;
            for (String line : stream.toList()) {
                engine[rowIndex] = line.split("");
                rowIndex++;
            }
            part1 += process(engine);
            part2 += process2(engine);
        }
        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);
    }

    private static int process(String[][] engine) {
        int result = 0;
        StringBuilder currentNumberStr = new StringBuilder();
        for (int rowIndex = 0; rowIndex < engine.length; rowIndex++) {
            if (!currentNumberStr.isEmpty()) { // wenn zeile mit nummer endet
                if (isNumberAdjacentToAnySymbol(currentNumberStr.toString(), engine, rowIndex-1, 139)) {
                    result +=Integer.parseInt(currentNumberStr.toString());
                }
            }
            currentNumberStr = new StringBuilder();
            for (int columnIndex = 0; columnIndex < engine[rowIndex].length; columnIndex++) {
                if (isNumeric(engine[rowIndex][columnIndex])) {
                    currentNumberStr.append(engine[rowIndex][columnIndex]);
                } else {
                    if (!currentNumberStr.isEmpty()) {
                        if (isNumberAdjacentToAnySymbol(currentNumberStr.toString(), engine, rowIndex, columnIndex)) {
                            result +=Integer.parseInt(currentNumberStr.toString());
                        }
                        currentNumberStr = new StringBuilder();
                    }
                }
            }
        }
        return result;
    }

    private static boolean isNumberAdjacentToAnySymbol(String numStr, String[][] engine, int rowIndexOffset, int columnIndexOffset) {
        for (int rowIndex = rowIndexOffset - 1; rowIndex <= rowIndexOffset + 1; rowIndex++) {
            if (rowIndex >= 0 && rowIndex <= 139) {
                for (int columnIndex = columnIndexOffset - numStr.length() - 1; columnIndex <= columnIndexOffset; columnIndex++) {
                    if (columnIndex >= 0 && columnIndex <= 139) {
                        if (!isNumeric(engine[rowIndex][columnIndex]) && !Objects.equals(engine[rowIndex][columnIndex], ".")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isNumeric(String strNum) {
        return Pattern.compile("[0-9]").matcher(strNum).matches();
    }

    private static int process2(String[][] engine) {
        int result = 0;
        StringBuilder currentNumberStr = new StringBuilder();
        Map<GearKey, List<Integer>> gearKeyListMap = new HashMap<>();
        for (int rowIndex = 0; rowIndex < engine.length; rowIndex++) {
            if (!currentNumberStr.isEmpty()) { // wenn zeile mit nummer endet
                handleNumberAdjacentToGearSymbol(currentNumberStr.toString(), engine, rowIndex-1, 139, gearKeyListMap);
            }
            currentNumberStr = new StringBuilder();
            for (int columnIndex = 0; columnIndex < engine[rowIndex].length; columnIndex++) {
                if (isNumeric(engine[rowIndex][columnIndex])) {
                    currentNumberStr.append(engine[rowIndex][columnIndex]);
                } else {
                    if (!currentNumberStr.isEmpty()) {
                        handleNumberAdjacentToGearSymbol(currentNumberStr.toString(), engine, rowIndex, columnIndex, gearKeyListMap);
                        currentNumberStr = new StringBuilder();
                    }
                }
            }
        }
        for (Map.Entry<GearKey, List<Integer>> set: gearKeyListMap.entrySet()) {
            if (set.getValue().size() == 2) {
                result += set.getValue().get(0) * set.getValue().get(1);
            }
        }
        return result;
    }

    private static void handleNumberAdjacentToGearSymbol(String numStr, String[][] engine, int rowIndexOffset, int columnIndexOffset, Map<GearKey,List<Integer>> gearKeyListMap) {
        for (int rowIndex = rowIndexOffset - 1; rowIndex <= rowIndexOffset + 1; rowIndex++) {
            if (rowIndex >= 0 && rowIndex <= 139) {
                for (int columnIndex = columnIndexOffset - numStr.length() - 1; columnIndex <= columnIndexOffset; columnIndex++) {
                    if (columnIndex >= 0 && columnIndex <= 139) {
                        if (Objects.equals(engine[rowIndex][columnIndex], "*")) {
                            List<Integer> value = gearKeyListMap.getOrDefault(new GearKey(rowIndex, columnIndex), new LinkedList<>());
                            value.add(Integer.parseInt(numStr));
                            gearKeyListMap.put(new GearKey(rowIndex, columnIndex), value);
                        }
                    }
                }
            }
        }
    }
}
