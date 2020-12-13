package missionarycannibal;


import java.util.HashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;

public class MissionaryCannibalActionFunction implements ActionsFunction {

    @Override
    public Set<Action> actions(Object arg0) {
        if(!(arg0 instanceof Configuration)){
            throw new IllegalArgumentException();
        }
        Configuration state = (Configuration) arg0;
        Set<Action> actions = new HashSet<>();
        return computeAction(state, actions);
    }

    /**
     * @param config
     * @param actions
     * @return
     * Computes action function to be returned
     */
    private Set<Action> computeAction(Configuration config, Set<Action> actions) {
        if (config.isBoat()) {
            if (config.getMissionaries() == 3) {
                if (config.getCannibals() >= 2) {
                    actions.add(Configuration.Missionary);
                    actions.add(Configuration.MissionaryMissionary);
                    actions.add(Configuration.MissionaryCannibal);
                    actions.add(Configuration.Cannibal);
                    actions.add(Configuration.CannibalCannibal);
                } else if (config.getCannibals() == 1) {
                    actions.add(Configuration.MissionaryMissionary);
                    actions.add(Configuration.Missionary);
                    actions.add(Configuration.MissionaryCannibal);
                    actions.add(Configuration.Cannibal);
                } else {
                    actions.add(Configuration.MissionaryMissionary);
                    actions.add(Configuration.Missionary);
                }
            } else if (config.getMissionaries() == 2) {
                if (config.getCannibals() == 2) {
                    actions.add(Configuration.MissionaryMissionary);
                    actions.add(Configuration.Missionary);
                    actions.add(Configuration.MissionaryCannibal);
                    actions.add(Configuration.Cannibal);
                    actions.add(Configuration.CannibalCannibal);
                }
            } else if (config.getMissionaries() == 1) {
                if (config.getCannibals() == 1) {
                    actions.add(Configuration.Missionary);
                    actions.add(Configuration.MissionaryCannibal);
                    actions.add(Configuration.Cannibal);
                }
            } else {
                if (config.getCannibals() >= 2) {
                    actions.add(Configuration.CannibalCannibal);
                    actions.add(Configuration.Cannibal);
                } else {
                    actions.add(Configuration.Cannibal);
                }
            }
        }
        else if (!config.isBoat()) {
            if (config.getMissionaries() == 3) {
                if (config.getCannibals() == 2) {
                    actions.add(Configuration.Cannibal);
                } else {
                    actions.add(Configuration.Cannibal);
                    actions.add(Configuration.CannibalCannibal);
                }
            } else if (config.getMissionaries() == 2) {
                if (config.getCannibals() == 2) {
                    actions.add(Configuration.Missionary);
                    actions.add(Configuration.MissionaryCannibal);
                    actions.add(Configuration.Cannibal);
                }
            } else if (config.getMissionaries() == 1) {
                if (config.getCannibals() == 1) {
                    actions.add(Configuration.Cannibal);
                    actions.add(Configuration.CannibalCannibal);
                    actions.add(Configuration.MissionaryCannibal);
                    actions.add(Configuration.Missionary);
                    actions.add(Configuration.MissionaryMissionary);
                }
            } else {
                if (config.getCannibals() == 3) {
                    actions.add(Configuration.Missionary);
                    actions.add(Configuration.MissionaryMissionary);
                } else if (config.getCannibals() == 2) {
                    actions.add(Configuration.Missionary);
                    actions.add(Configuration.MissionaryMissionary);
                    actions.add(Configuration.MissionaryCannibal);
                    actions.add(Configuration.Cannibal);
                } else {
                    actions.add(Configuration.Missionary);
                    actions.add(Configuration.MissionaryMissionary);
                    actions.add(Configuration.MissionaryCannibal);
                    actions.add(Configuration.CannibalCannibal);
                }
            }
        }
        return actions;
    }

}