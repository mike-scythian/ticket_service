package test.payment.service.model;

import java.util.Random;

public enum PaymentStatus {
    NEW, FAILED, DONE;

    public static PaymentStatus randomGenerator(){
        int randIndex = new Random().nextInt(values().length);

        return values()[randIndex];
    }
}
