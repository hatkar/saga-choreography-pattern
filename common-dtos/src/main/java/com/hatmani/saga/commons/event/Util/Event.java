package com.hatmani.saga.commons.event.Util;

import java.util.Date;
import java.util.UUID;

public interface Event {
UUID getEventId();
Date getEventDate();
}
