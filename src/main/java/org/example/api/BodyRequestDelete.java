package org.example.api;

public class BodyRequestDelete {

    private int courierId;

    public BodyRequestDelete(int courierId){
        this.courierId = courierId;
    }

    public BodyRequestDelete(){
    }

    public int getCourierId() {
            return courierId;
    }
    public void setCourierId(int courierId) {
            this.courierId = courierId;
    }
}
