-- ========================
-- Users
-- ========================
INSERT INTO `user` (`email`, `name`, `password`, `user_role`, `is_public`)
VALUES
    ('1@1.com','Alice','$2a$10$QIJGp9r/K7OW.nMQ0P0uzeOv1XbFKASlmKYmvV9A65S8K7C3TaXsm','USER',1),
    ('2@2.com','Bob','$2a$10$QIJGp9r/K7OW.nMQ0P0uzeOv1XbFKASlmKYmvV9A65S8K7C3TaXsm','USER',0),
    ('3@3.com','Admin','$2a$10$QIJGp9r/K7OW.nMQ0P0uzeOv1XbFKASlmKYmvV9A65S8K7C3TaXsm','ADMIN',1);

-- ========================
-- Exercises
-- ========================
INSERT INTO `exercise` (`name`, `description`, `muscle_group`)
VALUES
    ('Bench Press','Flat bench press','CHEST'),
    ('Squat','Barbell back squat','LEGS'),
    ('Bicep Curl','Dumbbell curls','BICEPS'),
    ('Deadlift','Barbell deadlift','BACK'),
    ('Shoulder Press','Dumbbell shoulder press','SHOULDERS'),
    ('Tricep Pushdown','Cable tricep extension','TRICEPS'),
    ('Plank','Core stabilization exercise','CORE');

-- ========================
-- Workouts
-- ========================
INSERT INTO `workout` (`user_id`, `name`, `note`, `is_shared`, `date`)
VALUES
    (1,'Chest Day','Focus on upper body',1, '2025-10-26 10:00:00'),
    (2,'Leg Day','Leg strength',0, '2025-10-24 10:00:00'),
    (1,'Full Body Blast','High intensity full body',1,'2025-10-20 10:00:00'),
    (1,'Arm Focus','Biceps & Triceps day',0,'2025-10-22 15:00:00');

-- ========================
-- Workout Exercises (with explicit IDs)
-- ========================
INSERT INTO `workout_exercise` (`id`, `workout_id`, `exercise_id`)
VALUES
    (1,1,1),  -- Chest Day: Bench Press
    (2,1,3),  -- Chest Day: Bicep Curl
    (3,2,2),  -- Leg Day: Squat
    (4,3,1),  -- Full Body Blast: Bench Press
    (5,3,2),  -- Full Body Blast: Squat
    (6,3,4),  -- Full Body Blast: Deadlift
    (7,3,5),  -- Full Body Blast: Shoulder Press
    (8,3,7),  -- Full Body Blast: Plank
    (9,4,3),  -- Arm Focus: Bicep Curl
    (10,4,6); -- Arm Focus: Tricep Pushdown

-- ========================
-- Lifting Sets
-- ========================
INSERT INTO `lifting_set` (`reps`, `set_order`, `weight`, `workout_exercise_id`, `logged_at`)
VALUES
    (12,1,15,9,'2025-10-22 15:05:00'),
    (10,2,17,9,'2025-10-22 15:10:00'),
    (15,1,20,10,'2025-10-22 15:15:00'),
    (12,2,22,10,'2025-10-22 15:20:00'),
    (10,1,50,4,'2025-10-20 10:05:00'),
    (12,2,55,4,'2025-10-20 10:10:00'),
    (8,1,70,5,'2025-10-20 10:15:00'),
    (10,2,75,5,'2025-10-20 10:20:00'),
    (6,1,100,6,'2025-10-20 10:25:00'),
    (8,2,105,6,'2025-10-20 10:30:00'),
    (12,1,40,7,'2025-10-20 10:35:00'),
    (60,1,0,8,'2025-10-20 10:40:00');

-- ========================
-- User Connections
-- ========================
INSERT INTO `user_connection` (`sender_id`,`receiver_id`,`status`)
VALUES
    (1,2,'ACCEPTED'),
    (2,1,'ACCEPTED');

-- ========================
-- Posts
-- ========================
INSERT INTO `post` (`author_id`,`workout_id`,`content`)
VALUES
    (1,1,'Great chest workout today!'),
    (2,2,'Legs are sore after squats!');

-- ========================
-- Splits
-- ========================
INSERT INTO `split` (`name`, `description`, `user_id`)
VALUES
    ('Beginner Full Body', '3-day full body split for beginners', 1),
    ('Upper/Lower Split', '4-day split focusing on upper and lower body', 1),
    ('Push/Pull/Legs', 'Classic 3-day PPL split', 1);

-- ========================
-- Training Days
-- ========================
INSERT INTO `training_day` (`name`, `split_id`)
VALUES
    ('Day 1 - Full Body', 1),
    ('Day 2 - Full Body', 1),
    ('Day 3 - Full Body', 1),
    ('Upper Body', 2),
    ('Lower Body', 2),
    ('Push', 3),
    ('Pull', 3),
    ('Legs', 3);

-- ========================
-- Training Day Exercises (many-to-many)
-- ========================
INSERT INTO `training_day_exercise` (`training_day_id`, `exercise_id`)
VALUES
    -- Beginner Full Body Split
    (1,1), -- Bench Press
    (1,2), -- Squat
    (1,7), -- Plank
    (2,3), -- Bicep Curl
    (2,4), -- Deadlift
    (2,7), -- Plank
    (3,5), -- Shoulder Press
    (3,6), -- Tricep Pushdown
    (3,7), -- Plank

    -- Upper/Lower Split
    (4,1), -- Bench Press
    (4,3), -- Bicep Curl
    (5,2), -- Squat
    (5,4), -- Deadlift

    -- Push/Pull/Legs
    (6,1), -- Bench Press (Push)
    (6,5), -- Shoulder Press (Push)
    (6,6), -- Tricep Pushdown (Push)
    (7,3), -- Bicep Curl (Pull)
    (7,4), -- Deadlift (Pull)
    (8,2), -- Squat (Legs)
    (8,7); -- Plank (Legs)
