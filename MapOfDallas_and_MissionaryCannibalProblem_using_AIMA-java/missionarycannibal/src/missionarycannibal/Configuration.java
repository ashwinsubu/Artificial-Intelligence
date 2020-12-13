package missionarycannibal;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.search.framework.problem.GoalTest;
import aima.core.search.framework.problem.StepCostFunction;

public class Configuration implements GoalTest, StepCostFunction, HeuristicFunction {

    final static Action Missionary = new DynamicAction("M");
    final static Action MissionaryCannibal = new DynamicAction("MC");
    final static Action Cannibal = new DynamicAction("C");
    final static Action CannibalCannibal = new DynamicAction("CC");
    final static Action MissionaryMissionary = new DynamicAction("MM");

    private int cannibal;
    private int missionary;
    private boolean boat;

    public Configuration(int cannibal, int missionary, boolean boat) {
        if(cannibal < 0 || cannibal > 3  || missionary > 3 || missionary < 0){
            throw new IllegalArgumentException();
        }
        this.cannibal = cannibal;
        this.missionary = missionary;
        this.boat = boat;
    }

    public int getCannibals() {
        return this.cannibal;
    }
    public int getMissionaries() {
        return this.missionary;
    }
    public boolean isBoat() {
        return this.boat;
    }

    @Override
    public boolean isGoalState(Object arg0) {
        if(arg0 instanceof Configuration){
            Configuration s = (Configuration) arg0;
            return s.getCannibals() == 0 && s.getMissionaries() == 0 && !s.isBoat();
        }else{
            return false;
        }
    }

    @Override
    public double h(Object arg0) {
        return (((Configuration) arg0).getCannibals()+((Configuration) arg0).getMissionaries())/2;
    }

    @Override
    public double c(Object arg0, Action arg1, Object arg2) {
        return 1;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("{C:").append(this.getCannibals()).append(", M:").append(this.getMissionaries()).append(", Boat:").append(this.boat +"}").toString();
    }
}