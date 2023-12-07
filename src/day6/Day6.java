package day6;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class Day6 {

    public static void main(String[] args) {
        List<Race> races = List.of(new Race(49, 356), new Race(87, 1378), new Race(78, 1502), new Race(95, 1882));
        System.out.println("Part1: " + process(races));
        System.out.println("Part2: " + processSingleBigRace(49877895,  356137815021882L));
    }

    private static long processSingleBigRace(int time, long distance) {
        long timeMid = time / 2;
        long left = 0;
        long right = timeMid;
        long min_hold_time = 0;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            long currentDistance = mid * (time - mid);
            if (currentDistance == distance) {
                return mid+1;
            } else if (currentDistance < distance) {
                left = mid + 1;
                min_hold_time = left;
            } else {
                right = mid - 1;
            }

        }
        return time - min_hold_time*2 + 1;
    }

    private static int process(List<Race> races) {
        int result = 1;
        List<Integer> numberOfWays = new LinkedList<>();
        for (Race race : races) {
            int possibleNumberOfWays = 0;
            for (int i = 0; i <= race.time; i++) {
                if (i * (race.time - i) > race.distance) {
                    possibleNumberOfWays++;
                }
            }
            numberOfWays.add(possibleNumberOfWays);
        }
        for (Integer numberOfWay : numberOfWays) {
            result *= numberOfWay;
        }
        return result;
    }

    record Race(int time, int distance) {
    }
}
