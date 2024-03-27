package com.tutorial.rediscache.constant;

public enum Status {
    NEW(0),
    ACTIVE(1),
    CANCELED(2),
    INACTIVE(3),
    SUSPENDED(4),
    DEACTIVATED(5),
    DELETED(6),
    ARCHIVED(7),
    PENDING(8),
    DEPARTED(9),
    REJECTED(10),
    REPORTED(11),
    REQUESTED(12),
    STOPPED(13),
    EXPIRED(14),
    CLOSED(15),
    ENABLED(16),
    DISABLED(17),
    TRIALING(18),
    ENDED(19);

    private final long id;

    Status(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean isActive() {
        return ACTIVE.equals(this);
    }
}
