package com.jaimayal.learningmanager.business;

import java.util.function.Consumer;

/**
 * An enum representing the different types of resources available.
 * Matches the values of Postgres type "resource_type".
 */
public enum ResourceType {
    COURSE,
    ARTICLE,
    BOOK,
    VIDEO,
    OTHER
}
