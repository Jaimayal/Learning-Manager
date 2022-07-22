package com.jaimayal.learningmanager.business;

/**
 * An enum representing the different types of resources available.
 * Matches the values of Postgres type "resource_type".
 */
public enum ResourceType {
    ARTICLE,
    BOOK,
    COURSE,
    OTHER,
    VIDEO
}
