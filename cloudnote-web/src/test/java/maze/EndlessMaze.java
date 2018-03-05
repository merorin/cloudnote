package maze;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author guobin On date 2018/3/2.
 * @version 1.0
 * @since jdk 1.8
 */
public class EndlessMaze {

    static final String INIT_TYPE = "";
    static final String PORT_START = "-";
    static final String PORT_END = "+";
    private static final Port DEAD_PORT = new Port(new EndlessMaze(0), "ERROR", Direction.ERROR);

    enum Direction {

        /**
         * 方向是向迷宫内行进
         */
        IN,

        /**
         * 方向是向迷宫外行进
         */
        OUT,

        /**
         * 错误的行进方向
         */
        ERROR
    }

    private String type;

    private EndlessMaze parent;

    private final Map<String, EndlessMaze> children;

    private Map<String, List<Function<EndlessMaze, Port>>> ruleMap;

    private final int childrenCount;

    private final String name;

    public EndlessMaze(int childrenCount) {
        this(INIT_TYPE, null, childrenCount);
    }

    private EndlessMaze(String type, EndlessMaze parent, int childrenCount) {
        this.type = type;
        this.parent = parent;
        this.childrenCount = childrenCount;
        this.children = new HashMap<>(childrenCount);
        this.name = Optional.ofNullable(this.parent).map(EndlessMaze::getName).orElse("") + this.type;
    }


    public List<String> findTheWay(List<Rule> rules) {
        this.initRules(rules);

        Port currentPort, nextPort;
        currentPort = new Port(this, PORT_START, Direction.OUT);

        List<String> paths = new LinkedList<>();

        String lastPath = PORT_START;
        this.printPath(lastPath);
        paths.add(lastPath);
        Port uncheckedNextPort;
        while (!PORT_END.equals((uncheckedNextPort = this.getNextPort(currentPort)).port)) {
            if (DEAD_PORT.port.equals(uncheckedNextPort.port)) {
                // 这种情况下要重新寻路
                continue;
            }
            nextPort = uncheckedNextPort;
            currentPort = new Port(nextPort.maze, nextPort.port, nextPort.direction);
            this.printPath(nextPort.maze.getPortName(nextPort.port));
            paths.add(nextPort.maze.getPortName(nextPort.port));
            if (paths.size() > 10000) {
                throw new RuntimeException("The algorithm is so terrible!");
            }
        }

        this.printPath(PORT_END);
        paths.add(PORT_END);

        return paths;
    }

    public EndlessMaze getParent() {
        // 只有初始迷宫才不会有parent,初始迷宫是不应该被调用getParent方法的
        if (Objects.equals(INIT_TYPE, type)) {
            throw new NullPointerException("Initial maze cannot call the 'getParent()' method.");
        }
        return parent;
    }

    public EndlessMaze getChild(String type) {
        return children.computeIfAbsent(type, mazeType -> new EndlessMaze(mazeType, this, this.childrenCount));
    }

    private String getName() {
        return name;
    }

    private String getPortName(String port) {
        return this.buildPath(this.getName(), port);
    }

    private String buildPath(String mazeName, String port) {
        return mazeName + port;
    }

    private Port getNextPort(Port currentPort) {
        String queryKey = this.buildRuleKey(currentPort);
        return Optional.ofNullable(this.ruleMap.get(queryKey))
                .map(this::getRandomPortMapper)
                .map(mapper -> mapper.apply(currentPort.maze))
                .orElse(DEAD_PORT);
    }

    private void printPath(String path) {
        System.out.println(path);
        System.out.println("->");
    }

    private Function<EndlessMaze, Port> getRandomPortMapper(List<Function<EndlessMaze, Port>> list) {
        int index = new Random().nextInt(list.size());
        return list.get(index);
    }

    private void initRules(List<Rule> rules) {
        this.ruleMap = new HashMap<>(rules.size());
        for (Rule rule : rules) {
            String portKey = this.buildRuleKey(rule.srcType, rule.srcPort, rule.srcDirection);
            Function<EndlessMaze, Port> mapper = maze -> new Port(rule.mapper.apply(maze), rule.desPort, rule.desDirection);
            this.ruleMap.computeIfAbsent(portKey, key -> new ArrayList<>(2)).add(mapper);
        }
    }

    private String buildRuleKey(Port currentPort) {
        return this.buildRuleKey(currentPort.maze.type, currentPort.port, currentPort.direction);
    }

    private String buildRuleKey(String type, String port, Direction direction) {
        return Stream.of(type, port, direction.toString())
                .collect(Collectors.joining("."));
    }

    private static class Port {

        private EndlessMaze maze;

        private String port;

        private Direction direction;

        Port(EndlessMaze maze, String port, Direction direction) {
            this.maze = maze;
            this.port = port;
            this.direction = direction;
        }
    }



    static class Rule {

        private String srcType;

        private String srcPort;

        private Direction srcDirection;

        private Function<EndlessMaze, EndlessMaze> mapper;

        private String desPort;

        private Direction desDirection;

        Rule(String srcType,
             String srcPort,
             Direction srcDirection,
             Function<EndlessMaze, EndlessMaze> mapper,
             String desPort,
             Direction desDirection) {
            this.srcType = srcType;
            this.srcPort = srcPort;
            this.srcDirection = srcDirection;
            this.mapper = mapper;
            this.desPort = desPort;
            this.desDirection = desDirection;
        }
    }


}


