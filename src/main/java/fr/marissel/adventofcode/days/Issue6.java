package fr.marissel.adventofcode.days;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Issue6 extends Issue<List<String>> {

  public Issue6() {
    super(6);
  }

  @Override
  List<String> getInput(String data) {
    return Arrays.stream(data.split("\n\n"))
        .collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<String> input) {
    return input.stream().map(this::countGroup1).mapToInt(Integer::intValue).sum() + "";
  }

  @Override
  String computePartTwo(List<String> input) {
    return input.stream().map(this::countGroup2).mapToInt(Integer::intValue).sum() + "";
  }

  private int countGroup1(String group) {
    StringBuilder sb = new StringBuilder();
    group.replaceAll("\n", "").chars().distinct().forEach(c -> sb.append((char) c));
    return sb.length();
  }

  private int countGroup2(String group) {
    var people = Arrays.stream(group.split("\n")).collect(Collectors.toList());

    if (people.size() == 1) {
      return people.get(0).length();
    }

    int result = 0;
    var first = people.get(0);
    for (char c : first.toCharArray()) {
      if (people.stream().allMatch(str -> str.indexOf(c) >= 0)) {
        result += 1;
      }
    }
    return result;
  }
}
