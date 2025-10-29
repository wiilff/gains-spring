package com.wilff.gains_spring.migration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilff.gains_spring.model.enums.UserRole;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MigrateToMySQL {


    public static void importAllJsonToMySQL() throws Exception {
        String url = "jdbc:mysql://localhost:3306/db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "user";
        String password = "root";

        Connection conn = DriverManager.getConnection(url, user, password);
        ObjectMapper mapper = new ObjectMapper();

        // -------------------------------
        // Maps to store MongoID → MySQL auto-generated ID
        // -------------------------------
        Map<String, Integer> userIdMap = new HashMap<>();
        Map<String, Integer> exerciseIdMap = new HashMap<>();
        Map<String, Integer> workoutIdMap = new HashMap<>();
        Map<String, Integer> workoutExerciseIdMap = new HashMap<>();

        // -------------------------------
        // 1️⃣ Import users.json
        // -------------------------------
        File usersFile = new File("/home/apps/backups-json/users.json");
        for (JsonNode node : mapper.readTree(usersFile)) {
            String mongoId = node.get("_id").get("$oid").asText();
            String nameStr = node.get("name").asText();
            String email = node.get("email").asText();
            String passwordHash = node.get("password").asText(  );

            String sql = "INSERT INTO user (name, email, password, user_role) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nameStr);
                stmt.setString(2, email);
                stmt.setString(3, passwordHash);
                stmt.setString(4, UserRole.USER.name());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    userIdMap.put(mongoId, rs.getInt(1));
                }
            }
        }

        // -------------------------------
        // 2️⃣ Import exercises.json
        // -------------------------------
        File exercisesFile = new File("/home/apps/backups-json/exercises.json");
        for (JsonNode node : mapper.readTree(exercisesFile)) {
            String mongoId = node.get("_id").get("$oid").asText();
            String nameStr = node.get("name").asText();
            String description = node.has("description") ? node.get("description").asText() : null;
            String muscleGroup = node.has("muscle_group") ? node.get("muscle_group").asText() : null;

            String sql = "INSERT INTO exercise (name, description, muscle_group) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nameStr);
                stmt.setString(2, description);
                stmt.setString(3, muscleGroup);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    exerciseIdMap.put(mongoId, rs.getInt(1));
                }
            }
        }

        // -------------------------------
        // 3️⃣ Import workouts.json
        // -------------------------------
        File workoutsFile = new File("/home/apps/backups-json/workouts.json");
        for (JsonNode node : mapper.readTree(workoutsFile)) {
            String mongoId = node.get("_id").get("$oid").asText();
            String nameStr = node.get("name").asText();
            String note = node.has("note") ? node.get("note").asText() : null;

            String dateStr = node.get("date").get("$date").asText().replace("Z", "");
            LocalDateTime ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            String mongoUserId = node.get("userId").get("$oid").asText();
            int mysqlUserId = userIdMap.get(mongoUserId);

            String sql = "INSERT INTO workout (user_id, date, name, note) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, mysqlUserId);
                stmt.setObject(2, ldt);
                stmt.setString(3, nameStr);
                stmt.setString(4, note);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    workoutIdMap.put(mongoId, rs.getInt(1));
                }
            }
        }

        // -------------------------------
        // 4️⃣ Import workout_exercises.json
        // -------------------------------
        File workoutExercisesFile = new File("/home/apps/backups-json/workoutexercises.json");
        for (JsonNode node : mapper.readTree(workoutExercisesFile)) {
            String mongoId = node.get("_id").get("$oid").asText();
            String mongoWorkoutId = node.get("workoutId").get("$oid").asText();
            String mongoExerciseId = node.get("exerciseId").get("$oid").asText();

            int mysqlWorkoutId = workoutIdMap.get(mongoWorkoutId);
            int mysqlExerciseId = exerciseIdMap.get(mongoExerciseId);

            String sql = "INSERT INTO workout_exercise (workout_id, exercise_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, mysqlWorkoutId);
                stmt.setInt(2, mysqlExerciseId);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    workoutExerciseIdMap.put(mongoId, rs.getInt(1));
                }
            }
        }

        // -------------------------------
        // 5️⃣ Import lifting_sets.json
        // -------------------------------
        File liftingSetsFile = new File("/home/apps/backups-json/sets.json");
        for (JsonNode node : mapper.readTree(liftingSetsFile)) {
            int reps = node.get("reps").asInt();
            int setOrder = node.get("order").asInt();
            double weight = node.get("weight").asDouble();
            String mongoWorkoutExerciseId = node.get("workoutExerciseId").get("$oid").asText();
            int mysqlWorkoutExerciseId = workoutExerciseIdMap.get(mongoWorkoutExerciseId);
            String loggedAt = node.has("logged_at") ? node.get("logged_at").asText() : null;

            String sql = "INSERT INTO lifting_set (reps, set_order, weight, workout_exercise_id, logged_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, reps);
                stmt.setInt(2, setOrder);
                stmt.setDouble(3, weight);
                stmt.setInt(4, mysqlWorkoutExerciseId);
                stmt.setString(5, loggedAt);
                stmt.executeUpdate();
            }
        }

        conn.close();
        System.out.println("All JSON data imported successfully!");
    }

    public static void main(String[] args) {
        try {
            importAllJsonToMySQL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
