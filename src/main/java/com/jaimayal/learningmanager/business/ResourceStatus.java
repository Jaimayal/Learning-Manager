package com.jaimayal.learningmanager.business;

/**
 * An enum representing the different status one resource can have.
 * Matches the values of Postgres type "status_type".
 */
public enum ResourceStatus {
    FINISHED,
    IN_PROGRESS,
    NOT_STARTED
}
