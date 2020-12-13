package missionarycannibal;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.Problem;
import aima.core.search.informed.AStarSearch;
import missionarycannibal.GraphSearch;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.GreedyBestFirstSearch;
import missionarycannibal.RecursiveBestFirstSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

public class SearchAlgorithms {
    public static void main(String[] args) {
        Configuration config = new Configuration(3, 3, true);
        Problem p = new Problem(config, new MissionaryCannibalActionFunction(), new MissionaryCannibalResultFunction(), config, config);
        SearchAgent sa = null;

        UniformCostSearch ucs = new UniformCostSearch(new GraphSearch());
        System.out.println("---UniformCostSearch---");
        try{
            sa = new SearchAgent(p, ucs);
            System.out.println(sa.getActions());
            System.out.println(sa.getInstrumentation());
        }catch(Exception e){
            e.printStackTrace();
        }

        IterativeDeepeningSearch ids = new IterativeDeepeningSearch();
        System.out.println();
        System.out.println("---IterativeDeepeningSearch---");
        try{
            sa = new SearchAgent(p, ids);
            System.out.println(sa.getActions());
            System.out.println(sa.getInstrumentation());
        }catch(Exception e){
            e.printStackTrace();
        }

        GreedyBestFirstSearch gbfs = new GreedyBestFirstSearch(new GraphSearch(), config);
        System.out.println();
        System.out.println("---GreedyBestFirstSearch---");
        try{
            sa = new SearchAgent(p, gbfs);
            System.out.println(sa.getActions());
            System.out.println(sa.getInstrumentation());
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            System.out.println();
            AStarSearch aStarSearch = new AStarSearch(new GraphSearch(), config);
            System.out.println("---A* Search---");
            sa = new SearchAgent(p, aStarSearch);
            System.out.println(sa.getActions());
            System.out.println(sa.getInstrumentation());
        }catch(Exception e){
            e.printStackTrace();
        }
        RecursiveBestFirstSearch recursiveBestFirstSearch = new RecursiveBestFirstSearch(new AStarEvaluationFunction(config));
        System.out.println();
        System.out.println("---RecursiveBestFirstSearch---");
        try{
            sa = new SearchAgent(p, recursiveBestFirstSearch);
            System.out.println(sa.getActions());
            System.out.println(sa.getInstrumentation());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}