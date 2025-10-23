package com.neb.constants;


public enum WorkStatus {
    ASSIGNED,      // When Admin assigns a new task
    IN_PROGRESS,   // When Employee starts working on it
    COMPLETED,     // When Employee finishes work (but report not yet submitted)
    REPORTED       // When Employee submits the report
}
