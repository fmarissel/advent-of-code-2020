package fr.marissel.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Issue14 extends Issue<List<String>> {

  public Issue14() {
    super(14);
  }

  @Override
  List<String> getInput(String data) {
    return Arrays.stream(data.split("\n")).collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<String> input) {

    char[] mask = new char[36];
    Map<Integer, Long> values = new HashMap<>();

    for (String line : input) {

      if (line.startsWith("mask")) {
        mask = line.split(" = ")[1].toCharArray();
      } else {
        var tmp = line.split(" = ");
        var idx = Integer.parseInt(tmp[0].substring(4, tmp[0].length() - 1));
        var val = Long.parseLong(tmp[1]);
        values.put(idx, applyMask(val, mask));
      }

    }

    return values.values().stream().reduce(Long::sum).get() + "";
  }

  @Override
  String computePartTwo(List<String> input) {

    char[] mask = new char[36];
    Map<Long, Long> values = new HashMap<>();

    for (String line : input) {

      if (line.startsWith("mask")) {
        mask = line.split(" = ")[1].toCharArray();
      } else {
        var tmp = line.split(" = ");
        var idx = Integer.parseInt(tmp[0].substring(4, tmp[0].length() - 1));
        var val = Long.parseLong(tmp[1]);

        var memories = applyMemoryMask(idx, mask);

        for (Long mem : memories) {
          values.put(mem, val);
        }
      }

    }

    return values.values().stream().reduce(Long::sum).get() + "";
  }

  public long applyMask(long val, char[] mask) {

    var res = longToBinary(val);
    var vLength = res.length;

    for (int i = 0; i < vLength; i++) {
      if (mask[36 - i - 1] != 'X') {
        res[vLength - i - 1] = mask[36 - i - 1];
      }
    }

    return binaryToLong(res);
  }

  private char[] longToBinary(long x) {
    var res = new char[36];
    var tmp = Long.toBinaryString(x).toCharArray();
    var length = tmp.length;

    for (int i = 0; i < 36 - length; i++) {
      res[i] = '0';
    }
    for (int i = 0; i < length; i++) {
      res[36 - i - 1] = tmp[length - i - 1];
    }

    return res;
  }

  private long binaryToLong(char[] binary) {
    return Long.parseLong(new String(binary), 2);
  }

  private String removeLeadingZeroes(String s) {
    int index;
    for (index = 0; index < s.length() - 1; index++) {
      if (s.charAt(index) != '0') {
        break;
      }
    }
    return s.substring(index);
  }

  public Long[] applyMemoryMask(long val, char[] mask) {

    var res = longToBinary(val);
    var vLength = res.length;
    var idxs = new ArrayList<Integer>();

    for (int i = 0; i < vLength; i++) {
      if (mask[36 - i - 1] != '0') {
        res[vLength - i - 1] = mask[36 - i - 1];
        if (mask[36 - i - 1] == 'X') {
          idxs.add(vLength - i - 1);
        }
      }
    }

    var combinations = (int) Math.pow(2, idxs.size());
    var result = new Long[combinations];

    for (int i = 0; i < combinations; i++) {
      var tmp = Arrays.copyOf(res, res.length);
      var comb = String.format("%" + idxs.size() + "s", Integer.toBinaryString(i)).replace(' ', '0').toCharArray();
      for (int j = 0; j < comb.length; j++) {
        tmp[idxs.get(j)] = comb[j];
      }
      result[i] = binaryToLong(tmp);
    }

    return result;

  }
}
