package fr.marissel.adventofcode.days;

import fr.marissel.adventofcode.days.Issue2.Input2;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Issue2 extends Issue<List<Input2>> {

  public Issue2() {
    super(2);
  }

  @Override
  List<Input2> getInput(String data) {
    return Arrays.stream(data.split("\n"))
        .map(Input2::new)
        .collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<Input2> input) {
    return input.stream().filter(this::isValid).count() + "";
  }

  @Override
  String computePartTwo(List<Input2> input) {
    return input.stream().filter(this::isValid2).count() + "";
  }

  private boolean isValid(Input2 input2) {
    long count = input2.password.chars().filter(ch -> ch == input2.policy.letter).count();
    return count >= input2.policy.min && count <= input2.policy.max;
  }

  private boolean isValid2(Input2 input2) {

    return input2.password.charAt(input2.policy.min - 1) == input2.policy.letter
        ^ input2.password.charAt(input2.policy.max - 1) == input2.policy.letter;
  }

  public class Input2 {

    public Input2(String str) {

      var split = str.split(":");
      var policyStr = split[0].trim().split(" ");
      this.policy = new Policy();
      this.policy.min = Integer.parseInt(policyStr[0].trim().split("-")[0]);
      this.policy.max = Integer.parseInt(policyStr[0].trim().split("-")[1]);
      this.policy.letter = policyStr[1].trim().charAt(0);
      this.password = split[1].trim();
    }

    public Policy policy;
    public String password;
  }

  public class Policy {

    public int min, max;
    public char letter;
  }
}
