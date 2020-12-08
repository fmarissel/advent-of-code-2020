package fr.marissel.adventofcode.days;

import fr.marissel.adventofcode.days.Issue8.Instruction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Issue8 extends Issue<List<Instruction>> {

  public Issue8() {
    super(8);
  }

  @Override
  List<Instruction> getInput(String data) {
    return Arrays.stream(data.split("\n"))
        .map(s -> new Instruction(s.split(" ")[0], Integer.parseInt(s.split(" ")[1])))
        .collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<Instruction> input) {
    var acc = 0;
    int i = 0;
    var steps = new HashSet<Integer>();

    while (!steps.contains(i) && i < input.size()) {
      var inst = input.get(i);
      steps.add(i);
      switch (inst.operation) {
        case "acc":
          acc += inst.argument;
          i += 1;
          break;
        case "jmp":
          i += inst.argument;
          break;
        case "nop":
          i += 1;
          break;
        default:
          throw new UnsupportedOperationException();
      }
    }

    return acc + "";
  }

  @Override
  String computePartTwo(List<Instruction> input) {

    int toReplace = 0;

    while (true) {

      toReplace = replaceNext(toReplace, input, "jmp");

      var acc = 0;
      int i = 0;
      var steps = new HashSet<Integer>();

      while (!steps.contains(i) && i < input.size()) {
        var inst = input.get(i);
        steps.add(i);
        switch (inst.operation) {
          case "acc":
            acc += inst.argument;
            i += 1;
            break;
          case "jmp":
            i += inst.argument;
            break;
          case "nop":
            i += 1;
            break;
          default:
            throw new UnsupportedOperationException();
        }
      }
      if (i >= input.size()) {
        return acc + "";
      }
    }
  }

  private int replaceNext(int ind, List<Instruction> input, String toReplace) {

    if (ind != 0) {
      input.get(ind - 1).operation = toReplace;
    }

    for (int i = ind; i < input.size(); i++) {
      if (input.get(i).operation.equals(toReplace)) {
        input.get(i).operation = (toReplace.equals("jmp") ? "nop" : "jmp");
        return i + 1;
      }
    }
    return input.size();
  }

  public class Instruction {

    public String operation;
    public int argument;

    public Instruction(String op, int arg) {
      this.operation = op;
      this.argument = arg;
    }
  }
}
