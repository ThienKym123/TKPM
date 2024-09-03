package com.example.project.repository;

import com.example.project.model.TeachingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachingAssignmentRepository extends JpaRepository<TeachingAssignment, String> {

    // Find TeachingAssignments by teacherId, subjectId, and studentId
    List<TeachingAssignment> findByTeacherIdAndSubjectIdAndStudentId(String teacherId, String subjectId, String studentId);

    // Find TeachingAssignments that do not have a corresponding Grade entry
    @Query("SELECT ta FROM TeachingAssignment ta WHERE ta.id NOT IN (SELECT g.assignmentId FROM Grade g)")
    List<TeachingAssignment> findAssignmentsWithoutGrades();
}
