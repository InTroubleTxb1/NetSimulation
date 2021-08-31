package me.txb1;

import eu.firedata.system.controller.Controller;
import eu.firedata.system.controller.loader.NoContextLoader;

/**
 * @author Txb1 at 30.08.2021
 * @project NetSimulation
 */

public class NetSimulationApplication {
    public static void main(String[] args) {
        Controller.load(new NoContextLoader("me.txb1"), null);
    }
}