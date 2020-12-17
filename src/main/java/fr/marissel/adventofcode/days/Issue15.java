package fr.marissel.adventofcode.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Issue15 extends Issue<List<Integer>> {

  public Issue15() {
    super(15);
  }

  @Override
  List<Integer> getInput(String data) {
    return Arrays.stream(data.split("\n")[0].split(",")).map(Integer::parseInt).collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<Integer> input) {

    int turn = input.size();
    Integer next = 0;
    while (input.size() != 2020) {

      int current = input.get(turn - 1);
      long count = input.stream().filter(val -> val.equals(current)).count();

      if (count == 1l) {
        next = 0;
      } else {
        next = turn - (input.subList(0, turn - 1).lastIndexOf(current) + 1);
      }

      input.add(next);
      turn++;
    }

    return next + "";
  }

  @Override
  String computePartTwo(List<Integer> input) {

    Map<Integer, Integer> indexes = new HashMap<>();
    for (int i = 0; i < input.size() - 1; i++) {
      indexes.put(input.get(i), i + 1);
    }

    int turn = input.size();
    int last = input.get(input.size() - 1);
    int next = 0;

    while (turn != 30000000) {

      if (indexes.containsKey(last)) {
        next = turn - indexes.get(last);
      } else {
        next = 0;
      }
      indexes.put(last, turn);
      turn = turn + 1;
      last = next;
    }

    return next + "";
  }

  private Integer next(List<Integer> input) {

    int turn = input.size();

    Integer current = input.get(turn - 1);
    long count = input.stream().filter(val -> val.equals(current)).count();

    Integer next;
    if (count == 1l) {
      next = 0;
    } else {
      next = turn - (input.subList(0, turn - 1).lastIndexOf(current) + 1);
    }
    return next;
  }
}
