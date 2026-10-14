package com.codecrafthub.service;

import com.codecrafthub.CourseNotFoundException;
import com.codecrafthub.model.Course;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class CourseService {

    private static final Path FILE_PATH = Path.of("courses.json");

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final List<String> VALID_STATUSES =
            Arrays.asList(
                    "Not Started",
                    "In Progress",
                    "Completed"
            );

    private final ObjectMapper objectMapper;

    public CourseService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Course> getCourses() {
        return loadCourses();
    }

    public Course getCourseById(Long id) {

        return loadCourses()
                .stream()
                .filter(course -> course.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    public Course createCourse(Course course) {

        validateStatus(course.getStatus());

        List<Course> courses = loadCourses();

        long nextId = getNextId(courses);

        course.setId(nextId);

        course.setCreatedAt(
                LocalDateTime.now().format(DATE_TIME_FORMATTER)
        );

        courses.add(course);

        saveCourses(courses);

        return course;
    }

    public Course updateCourse(Long id, Course updatedCourse) {

        validateStatus(updatedCourse.getStatus());

        List<Course> courses = loadCourses();

        Course existingCourse = courses
                .stream()
                .filter(course -> course.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(id));

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setTargetDate(updatedCourse.getTargetDate());
        existingCourse.setStatus(updatedCourse.getStatus());

        saveCourses(courses);

        return existingCourse;
    }

    public void deleteCourse(Long id) {

        List<Course> courses = loadCourses();

        Course course = courses
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(id));

        courses.remove(course);

        saveCourses(courses);
    }

    public List<Course> searchCourses(String query) {

        String searchTerm = query.toLowerCase();

        return loadCourses()
                .stream()
                .filter(course ->
                        course.getName().toLowerCase().contains(searchTerm)
                                ||
                                course.getDescription().toLowerCase().contains(searchTerm)
                )
                .toList();
    }

    public CourseStatistics getStatistics() {

        List<Course> courses = loadCourses();

        long notStarted = courses.stream()
                .filter(course -> "Not Started".equals(course.getStatus()))
                .count();

        long inProgress = courses.stream()
                .filter(course -> "In Progress".equals(course.getStatus()))
                .count();

        long completed = courses.stream()
                .filter(course -> "Completed".equals(course.getStatus()))
                .count();

        return new CourseStatistics(
                courses.size(),
                notStarted,
                inProgress,
                completed
        );
    }

    private List<Course> loadCourses() {

        try {

            if (!Files.exists(FILE_PATH)) {

                List<Course> emptyCourses = new ArrayList<>();

                saveCourses(emptyCourses);

                return emptyCourses;
            }

            if (Files.size(FILE_PATH) == 0) {
                return new ArrayList<>();
            }

            return objectMapper.readValue(
                    FILE_PATH.toFile(),
                    new TypeReference<List<Course>>() {
                    }
            );

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Could not read courses.json",
                    exception
            );
        }
    }

    private void saveCourses(List<Course> courses) {

        try {

            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(FILE_PATH.toFile(), courses);

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Could not save courses.json",
                    exception
            );
        }
    }

    private long getNextId(List<Course> courses) {

        return courses
                .stream()
                .map(Course::getId)
                .filter(id -> id != null)
                .max(Comparator.naturalOrder())
                .orElse(0L) + 1;
    }

    private void validateStatus(String status) {

        if (!VALID_STATUSES.contains(status)) {

            throw new IllegalArgumentException(
                    "Status must be one of: Not Started, In Progress, Completed"
            );
        }
    }

    public record CourseStatistics(
            long total,
            long notStarted,
            long inProgress,
            long completed) {
    }
}