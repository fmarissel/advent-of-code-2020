package fr.marissel.adventofcode.days;

import java.util.Arrays;

public class Issue1 extends Issue<Integer[]> {

  public Issue1() {
    super(1);
  }

  @Override
  Integer[] getInput(String data) {
    return Arrays.stream(data.split("\n"))
        .map(Integer::valueOf)
        .toArray(Integer[]::new);
  }

  @Override
  String computePartOne(Integer[] input) {
    for (int i = 0; i < input.length - 1; i++) {
      for (int j = i + 1; j < input.length; j++) {

        if (input[i] + input[j] == 2020) {
          return String.valueOf(input[i] * input[j]);
        }
      }
    }
    return null;
  }

  @Override
  String computePartTwo(Integer[] input) {
    for (int i = 0; i < input.length - 2; i++) {
      for (int j = i + 1; j < input.length - 1; j++) {
        for (int k = j + 1; k < input.length; k++) {
          if (input[i] + input[j] + input[k] == 2020) {
            return String.valueOf(input[i] * input[j] * input[k]);
          }
        }
      }
    }
    return null;
  }
}
