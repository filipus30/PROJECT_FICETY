/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class Client {
    private IntegerProperty clientID = new SimpleIntegerProperty();
    private StringProperty clientName = new SimpleStringProperty();
    private StringProperty imgLocation = new SimpleStringProperty();
    private FloatProperty standardRate = new SimpleFloatProperty();
    private StringProperty email = new SimpleStringProperty();
    private IntegerProperty  projectNr = new SimpleIntegerProperty();



    
    public Client(int id, String clientName, String imgLocation, float standardRate, String email) {
        this.clientID.set(id);
        this.clientName.set(clientName);
        this.imgLocation.set(imgLocation);
        this.standardRate.set(standardRate);
        this.email.set(email);
    }

    
    public int getId() {
        return this.clientID.get();
    }

    public void setId(int clientID) {
        this.clientID.set(clientID);
    }

    public String getClientName() {
        return this.clientName.get();
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }

    public String getImgLocation() {
        return this.imgLocation.get();
    }

    public void setImgLocation(String imgLocation) {
        this.imgLocation.set(imgLocation);
    }

    public float getStandardRate() {
        return this.standardRate.get();
    }

    public void setStandardRate(float standardRate) {
        this.standardRate.set(standardRate);
    }

    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public int getProjectNr() {
        return this.projectNr.get();
    }

    public void setProjectNr(int projectNr) {
        this.projectNr.set(projectNr);
    }
    
    
}
