/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.gui.model;

import ficety.bll.BllManager;

/**
 *
 * @author Trigger
 */
public class UserViewModel {
    private BllManager BllM = new BllManager();
    
    public void startStopSession()
    {
        BllM.startStopSession();
    }
}
