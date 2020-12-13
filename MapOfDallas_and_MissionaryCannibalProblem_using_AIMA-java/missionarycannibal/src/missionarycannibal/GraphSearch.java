package missionarycannibal;


import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.QueueSearch;

import java.util.*;

public class GraphSearch extends QueueSearch {
    private Set<Object> explored;

    int ct=0;

    public GraphSearch() {
        this(new NodeExpander());
    }

    public GraphSearch(NodeExpander nodeExpander) {
        super(nodeExpander);
        this.explored = new HashSet();
    }

    public Node findNode(Problem problem, Queue<Node> frontier) {
        this.explored.clear();
        return super.findNode(problem, frontier);
    }

    protected void addToFrontier(Node node) {
        if (!this.explored.contains(node.getState())) {
            this.frontier.add(node);
            if(this.ct<=5) {
                System.out.println("Current:"+node.getState());
                System.out.print("Frontier: ");
                for(Node n: this.frontier) {
                    System.out.print(n.getState().toString()+", ");
                }
                System.out.println("}");
                System.out.print("Successors: ");
                List<Node> rev = new ArrayList<>();
                for(Node n: this.frontier) {
                    rev.add(n);
                }
                Collections.reverse(rev);
                for(Node n: rev) {
                    System.out.print(n.getState().toString()+", ");
                }

                System.out.println("}");

                System.out.println("Explored:" + explored);
            }
            this.ct++;
            this.updateMetrics(this.frontier.size());
        }

    }

    protected Node removeFromFrontier() {
        this.cleanUpFrontier();
        Node result = (Node)this.frontier.remove();
        this.explored.add(result.getState());
        this.updateMetrics(this.frontier.size());
        return result;
    }

    protected boolean isFrontierEmpty() {
        this.cleanUpFrontier();
        this.updateMetrics(this.frontier.size());
        return this.frontier.isEmpty();
    }

    private void cleanUpFrontier() {
        while(!this.frontier.isEmpty() && this.explored.contains(((Node)this.frontier.element()).getState())) {
            this.frontier.remove();
        }

    }
}
