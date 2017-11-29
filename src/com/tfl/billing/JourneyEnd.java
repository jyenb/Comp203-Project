package com.tfl.billing;

import java.util.UUID;

public class JourneyEnd extends JourneyEvent {


    //added time to constructor
    public JourneyEnd(UUID cardId, UUID readerId) {
        super(cardId, readerId);
    }
}
