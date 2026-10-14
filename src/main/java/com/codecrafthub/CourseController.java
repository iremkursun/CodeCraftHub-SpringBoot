package com.codecrafthub;

import com.codecrafthub.model.Course;
import com.codecrafthub.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/api/courses")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping("/api/courses/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping("/api/courses")
    public ResponseEntity<Map<String, Object>> createCourse(
            @Valid @RequestBody Course course) {

        Course createdCourse = courseService.createCourse(course);

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("success", true);
        response.put("message", "Course added successfully");
        response.put("course", createdCourse);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/api/courses/{id}")
    public Course updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody Course course) {

        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/api/courses/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourse(
            @PathVariable Long id) {

        courseService.deleteCourse(id);

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("success", true);
        response.put("message", "Course deleted successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/courses/search")
    public List<Course> searchCourses(
            @RequestParam("q") String query) {

        return courseService.searchCourses(query);
    }

    @GetMapping("/api/courses/stats")
    public CourseService.CourseStatistics getStatistics() {

        return courseService.getStatistics();
    }
}