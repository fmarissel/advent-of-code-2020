package fr.marissel.adventofcode.days;

import fr.marissel.adventofcode.days.Issue12.Instruction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Issue12 extends Issue<List<Instruction>> {

  public Issue12() {
    super(12);
  }

  @Override
  List<Instruction> getInput(String data) {
    return Arrays.stream(data.split("\n")).map(Instruction::new).collect(Collectors.toList());
  }

  @Override
  String computePartOne(List<Instruction> input) {

    List<Position> histo = new ArrayList<>();
    var current = new Position(0, 0, Direction.E);
    histo.add(current);

    for (Instruction inst : input) {
      current = next(current, inst);
      histo.add(current);
    }

    return current.getMD() + "";
  }

  @Override
  String computePartTwo(List<Instruction> input) {

    List<Position> histo = new ArrayList<>();
    var current = new Position(0, 0, Direction.E);
    var waypoint = new Position(10, 1, Direction.E);
    histo.add(current);

    for (Instruction inst : input) {
      waypoint = nextWaypoint(waypoint, inst);
      current = next2(current, waypoint, inst);
      histo.add(current);
    }

    return current.getMD() + "";
  }

  public class Position {

    public Position(int east, int north, Direction dir) {
      this.east = east;
      this.north = north;
      this.dir = dir;
    }

    public int east;
    public int north;
    public Direction dir;

    public int getMD() {
      return Math.abs(east) + Math.abs(north);
    }
  }

  private Position next(Position current, Instruction inst) {

    int east = current.east, north = current.north;
    Direction dir = current.dir;

    switch (inst.action) {
      case "N":
        north += inst.value;
        break;
      case "S":
        north -= inst.value;
        break;
      case "E":
        east += inst.value;
        break;
      case "W":
        east -= inst.value;
        break;
      case "L":
        dir = Direction.fromDir((((dir.dir - (inst.value / 90)) % 4) + 4) % 4);
        break;
      case "R":
        dir = Direction.fromDir((dir.dir + (inst.value / 90)) % 4);
        break;
      case "F":
        return next(current, new Instruction(current.dir.toString() + inst.value));
    }

    return new Position(east, north, dir);
  }

  private int nextEast(int east, int north, int dir) {
    if (dir == 1) {
      return north;
    } else if (dir == 2) {
      return Math.negateExact(east);
    } else if (dir == 3) {
      return Math.negateExact(north);
    }
    return east;
  }

  private int nextNorth(int east, int north, int dir) {
    if (dir == 1) {
      return Math.negateExact(east);
    } else if (dir == 2) {
      return Math.negateExact(north);
    } else if (dir == 3) {
      return east;
    }
    return north;
  }

  private Position next2(Position current, Position waypoint, Instruction inst) {

    int east = current.east, north = current.north;
    Direction dir = current.dir;

    switch (inst.action) {
      case "F":
        east += waypoint.east * inst.value;
        north += waypoint.north * inst.value;
        break;
    }

    return new Position(east, north, dir);
  }

  private Position nextWaypoint(Position current, Instruction inst) {

    int east = current.east, north = current.north;
    Direction dir = current.dir;

    switch (inst.action) {
      case "N":
        north += inst.value;
        break;
      case "S":
        north -= inst.value;
        break;
      case "E":
        east += inst.value;
        break;
      case "W":
        east -= inst.value;
        break;
      case "L":
        north = nextNorth(current.east, current.north, (360 - inst.value) / 90);
        east = nextEast(current.east, current.north, (360 - inst.value) / 90);
        break;
      case "R":
        north = nextNorth(current.east, current.north, inst.value / 90);
        east = nextEast(current.east, current.north, inst.value / 90);
        break;
    }

    return new Position(east, north, dir);
  }

  public class Instruction {

    public String action;
    public int value;

    public Instruction(String seq) {
      this.action = seq.substring(0, 1);
      this.value = Integer.parseInt(seq.substring(1));
    }
  }

  public enum Direction {
    N(0), E(1), S(2), W(3);

    Direction(int dir) {
      this.dir = dir;
    }

    public int dir;

    public static Direction fromDir(int dir) {
      for (Direction d : Direction.values()) {
        if (d.dir == dir) {
          return d;
        }
      }
      throw new IllegalArgumentException();
    }
  }
}
