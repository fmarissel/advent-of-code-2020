package fr.marissel.adventofcode.days;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Issue9 extends Issue<List<BigInteger>> {

  public Issue9() {
    super(9);
  }

  @Override
  List<BigInteger> getInput(String data) {
    return Arrays.stream(data.split("\n")).map(BigInteger::new).collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<BigInteger> input) {

    int prefix = 25;
    for (int i = prefix; i < input.size(); i++) {
      if (!isValid(input, i, prefix)) {
        return input.get(i) + "";
      }
    }
    return "";

  }

  @Override
  String computePartTwo(List<BigInteger> input) {

    BigInteger invalid = BigInteger.ZERO;
    int prefix = 25;
    for (int i = prefix; i < input.size(); i++) {
      if (!isValid(input, i, prefix)) {
        invalid = input.get(i);
        break;
      }
    }

    for (int i = 0; i < input.size(); i++) {
      for (int j = i + 1; j < input.size(); j++) {
        BigInteger sum = BigInteger.ZERO;
        List<BigInteger> range = new ArrayList<>();

        for (int k = i; k <= j; k++) {
          sum = sum.add(input.get(k));
          range.add(input.get(k));
        }
        if (sum.equals(invalid)) {
          return range.stream().min(BigInteger::compareTo).get().add(range.stream().max(BigInteger::compareTo).get())
              .toString();
        } else if (invalid.compareTo(sum) < 0) {
          break;
        }
      }
    }
    return null;
  }

  private boolean isValid(List<BigInteger> input, int index, int size) {

    for (int i = index - size; i < index - 1; i++) {
      for (int j = i + 1; j < index; j++) {
        if (!input.get(i).equals(input.get(j)) && input.get(i).add(input.get(j)).equals(input.get(index))) {
          return true;
        }
      }
    }

    return false;
  }

}
