package fr.marissel.adventofcode.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Issue10 extends Issue<List<Integer>> {

  public Issue10() {
    super(10);
  }

  @Override
  List<Integer> getInput(String data) {
    return Arrays.stream(data.split("\n")).map(s -> Integer.parseInt(s)).sorted().collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<Integer> input) {

    Integer last = input.get(input.size() - 1) + 3;
    Integer first = 0;
    int jolt1 = 0, jolt3 = 0;

    for (int i = input.size() - 1; i >= 0; i--) {
      var diff = last - input.get(i);
      jolt1 += (diff == 1 ? 1 : 0);
      jolt3 += (diff == 3 ? 1 : 0);
      last = input.get(i);
    }
    var diff = input.get(0) - first;
    jolt1 += (diff == 1 ? 1 : 0);
    jolt3 += (diff == 3 ? 1 : 0);

    return jolt1 * jolt3 + "";
  }

  @Override
  String computePartTwo(List<Integer> input) {

    Integer last = input.get(input.size() - 1) + 3;
    input.add(0, 0);
    input.add(input.size(), last);
    Map<Integer, Long> sizes = new HashMap<>();

    for (Integer value : input) {
      var size = count(value, input, sizes);
      sizes.put(value, size);
    }

    return sizes.get(last) + "";
  }

  public long count(int last, List<Integer> input, Map<Integer, Long> sizes) {

    long size = 0;
    if (sizes.containsKey(last)) {
      return sizes.get(last);
    } else if (last == 0) {
      return 1;
    } else {
      var previous = input.stream().filter(val -> val < last && val >= last - 3).collect(Collectors.toList());
      for (Integer prev : previous) {
        size += count(prev,
            input.stream().filter(integer -> integer.compareTo(prev) < 0).collect(Collectors.toList()), sizes);
      }
      return size;
    }
  }
}
