package dallasmap;

import aima.core.agent.Action;
import aima.core.environment.map.ExtendableMap;
import aima.core.environment.map.Map;
import aima.core.environment.map.MapFunctionFactory;
import aima.core.environment.map.MapStepCostFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.DefaultGoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.AStarSearch;
import dallasmap.RecursiveBestFirstSearch;

/**
 * Represents a simplified road map of Dallas.
 *
 * @author Ashwin Subramaniam
 */
public class SimplifiedRoadMapOfDallas extends ExtendableMap {

    // Locations for Dallas Problem
    public static final String AUSTIN = "Austin";
    public static final String CHARLOTTE = "Charlotte";
    public static final String SAN_FRANCISCO = "San Francisco";
    public static final String LOS_ANGELES = "Los Angeles";
    public static final String NEW_YORK = "New York";
    public static final String CHICAGO = "Chicago";
    public static final String SEATTLE = "Seattle";
    public static final String SANTA_FE = "Santa Fe";
    public static final String BAKERSVILLE = "Bakersville";
    public static final String BOSTON = "Boston";
    public static final String DALLAS = "Dallas";

    //Constructor
    public SimplifiedRoadMapOfDallas() {
        initializeMap(this);
    }


    /**
     * Initializes a map with a simplified road map of Dallas.
     */
    public static void initializeMap(ExtendableMap map) {
        map.clear();
        //Init using distances given in Question
        initNeighborDistances(map);

        //Setting zero since the Straight Line distance is calculated internally
        initStraightLineDistances(map);

    }


    /**
     * @param map
     * Setting zero since the Straight Line distance is calculated internally
     */
    private static void initStraightLineDistances(ExtendableMap map) {
        map.setPosition(AUSTIN, 182, 0);
        map.setPosition(CHARLOTTE, 929, 0);
        map.setPosition(SAN_FRANCISCO, 1230, 0);
        map.setPosition(LOS_ANGELES, 1100, 0);
        map.setPosition(NEW_YORK, 1368, 0);
        map.setPosition(CHICAGO, 800, 0);
        map.setPosition(SEATTLE, 1670, 0);
        map.setPosition(SANTA_FE, 560, 0);
        map.setPosition(BAKERSVILLE, 1282, 0);
        map.setPosition(BOSTON, 1551, 0);
    }


    /**
     * @param map
     * Distances given in Question
     */
    private static void initNeighborDistances(ExtendableMap map) {
        map.addBidirectionalLink(LOS_ANGELES, SAN_FRANCISCO, 383.0);
        map.addBidirectionalLink(LOS_ANGELES, AUSTIN, 1377.0);
        map.addBidirectionalLink(LOS_ANGELES, BAKERSVILLE, 153.0);
        map.addBidirectionalLink(SAN_FRANCISCO, BAKERSVILLE, 283.0);
        map.addBidirectionalLink(SAN_FRANCISCO, SEATTLE, 807.0);
        map.addBidirectionalLink(SEATTLE, SANTA_FE, 1463.0);
        map.addBidirectionalLink(SEATTLE, CHICAGO, 2064.0);
        map.addBidirectionalLink(BAKERSVILLE, SANTA_FE, 864.0);
        map.addBidirectionalLink(AUSTIN, DALLAS, 195.0);
        map.addBidirectionalLink(SANTA_FE, DALLAS, 640.0);
        map.addBidirectionalLink(DALLAS, NEW_YORK, 1548.0);
        map.addBidirectionalLink(AUSTIN, CHARLOTTE, 1200.0);
        map.addBidirectionalLink(CHARLOTTE, NEW_YORK, 634.0);
        map.addBidirectionalLink(NEW_YORK, BOSTON, 225.0);
        map.addBidirectionalLink(BOSTON, CHICAGO, 983.0);
        map.addBidirectionalLink(CHICAGO, SANTA_FE, 1272.0);
        map.addBidirectionalLink(BOSTON, SAN_FRANCISCO, 3095.0);
    }


    public static void main(String args[]) throws Exception {
        Map map = new SimplifiedRoadMapOfDallas();
        Problem problem = new Problem(SimplifiedRoadMapOfDallas.SEATTLE, MapFunctionFactory.getActionsFunction(map), MapFunctionFactory.getResultFunction(), new DefaultGoalTest(SimplifiedRoadMapOfDallas.DALLAS), new MapStepCostFunction(map));
        printAStarSearch(map, problem);
        printRecursiveBreadthFirstSearch(map, problem);

    }

    /**
     * @param map
     * @param problem
     * @throws Exception
     * Prints AStar Search
     */
    private static void printAStarSearch(Map map, Problem problem) throws Exception {
        System.out.println("---AStarSearch---");
        AStarSearch search = new AStarSearch(new GraphSearch(), MapFunctionFactory.getSLDHeuristicFunction(DALLAS, map));
        SearchAgent agent = new SearchAgent(problem, search);
        kabinmethod(agent);
        System.out.println(agent.getInstrumentation());
    }

    private static void kabinmethod(SearchAgent agent) {
        for(Action action : agent.getActions()) {
            System.out.println(action.toString());
        }
    }

    /**
     * @param map
     * @param problem
     * @throws Exception
     * Prints RecursiveBreadthFirstSearch Search
     */
    private static void printRecursiveBreadthFirstSearch(Map map, Problem problem) throws Exception {
        System.out.println("---RecursiveBreadthFirstSearch---");
        RecursiveBestFirstSearch recursiveBestFirstSearch = new RecursiveBestFirstSearch(new AStarEvaluationFunction(MapFunctionFactory.getSLDHeuristicFunction(DALLAS, map)));
        SearchAgent agent1 = new SearchAgent(problem, recursiveBestFirstSearch);
        for(Action action : agent1.getActions()) {
            System.out.println(action.toString());
        }
        System.out.println(agent1.getInstrumentation());
    }
}