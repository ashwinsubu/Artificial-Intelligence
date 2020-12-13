package missionarycannibal;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.problem.ResultFunction;

public class MissionaryCannibalResultFunction implements ResultFunction {

    @Override
    public Object result(Object arg0, Action arg1) {
        if(!(arg0 instanceof Configuration && arg1 instanceof DynamicAction)){
            throw new IllegalArgumentException();
        }
        Configuration conf = (Configuration) arg0;
        String actionName = ((DynamicAction)arg1).getName();
        return computeResult(conf, actionName);
    }

    /**
     * @param action
     * @param actionName
     * @return
     * Computes the result function to be returned
     */
    private Object computeResult(Configuration action, String actionName) {
        if("MM".equals(actionName)) {
            if(action.isBoat()){
                return new Configuration(action.getCannibals(), action.getMissionaries() - 2, false);
            }else{
                return new Configuration(action.getCannibals(), action.getMissionaries() + 2, true);
            }
        } else if("CC".equals(actionName)) {
            if(action.isBoat()){
                return new Configuration(action.getCannibals() - 2, action.getMissionaries(), false);
            }else{
                return new Configuration(action.getCannibals() + 2, action.getMissionaries(), true);
            }
        } else if("M".equals(actionName)) {
            if(action.isBoat()){
                return new Configuration(action.getCannibals(), action.getMissionaries() - 1, false);
            }else{
                return new Configuration(action.getCannibals(), action.getMissionaries() + 1, true);
            }
        } else if("C".equals(actionName)) {
            if(action.isBoat()){
                return new Configuration(action.getCannibals() - 1, action.getMissionaries(), false);
            }else{
                return new Configuration(action.getCannibals() + 1, action.getMissionaries(), true);
            }
        } else if("MC".equals(actionName)) {
            if(action.isBoat()){
                return new Configuration(action.getCannibals() - 1, action.getMissionaries() - 1, false);
            } else {
                return new Configuration(action.getCannibals() + 1, action.getMissionaries() + 1, true);
            }
        }else {
            return null;
        }
    }
}