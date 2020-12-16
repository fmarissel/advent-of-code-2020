package fr.marissel.adventofcode.days;

import fr.marissel.adventofcode.days.Issue16.Input16;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Range;


public class Issue16 extends Issue<Input16> {

  public Issue16() {
    super(16);
  }

  @Override
  Input16 getInput(String data) {
    var content = data.split("\n\n");
    var rules = Arrays.stream(content[0].split("\n"))
        .map(Rule::new)
        .collect(Collectors.toList());

    var mine = new Ticket(content[1].split("\n")[1]);

    var others = Arrays.stream(content[2].split("\n"))
        .skip(1)
        .map(Ticket::new)
        .collect(Collectors.toList());

    return new Input16(rules, mine, others);
  }

  @Override
  String computePartOne(Input16 input) {
    return input.others.stream()
        .mapToInt(ticket -> ticket.sumInvalidsAnyRules(input.rules))
        .sum() + "";
  }

  @Override
  String computePartTwo(Input16 input) {

    var valids = input.others.stream().filter(ticket -> ticket.isValidAnyRule(input.rules))
        .collect(Collectors.toList());

    var possibilities = new ArrayList<List<Rule>>();
    for (int idx = 0; idx < input.mine.items.size(); idx++) {
      var forIndex = input.rules;
      for (int line = 0; line < valids.size(); line++) {
        forIndex = valids.get(line).items.get(idx).getValidRules(forIndex);
      }
      possibilities.add(forIndex);
    }

    var found = new ArrayList<Rule>();
    for (int idx = 0; idx < input.mine.items.size(); idx++) {
      found.add(null);
    }

    while (possibilities.stream().anyMatch(rules -> rules.size() != 1)) {
      for (int idx = 0; idx < input.mine.items.size(); idx++) {
        if (possibilities.get(idx).size() == 1) {
          var rule = possibilities.get(idx).get(0);
          if (!found.contains(rule)) {
            found.set(idx, rule);
            for (int j = 0; j < input.mine.items.size(); j++) {
              if (j != idx) {
                possibilities.get(j).remove(rule);
              }
            }
            break;
          }
        }
      }
    }
    var result = possibilities.stream().map(rules -> rules.get(0)).collect(Collectors.toList());

    Long res = 1l;
    for (int idx = 0; idx < result.size(); idx++) {
      if (result.get(idx).field.startsWith("departure")) {
        res = Math.multiplyExact(res, input.mine.items.get(idx).value);
      }
    }

    return res + "";
  }

  public class Input16 {

    public Input16(List<Rule> rules, Ticket mine, List<Ticket> others) {
      this.rules = rules;
      this.mine = mine;
      this.others = others;
    }

    public List<Rule> rules;
    public Ticket mine;
    public List<Ticket> others;
  }

  public class Ticket {

    public List<TicketItem> items = new ArrayList<>();

    public Ticket(String line) {
      var tmp = line.split(",");
      for (int i = 0; i < tmp.length; i++) {
        var item = new TicketItem();
        item.index = i;
        item.value = Integer.parseInt(tmp[i]);
        items.add(item);
      }
    }

    public boolean isValidAnyRule(List<Rule> rules) {
      return items.stream().allMatch(item -> item.isValidAnyRule(rules));
    }

    public boolean isValidForRules(List<Rule> rule) {
      return items.stream().allMatch(item -> item.isValidForRule(rule.get(item.index)));
    }

    public List<TicketItem> getInvalidsAnyRules(List<Rule> rules) {
      return items.stream().filter(item -> !item.isValidAnyRule(rules)).collect(Collectors.toList());
    }

    public int sumInvalidsAnyRules(List<Rule> rules) {
      return getInvalidsAnyRules(rules).stream().mapToInt(item -> item.value).sum();
    }
  }

  public class TicketItem {

    public int index;
    public String field;
    public Integer value;

    public boolean isValidAnyRule(List<Rule> rules) {

      return rules.stream().anyMatch(rule -> rule.range1.contains(value) || rule.range2.contains(value));
    }

    public boolean isValidForRule(Rule rule) {

      return rule.range1.contains(value) || rule.range2.contains(value);
    }

    public List<Rule> getValidRules(List<Rule> rules) {
      return rules.stream().filter(rule -> isValidForRule(rule)).collect(Collectors.toList());
    }
  }

  public class Rule {

    public String field;
    public Range<Integer> range1;
    public Range<Integer> range2;

    public Rule(String line) {
      var tmp = line.split(": ");
      this.field = tmp[0];

      tmp = tmp[1].split(" or ");
      this.range1 = Range.between(Integer.parseInt(tmp[0].split("-")[0]), Integer.parseInt(tmp[0].split("-")[1]));
      this.range2 = Range.between(Integer.parseInt(tmp[1].split("-")[0]), Integer.parseInt(tmp[1].split("-")[1]));
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Rule rule = (Rule) o;
      return Objects.equals(field, rule.field);
    }

    @Override
    public int hashCode() {
      return Objects.hash(field);
    }
  }
}
