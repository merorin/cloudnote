package maze;

import java.util.*;

import static maze.EndlessMaze.*;
import static maze.EndlessMaze.Direction.*;

/**
 * Description:
 *
 * @author guobin On date 2018/3/3.
 * @version 1.0
 * @since jdk 1.8
 */
public class RectMazeCalculator {

    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    private static final String D = "D";

    private static final String PORT_ONE = "1";
    private static final String PORT_TWO = "2";
    private static final String PORT_THREE = "3";
    private static final String PORT_FOUR = "4";

    private static final List<Rule> RULE_LIST = new ArrayList<>(34);

    static {
        RULE_LIST.add(new Rule(INIT_TYPE, PORT_START, OUT, maze -> maze.getChild(A), PORT_THREE, IN));
        RULE_LIST.add(new Rule(A, PORT_ONE, IN, maze -> maze, PORT_TWO, OUT));
        RULE_LIST.add(new Rule(A, PORT_ONE, OUT, maze -> maze.getParent().getChild(B), PORT_ONE, IN));
        RULE_LIST.add(new Rule(A, PORT_TWO, IN, maze -> maze, PORT_ONE, OUT));
        RULE_LIST.add(new Rule(A, PORT_TWO, OUT, EndlessMaze::getParent, PORT_THREE, OUT));
        RULE_LIST.add(new Rule(A, PORT_TWO, OUT, maze -> maze.getParent().getChild(D), PORT_FOUR, IN));
        RULE_LIST.add(new Rule(A, PORT_THREE, IN, maze -> maze.getChild(A), PORT_TWO, IN));
        RULE_LIST.add(new Rule(A, PORT_THREE, IN, maze -> maze.getChild(D), PORT_FOUR, IN));
        RULE_LIST.add(new Rule(A, PORT_FOUR, IN, maze -> maze.getChild(D), PORT_THREE, IN));
        RULE_LIST.add(new Rule(A, PORT_FOUR, IN, maze -> maze.getChild(B), PORT_TWO, IN));
        RULE_LIST.add(new Rule(A, PORT_FOUR, OUT, maze -> maze.getParent().getChild(D), PORT_TWO, IN));
        RULE_LIST.add(new Rule(B, PORT_ONE, IN, maze -> maze, PORT_TWO, OUT));
        RULE_LIST.add(new Rule(B, PORT_ONE, OUT, maze -> maze.getParent().getChild(A), PORT_ONE, IN));
        RULE_LIST.add(new Rule(B, PORT_TWO, IN, maze -> maze, PORT_ONE, OUT));
        RULE_LIST.add(new Rule(B, PORT_TWO, OUT, EndlessMaze::getParent, PORT_FOUR, OUT));
        RULE_LIST.add(new Rule(B, PORT_TWO, OUT, maze -> maze.getParent().getChild(D), PORT_THREE, IN));
        RULE_LIST.add(new Rule(C, PORT_THREE, IN, maze -> maze.getChild(A), PORT_TWO, IN));
        RULE_LIST.add(new Rule(C, PORT_THREE, IN, maze -> maze.getChild(D), PORT_FOUR, IN));
        RULE_LIST.add(new Rule(C, PORT_THREE, OUT, EndlessMaze::getParent, PORT_END, IN));
        RULE_LIST.add(new Rule(C, PORT_FOUR, IN, maze -> maze.getChild(D), PORT_THREE, IN));
        RULE_LIST.add(new Rule(C, PORT_FOUR, IN, maze -> maze.getChild(B), PORT_TWO, IN));
        RULE_LIST.add(new Rule(C, PORT_FOUR, OUT, maze -> maze.getParent().getChild(D), PORT_ONE, IN));
        RULE_LIST.add(new Rule(D, PORT_ONE, IN, maze -> maze, PORT_ONE, OUT));
        RULE_LIST.add(new Rule(D, PORT_ONE, OUT, maze -> maze.getParent().getChild(C), PORT_FOUR, IN));
        RULE_LIST.add(new Rule(D, PORT_TWO, IN, maze -> maze, PORT_ONE, OUT));
        RULE_LIST.add(new Rule(D, PORT_TWO, OUT, maze -> maze.getParent().getChild(A), PORT_FOUR, IN));
        RULE_LIST.add(new Rule(D, PORT_THREE, IN, maze -> maze.getChild(A), PORT_TWO, IN));
        RULE_LIST.add(new Rule(D, PORT_THREE, IN, maze -> maze.getChild(D), PORT_FOUR, IN));
        RULE_LIST.add(new Rule(D, PORT_THREE, OUT, EndlessMaze::getParent, PORT_FOUR, OUT));
        RULE_LIST.add(new Rule(D, PORT_THREE, OUT, maze -> maze.getParent().getChild(B), PORT_TWO, IN));
        RULE_LIST.add(new Rule(D, PORT_FOUR, IN, maze -> maze.getChild(B), PORT_TWO, IN));
        RULE_LIST.add(new Rule(D, PORT_FOUR, IN, maze -> maze.getChild(D), PORT_THREE, IN));
        RULE_LIST.add(new Rule(D, PORT_FOUR, OUT, EndlessMaze::getParent, PORT_THREE, OUT));
        RULE_LIST.add(new Rule(D, PORT_FOUR, OUT, maze -> maze.getParent().getChild(A), PORT_TWO, IN));
    }

    public static void main(String[] args) {
        EndlessMaze maze = new EndlessMaze(4);
        maze.findTheWay(RULE_LIST);
    }
}
