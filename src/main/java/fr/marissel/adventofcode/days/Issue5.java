package fr.marissel.adventofcode.days;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Issue5 extends Issue<List<String>> {


  public Issue5() {
    super(5);
  }

  @Override
  List<String> getInput(String data) {
    return Arrays.stream(data.split("\n"))
        .collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<String> input) {
    return input.stream().map(this::getSeatId).mapToInt(s -> s).max().orElseThrow(NoSuchElementException::new) + "";
  }

  @Override
  String computePartTwo(List<String> input) {

    List<Integer> seatList = input.stream().map(this::getSeatId).collect(Collectors.toList());

    for (int i = 0; i < 128; i++) {
      for (int j = 0; j < 8; j++) {
        Integer seatId = i * 8 + j;
        if (!seatList.contains(seatId) && seatList.contains(seatId + 1) && seatList.contains(seatId - 1)) {
          return seatId + "";
        }
      }
    }

    return "0";
  }

  private int getSeatId(String seq) {
    return getIndex(seq.substring(0, 7), 0, 127) * 8 + getIndex(seq.substring(7, 10), 0, 7);
  }

  private int getIndex(String seq, int start, int end) {

    if (seq.length() == 0) {
      return start;
    } else {
      int middle = (end - start + 1) / 2;
      if (upper(seq.substring(0, 1))) {
        return getIndex(seq.substring(1), start + middle, end);
      } else {
        return getIndex(seq.substring(1), start, end - middle);
      }
    }
  }

  public boolean upper(String chara) {
    return chara.equals("B") || chara.equals("R");
  }
}
