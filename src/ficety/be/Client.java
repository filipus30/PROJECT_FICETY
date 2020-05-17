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
    private FloatProperty standardRate = new SimpleFloatProperty();
    private StringProperty email = new SimpleStringProperty();
    private IntegerProperty  projectNr = new SimpleIntegerProperty();



    
    public Client(int id, String clientName, float standardRate, String email) {
        this.clientID.set(id);
        this.clientName.set(clientName);
        this.standardRate.set(standardRate);
        this.email.set(email);
    }

    public IntegerProperty clientIDProperty()
    {
        return clientID;
    }
    
    public int getId() {
        return this.clientID.get();
    }

    public void setId(int clientID) {
        this.clientID.set(clientID);
    }

    public StringProperty clientNameProperty()
    {
        return clientName;
    }
    
    public String getClientName() {
        return this.clientName.get();
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }

    public FloatProperty standardRateProperty()
    {
        return standardRate;
    }
    
    public float getStandardRate() {
        return this.standardRate.get();
    }

    public void setStandardRate(float standardRate) {
        this.standardRate.set(standardRate);
    }
    
    public StringProperty emailProperty()
    {
        return email;
    }
    
    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public IntegerProperty projectNrProperty()
    {
        return projectNr;
    }
    
    public int getProjectNr() {
        return this.projectNr.get();
    }

    public void setProjectNr(int projectNr) {
        this.projectNr.set(projectNr);
    }

    @Override
    public String toString() {
        return this.clientName.get();
    }
    
    
}
