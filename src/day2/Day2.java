package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Day2 {

    public static void main(String[] args) throws IOException {
        int part1 = 0;
        int part2 = 0;
        try (Stream<String> stream = Files.lines(Paths.get("src/day2/input"))) {
            for (String line : stream.toList()) {
                part1 += process(line);
                part2 += process2(line);
            }
        }
        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);
    }

    private static int process(String line) {
        boolean isGamePossible = true;
        int maxRedCubes = 12;
        int maxGreenCubes = 13;
        int maxBlueCubes = 14;
        String[] gameSplit = line.split(": ");
        int gameId = Integer.parseInt(gameSplit[0].split(" ")[1]);
        String[] sets = gameSplit[1].split("; ");
        for (String set: sets) {
            String[] setSplit = set.split(", ");
            for (String revealCube : setSplit) {
                String[] revealCubeSplit = revealCube.split(" ");
                int cubeCount = Integer.parseInt(revealCubeSplit[0]);
                String cubeColor = revealCubeSplit[1];
                switch (cubeColor) {
                    case "red" -> {
                        if (cubeCount > maxRedCubes) {
                            isGamePossible = false;
                        }
                    }
                    case "green" -> {
                        if (cubeCount > maxGreenCubes) {
                            isGamePossible = false;
                        }
                    }
                    case "blue" -> {
                        if (cubeCount > maxBlueCubes) {
                            isGamePossible = false;
                        }
                    }
                }
            }
        }
        if (isGamePossible){
            return gameId;
        } else {
            return 0;
        }
    }

    private static int process2(String line) {
        int maxRedCubes = 0;
        int maxGreenCubes = 0;
        int maxBlueCubes = 0;
        String[] gameSplit = line.split(": ");
        String[] sets = gameSplit[1].split("; ");
        for (String set: sets) {
            String[] setSplit = set.split(", ");
            for (String revealCube : setSplit) {
                String[] revealCubeSplit = revealCube.split(" ");
                int cubeCount = Integer.parseInt(revealCubeSplit[0]);
                String cubeColor = revealCubeSplit[1];
                switch (cubeColor) {
                    case "red" -> {
                        if (cubeCount > maxRedCubes) {
                            maxRedCubes = cubeCount;
                        }
                    }
                    case "green" -> {
                        if (cubeCount > maxGreenCubes) {
                            maxGreenCubes = cubeCount;
                        }
                    }
                    case "blue" -> {
                        if (cubeCount > maxBlueCubes) {
                            maxBlueCubes = cubeCount;
                        }
                    }
                }
            }
        }
        return maxRedCubes * maxGreenCubes * maxBlueCubes;
    }
}
