
package missionarycannibal;
import aima.core.agent.Action;
import aima.core.search.framework.Metrics;
import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.evalfunc.EvaluationFunction;
import aima.core.search.framework.problem.Problem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RecursiveBestFirstSearch implements SearchForActions {
    public static final String METRIC_NODES_EXPANDED = "nodesExpanded";
    public static final String METRIC_MAX_RECURSIVE_DEPTH = "maxRecursiveDepth";
    public static final String METRIC_PATH_COST = "pathCost";
    private static final Double INFINITY = 1.7976931348623157E308D;
    private final EvaluationFunction evaluationFunction;
    private boolean avoidLoops;
    private final NodeExpander nodeExpander;
    Set<Object> explored;
    private Metrics metrics;
    int count=0;
    public RecursiveBestFirstSearch(EvaluationFunction ef) {
        this(ef, false);
    }

    public RecursiveBestFirstSearch(EvaluationFunction ef, boolean avoidLoops) {
        this(ef, avoidLoops, new NodeExpander());
    }

    public RecursiveBestFirstSearch(EvaluationFunction ef, boolean avoidLoops, NodeExpander nodeExpander) {
        this.explored = new HashSet();
        this.evaluationFunction = ef;
        this.avoidLoops = avoidLoops;
        this.nodeExpander = nodeExpander;
        this.metrics = new Metrics();
    }

    public List<Action> findActions(Problem p) {
        List<Action> actions = new ArrayList();

        this.explored.clear();
        this.clearInstrumentation();
        Node n = this.nodeExpander.createRootNode(p.getInitialState());
        RecursiveBestFirstSearch.SearchResult sr = this.rbfs(p, n, this.evaluationFunction.f(n), INFINITY, 0);
        if (sr.hasSolution()) {
            Node s = sr.getSolutionNode();
            actions = SearchUtils.getSequenceOfActions(s);
            this.metrics.set("pathCost", s.getPathCost());
        }

        return (List)actions;
    }

    public EvaluationFunction getEvaluationFunction() {
        return this.evaluationFunction;
    }

    public NodeExpander getNodeExpander() {
        return this.nodeExpander;
    }

    public Metrics getMetrics() {
        this.metrics.set("nodesExpanded", this.nodeExpander.getNumOfExpandCalls());
        return this.metrics;
    }

    private void clearInstrumentation() {
        this.nodeExpander.resetCounter();
        this.metrics.set("nodesExpanded", 0);
        this.metrics.set("maxRecursiveDepth", 0);
        this.metrics.set("pathCost", 0.0D);
    }

    private RecursiveBestFirstSearch.SearchResult rbfs(Problem p, Node node, double node_f, double fLimit, int recursiveDepth) {
        this.updateMetrics(recursiveDepth);
        if (SearchUtils.isGoalState(p, node)) {
            return this.getResult((Node)null, node, fLimit);
        } else {
            List<Node> successors = this.expandNode(node, p);
            if (successors.isEmpty()) {
                return this.getResult(node, (Node)null, INFINITY);
            } else {
                double[] f = new double[successors.size()];
                int size = successors.size();

                int bestIndex;
                for(bestIndex = 0; bestIndex < size; ++bestIndex) {
                    f[bestIndex] = Math.max(this.evaluationFunction.f((Node)successors.get(bestIndex)), node_f);
                }

                RecursiveBestFirstSearch.SearchResult sr;
                do {
                    bestIndex = this.getBestFValueIndex(f);
                    if (f[bestIndex] > fLimit) {
                        return this.getResult(node, (Node)null, f[bestIndex]);
                    }

                    int altIndex = this.getNextBestFValueIndex(f, bestIndex);
                    if(count++ <=5) {
                        System.out.println("Current:"+node.getState());
                        System.out.print("Successors:");
                        for(Node nd:successors) {
                            System.out.print( "("+nd.getState()+ "), ");
                        }
                        System.out.println();
                    }
                    sr = this.rbfs(p, (Node)successors.get(bestIndex), f[bestIndex], Math.min(fLimit, f[altIndex]), recursiveDepth + 1);
                    f[bestIndex] = sr.getFCostLimit();
                } while(!sr.hasSolution());

                return this.getResult(node, sr.getSolutionNode(), sr.getFCostLimit());
            }
        }
    }

    private int getBestFValueIndex(double[] f) {
        int lidx = 0;
        Double lowestSoFar = INFINITY;

        for(int i = 0; i < f.length; ++i) {
            if (f[i] < lowestSoFar) {
                lowestSoFar = f[i];
                lidx = i;
            }
        }

        return lidx;
    }

    private int getNextBestFValueIndex(double[] f, int bestIndex) {
        int lidx = bestIndex;
        Double lowestSoFar = INFINITY;

        for(int i = 0; i < f.length; ++i) {
            if (i != bestIndex && f[i] < lowestSoFar) {
                lowestSoFar = f[i];
                lidx = i;
            }
        }

        return lidx;
    }

    private List<Node> expandNode(Node node, Problem problem) {
        List<Node> result = this.nodeExpander.expand(node, problem);
        if (this.avoidLoops) {
            this.explored.add(node.getState());


            Iterator ni = result.iterator();
            while(ni.hasNext()) {
                if (this.explored.contains(((Node)ni.next()).getState())) {
                    ni.remove();
                }
            }
        }

        return result;
    }

    private RecursiveBestFirstSearch.SearchResult getResult(Node currNode, Node solutionNode, double fCostLimit) {
        if (this.avoidLoops && currNode != null) {
            this.explored.remove(currNode.getState());
        }

        return new RecursiveBestFirstSearch.SearchResult(solutionNode, fCostLimit);
    }

    private void updateMetrics(int recursiveDepth) {
        int maxRdepth = this.metrics.getInt("maxRecursiveDepth");
        if (recursiveDepth > maxRdepth) {
            this.metrics.set("maxRecursiveDepth", recursiveDepth);
        }

    }

    static class SearchResult {
        private Node solNode;
        private final double fCostLimit;

        public SearchResult(Node solutionNode, double fCostLimit) {
            this.solNode = solutionNode;
            this.fCostLimit = fCostLimit;
        }

        public boolean hasSolution() {
            return this.solNode != null;
        }

        public Node getSolutionNode() {
            return this.solNode;
        }

        public Double getFCostLimit() {
            return this.fCostLimit;
        }
    }
}
