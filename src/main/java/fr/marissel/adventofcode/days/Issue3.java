package fr.marissel.adventofcode.days;

import java.util.Arrays;

public class Issue3 extends Issue<Boolean[][]> {

  public Issue3() {
    super(3);
  }

  @Override
  Boolean[][] getInput(String data) {
    var input = Arrays.stream(data.split("\n"))
        .map(s -> {
          var charArray = s.toCharArray();
          var boolArray = new Boolean[charArray.length];
          for (int i = 0; i < charArray.length; i++) {
            boolArray[i] = charArray[i] == '#';
          }
          return boolArray;
        })
        .toArray(Boolean[][]::new);

    return input;
  }

  @Override
  String computePartOne(Boolean[][] input) {

    return compute(input, 3, 1) + "";
  }

  @Override
  String computePartTwo(Boolean[][] input) {
    return compute(input, 1, 1)
        * compute(input, 3, 1)
        * compute(input, 5, 1)
        * compute(input, 7, 1)
        * compute(input, 1, 2)
        + "";
  }

  private int compute(Boolean[][] input, int right, int bottom) {

    var limit = input.length;
    var modulo = input[0].length;
    var i = right;
    var j = bottom;
    var count = 0;

    while (j < limit) {

      if (input[j][i % modulo]) {
        count++;
      }

      i += right;
      j += bottom;
    }

    return count;
  }
}
