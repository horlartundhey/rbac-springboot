package com.rbac.rolebased.dtos;

import java.util.List;

public class InstructorAssignmentRequest {
    private List<Long> instructorIds;

    // Getters and setters
    public List<Long> getInstructorIds() {
        return instructorIds;
    }

    public void setInstructorIds(List<Long> instructorIds) {
        this.instructorIds = instructorIds;
    }
}
