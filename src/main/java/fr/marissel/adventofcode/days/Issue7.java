package fr.marissel.adventofcode.days;

import fr.marissel.adventofcode.days.Issue7.Bag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Issue7 extends Issue<Set<Bag>> {

  public Issue7() {
    super(7);
  }

  @Override
  Set<Bag> getInput(String data) {

    return Arrays.stream(data.split("\n"))
        .map(Bag::new)
        .collect(Collectors.toSet());
  }

  @Override
  String computePartOne(Set<Bag> input) {
    var init = new Bag("shiny", "gold");

    var search = new HashSet<Bag>();
    search.add(init);

    var result = new HashSet<Bag>();

    while (!search.isEmpty()) {
      var newBags = new HashSet<Bag>();
      for (Bag b : search) {
        newBags.addAll(input.stream()
            .filter(bag -> bag.capacity.keySet().contains(b))
            .filter(bag -> !result.contains(bag))
            .filter(bag -> !newBags.contains(bag))
            .collect(Collectors.toSet()));

      }
      result.addAll(newBags);
      search.clear();
      search.addAll(newBags);
    }
    return result.size() + "";
  }

  @Override
  String computePartTwo(Set<Bag> input) {
    var shinyGold = new Bag("shiny", "gold");

    return getCapacity(shinyGold, input) + "";
  }

  private int getCapacity(Bag bag, Set<Bag> input) {
    bag = getBag(bag, input);

    if (bag.capacity.size() == 0) {
      return 0;
    } else {
      int res = 0;
      for (Entry<Bag, Integer> entry : bag.capacity.entrySet()) {
        res += entry.getValue() + (entry.getValue() * getCapacity(entry.getKey(), input));
      }
      return res;
    }
  }

  private Bag getBag(Bag bag, Set<Bag> input) {
    return input.stream().filter(b -> b.equals(bag)).findFirst().get();
  }

  public class Bag {

    public String brightness;
    public String color;
    public Map<Bag, Integer> capacity;

    public Bag(String str) {
      var arr = str.replaceAll("\\.", "")
          .replaceAll(" bags", "")
          .replaceAll(" bag", "")
          .split(" contain ");

      this.brightness = arr[0].split(" ")[0];
      this.color = arr[0].split(" ")[1];
      this.capacity = Arrays.stream(arr[1].split(", "))
          .map(s -> s.split(" "))
          .filter(strings -> strings.length == 3)
          .collect(
              Collectors.toMap(strings -> new Bag(strings[1], strings[2]), strings -> Integer.parseInt(strings[0])));
    }

    public Bag(String brightness, String color) {
      this.brightness = brightness;
      this.color = color;
    }

    private void setCapacity(String[] array) {

      Arrays.stream(array)
          .map(s -> s.split(" "))
          .collect(Collectors.toMap(strings -> strings[0], strings -> new Bag(strings[1], strings[2])));
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Bag bag = (Bag) o;
      return brightness.equals(bag.brightness) &&
          color.equals(bag.color);
    }

    @Override
    public int hashCode() {
      return Objects.hash(brightness, color);
    }
  }
}
