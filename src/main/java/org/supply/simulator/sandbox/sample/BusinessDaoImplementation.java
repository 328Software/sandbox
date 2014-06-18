package org.supply.simulator.sandbox.sample;

import java.util.Map;

/**
 * Created by Brandon on 6/17/2014.
 */
public class BusinessDaoImplementation extends BusinessLayerAbstract implements BusinessLayerContract {

    //
    private Map<String,Object> mapInsteadOfDAO;

    //sample dao declaration
    //private EntityDAO entityDAO


    @Override
    public void someMethod() {
        //

        Object data = mapInsteadOfDAO.get("query");
        //actual dao call might look like this
        //Entity entity = entityDAO.getEntityFromSomeCriteria(..)

        someMethod(data);
    }

    @Override
    public void someOtherMethod() {
        //

    }

    public void setMapInsteadOfDAO(Map<String, Object> mapInsteadOfDAO) {
        this.mapInsteadOfDAO = mapInsteadOfDAO;
    }
}
