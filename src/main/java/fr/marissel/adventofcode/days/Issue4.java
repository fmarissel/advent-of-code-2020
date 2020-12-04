package fr.marissel.adventofcode.days;

import fr.marissel.adventofcode.days.Issue4.Passport;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class Issue4 extends Issue<List<Passport>> {

  public Issue4() {
    super(4);
  }

  @Override
  List<Passport> getInput(String data) {
    return Arrays.stream(data.split("\n\n"))
        .map(Passport::new)
        .collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<Passport> input) {
    return input.stream().filter(passport -> passport.isValid(true)).count() + "";
  }

  @Override
  String computePartTwo(List<Passport> input) {
    return input.stream().filter(passport -> passport.isValidStrict()).count() + "";
  }

  public class Passport {

    public Passport(String raw) {
      this.raw = raw;
      data = Arrays.stream(raw.replaceAll("\n", " ").split(" "))
          .collect(Collectors.toMap(s -> s.split(":")[0], s -> s.split(":")[1]));
    }

    public String raw;
    public Map<String, String> data;

    public boolean isValid(boolean cidOpt) {
      return data.containsKey("byr")
          && data.containsKey("iyr")
          && data.containsKey("eyr")
          && data.containsKey("hgt")
          && data.containsKey("hcl")
          && data.containsKey("ecl")
          && data.containsKey("pid")
          && (cidOpt || data.containsKey("cid"));
    }

    public boolean isValidStrict() {
      return isValid(true)
          && isYearValid(data.get("byr"), 1920, 2002)
          && isYearValid(data.get("iyr"), 2010, 2020)
          && isYearValid(data.get("eyr"), 2020, 2030)
          && isHeightValid(data.get("hgt"))
          && isHexa(data.get("hcl"))
          && isEyeColor(data.get("ecl"))
          && idDigits(data.get("pid"), 9);
    }

    private boolean isYearValid(String input, int min, int max) {
      if (input != null && StringUtils.isNumeric(input)) {
        int year = Integer.parseInt(input);
        return year >= min && year <= max;
      }
      return false;
    }

    private boolean isHeightValid(String input) {
      if (input != null && StringUtils.endsWithAny(input, "cm", "in")) {
        var isCm = StringUtils.endsWith(input, "cm");
        var height = input.replace("cm", "").replace("in", "");
        if (StringUtils.isNumeric(height)) {
          int num = Integer.parseInt(height);
          if (isCm) {
            return num >= 150 && num <= 193;
          } else {
            return num >= 59 && num <= 76;
          }
        }
      }
      return false;
    }

    private boolean isHexa(String input) {
      return input != null && input.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }

    private boolean isEyeColor(String input) {
      return Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(input);
    }

    private boolean idDigits(String input, int length) {
      return input != null && StringUtils.isNumeric(input) && input.length() == length;
    }
  }

}
