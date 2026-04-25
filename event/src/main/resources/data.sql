-- ============================================================
--  Sample Data for Event Management System
-- ============================================================

-- Insert Venues
INSERT INTO resource (name, type, description, available) VALUES
    ('Auditorium',      'VENUE',     'Main faculty auditorium with 500-seat capacity', true),
    ('Rajavidiya', 'VENUE',     'Outdoor Area',      true),
    ('New Canteen',     'VENUE',     'Canteen area suitable for small gatherings',      true),
    ('Ground',          'VENUE',     'Open ground for outdoor events',                  true);

-- Insert Equipment-- ============================================================
-- --  Sample Data for Event Management System
-- -- ============================================================
--
-- -- ============================================================
-- --  RESOURCE TABLE (with quantity column)
-- -- ============================================================
-- -- Insert Venues (quantity = 1 for venues)
-- INSERT INTO resource (name, type, description, available, quantity) VALUES
--     ('Auditorium',      'VENUE',     'Main faculty auditorium with 500-seat capacity', true, 1),
--     ('Rajavidiya',      'VENUE',     'Outdoor Area',      true, 1),
--     ('New Canteen',     'VENUE',     'Canteen area suitable for small gatherings',      true, 1),
--     ('Ground',          'VENUE',     'Open ground for outdoor events',                  true, 1);
--
-- -- Insert Equipment (with actual available quantities)
-- INSERT INTO resource (name, type, description, available, quantity) VALUES
--     ('Microphones', 'EQUIPMENT', 'Wireless microphone set (5 units)',    true, 5),
--     ('Chairs',      'EQUIPMENT', 'Plastic chairs available (300 units)', true, 300),
--     ('Tables',      'EQUIPMENT', 'Foldable tables available (50 units)', true, 50);
--
-- -- ============================================================
-- --  USERS TABLE
-- -- ============================================================
-- INSERT INTO users (email, password, first_name, last_name, student_id, level, phone_number) VALUES
--
-- -- Level 4 Students (TG/2021/998 - TG/2021/1340)
-- ('amila@gmail.com', 'amila123', 'Amila', 'Perera', 'TG/2021/998', '4', '0712345601'),
-- ('ruwan@gmail.com', 'ruwan123', 'Ruwan', 'Jayawardena', 'TG/2021/1050', '4', '0712345602'),
-- ('dilini@gmail.com', 'dilini123', 'Dilini', 'Fernando', 'TG/2021/1100', '4', '0712345603'),
-- ('chamara@gmail.com', 'chamara123', 'Chamara', 'Bandara', 'TG/2021/1150', '4', '0712345604'),
-- ('tharindu@gmail.com', 'tharindu123', 'Tharindu', 'Weerasinghe', 'TG/2021/1200', '4', '0712345605'),
-- ('lasitha@gmail.com', 'lasitha123', 'Lasitha', 'Kumara', 'TG/2021/1250', '4', '0712345606'),
-- ('madushani@gmail.com', 'madushani123', 'Madushani', 'Silva', 'TG/2021/1300', '4', '0712345607'),
-- ('ishara@gmail.com', 'ishara123', 'Ishara', 'Wickramasinghe', 'TG/2021/1340', '4', '0712345608'),
--
-- -- Level 3 Students (TG/2022/1341 - TG/2022/1676)
-- ('nithya@gmail.com', 'nithya123', 'Nithya', 'Maduhansi', 'TG/2022/1348', '3', '0712345678'),
-- ('chavi@gmail.com', '1234', 'Chavindya', 'Nishadini', 'TG/2022/1393', '3', '0723456789'),
-- ('maheesha@gmail.com', 'mahee', 'Maheesha', 'Nidushani', 'TG/2022/1378', '3', '0734567890'),
-- ('sachini@gmail.com', 'sachini123', 'Sachini', 'Dissanayake', 'TG/2022/1450', '3', '0712345609'),
-- ('laksitha@gmail.com', 'laksitha123', 'Laksitha', 'Gunasekara', 'TG/2022/1500', '3', '0712345610'),
-- ('himasha@gmail.com', 'himasha123', 'Himasha', 'Abeykoon', 'TG/2022/1550', '3', '0712345611'),
-- ('pabasara@gmail.com', 'pabasara123', 'Pabasara', 'Ranasinghe', 'TG/2022/1600', '3', '0712345612'),
-- ('dilshan@gmail.com', 'dilshan123', 'Dilshan', 'Mendis', 'TG/2022/1650', '3', '0712345613'),
-- ('oshadi@gmail.com', 'oshadi123', 'Oshadi', 'Jayasuriya', 'TG/2022/1676', '3', '0712345614'),
--
-- -- Level 2 Students (TG/2023/1677 - TG/2023/1902)
-- ('nuwan@gmail.com', 'nuwan123', 'Nuwan', 'Rathnayake', 'TG/2023/1680', '2', '0712345615'),
-- ('sanduni@gmail.com', 'sanduni123', 'Sanduni', 'Ekanayake', 'TG/2023/1720', '2', '0712345616'),
-- ('chathura@gmail.com', 'chathura123', 'Chathura', 'Liyanage', 'TG/2023/1760', '2', '0712345617'),
-- ('prasadi@gmail.com', 'prasadi123', 'Prasadi', 'Weerakkody', 'TG/2023/1800', '2', '0712345618'),
-- ('kalana@gmail.com', 'kalana123', 'Kalana', 'Samarawickrama', 'TG/2023/1840', '2', '0712345619'),
-- ('tharushi@gmail.com', 'tharushi123', 'Tharushi', 'Dharmasiri', 'TG/2023/1880', '2', '0712345620'),
-- ('ishan@gmail.com', 'ishan123', 'Ishan', 'Rajapaksha', 'TG/2023/1902', '2', '0712345621'),
--
-- -- Level 1 Students (TG/2024/1903 - TG/2024/2226)
-- ('dinuka@gmail.com', 'dinuka123', 'Dinuka', 'Fernando', 'TG/2024/1910', '1', '0712345622'),
-- ('saduni@gmail.com', 'saduni123', 'Saduni', 'Perera', 'TG/2024/1950', '1', '0712345623'),
-- ('yashoda@gmail.com', 'yashoda123', 'Yashoda', 'Silva', 'TG/2024/2000', '1', '0712345624'),
-- ('malith@gmail.com', 'malith123', 'Malith', 'Jayawardena', 'TG/2024/2050', '1', '0712345625'),
-- ('sithara@gmail.com', 'sithara123', 'Sithara', 'Bandara', 'TG/2024/2100', '1', '0712345626'),
-- ('ravindu@gmail.com', 'ravindu123', 'Ravindu', 'Weerasinghe', 'TG/2024/2150', '1', '0712345627'),
-- ('nimeth@gmail.com', 'nimeth123', 'Nimeth', 'Kumara', 'TG/2024/2200', '1', '0712345628'),
-- ('vinuri@gmail.com', 'vinuri123', 'Vinuri', 'Gunawardena', 'TG/2024/2226', '1', '0712345629'),
--
-- -- Admin
-- ('admin@gmail.com', 'admin123', 'System', 'Admin', 'ADMIN001', 'Staff', '0771234567');
--
-- -- ============================================================
-- --  LEVEL RANGES TABLE
-- -- ============================================================
-- INSERT INTO level_ranges (level, academic_year, start_number, end_number) VALUES
-- ('1', '2024', 1903, 2226),
-- ('2', '2023', 1677, 1902),
-- ('3', '2022', 1341, 1676),
-- ('4', '2021', 998, 1340);
--
-- -- ============================================================
-- --  EVENTS TABLE
-- -- ============================================================
-- INSERT INTO events (title, description, event_date, start_time, end_time, organizer_name, contact_email, status, resource_id, image_url)
-- VALUES (
-- '✨ Diwali Celebration 2025 ✨','🎓 University of Ruhuna proudly presents 🪔 The Festival of Lights - spreading joy, unity, and brightness all around! 🌟
--
--        ஒளியின் திருநாளை ஒன்றாகக் கொண்டாடுவோம் 🌸💥
--
--        Let the lights shine brighter, the smiles grow wider, and the hearts fill with happiness 💫
--        Celebrating this beautiful Festival of Lights with joy, colors, music, and endless memories 🪔🎶🎉
--        May this Theepavali bring peace, prosperity, and new beginnings to everyone around 💛
--        Feeling blessed to be part of this wonderful celebration with amazing people ✨',
--
-- '2026-11-04','06:00 PM','10:00 PM','Level 3','sanduni@gmail.com','PENDING',3,'/images/events/Diwali.jpeg'),
--     (
--       '💖 ප්‍රේමණීය හුළංකන්ද 2026 💖',
--       'ආදරයෙන් හිනැහෙන, ප්‍රේමයෙන් සැනහෙන ප්‍රේම පරිච්ඡේදයක සදාතනික ආදර නවාතැනක් ✨
--
--   සංගීතය, නර්තනය, ආදරයෙන් පිරි මොහොතවල් සමඟ
--   ඔබේ විශ්වවිද්‍යාල පවුල සමඟ
--   අමතක නොවන රාත්‍රියක් සදහා එක්වන්න! 🌹',
--       '2026-06-14',
--       '05:00 PM',
--       '10:00 PM',
--       'University of Ruhuna - Faculty of Technology',
--       'events@ruhuna.ac.lk',
--       'PENDING',
--       2,
--       '/images/events/Valentine.jpeg'
--         );
INSERT INTO resource (name, type, description, available) VALUES
    ('Microphones', 'EQUIPMENT', 'Wireless microphone set (5 units)',    true),
    ('Chairs',      'EQUIPMENT', 'Plastic chairs available (300 units)', true),
    ('Tables',      'EQUIPMENT', 'Foldable tables available (50 units)', true);

-- Insert Users
INSERT INTO users (email, password, first_name, last_name, student_id, level, phone_number) VALUES

-- Level 4 Students (TG/2021/998 - TG/2021/1340)
('amila@gmail.com', 'amila123', 'Amila', 'Perera', 'TG/2021/998', '4', '0712345601'),
('ruwan@gmail.com', 'ruwan123', 'Ruwan', 'Jayawardena', 'TG/2021/1050', '4', '0712345602'),
('dilini@gmail.com', 'dilini123', 'Dilini', 'Fernando', 'TG/2021/1100', '4', '0712345603'),
('chamara@gmail.com', 'chamara123', 'Chamara', 'Bandara', 'TG/2021/1150', '4', '0712345604'),
('tharindu@gmail.com', 'tharindu123', 'Tharindu', 'Weerasinghe', 'TG/2021/1200', '4', '0712345605'),
('lasitha@gmail.com', 'lasitha123', 'Lasitha', 'Kumara', 'TG/2021/1250', '4', '0712345606'),
('madushani@gmail.com', 'madushani123', 'Madushani', 'Silva', 'TG/2021/1300', '4', '0712345607'),
('ishara@gmail.com', 'ishara123', 'Ishara', 'Wickramasinghe', 'TG/2021/1340', '4', '0712345608'),

-- Level 3 Students (TG/2022/1341 - TG/2022/1676)
('nithya@gmail.com', 'nithya123', 'Nithya', 'Maduhansi', 'TG/2022/1348', '3', '0712345678'),
('chavi@gmail.com', '1234', 'Chavindya', 'Nishadini', 'TG/2022/1393', '3', '0723456789'),
('maheesha@gmail.com', 'mahee', 'Maheesha', 'Nidushani', 'TG/2022/1378', '3', '0734567890'),
('sachini@gmail.com', 'sachini123', 'Sachini', 'Dissanayake', 'TG/2022/1450', '3', '0712345609'),
('laksitha@gmail.com', 'laksitha123', 'Laksitha', 'Gunasekara', 'TG/2022/1500', '3', '0712345610'),
('himasha@gmail.com', 'himasha123', 'Himasha', 'Abeykoon', 'TG/2022/1550', '3', '0712345611'),
('pabasara@gmail.com', 'pabasara123', 'Pabasara', 'Ranasinghe', 'TG/2022/1600', '3', '0712345612'),
('dilshan@gmail.com', 'dilshan123', 'Dilshan', 'Mendis', 'TG/2022/1650', '3', '0712345613'),
('oshadi@gmail.com', 'oshadi123', 'Oshadi', 'Jayasuriya', 'TG/2022/1676', '3', '0712345614'),

-- Level 2 Students (TG/2023/1677 - TG/2023/1902)
('nuwan@gmail.com', 'nuwan123', 'Nuwan', 'Rathnayake', 'TG/2023/1680', '2', '0712345615'),
('sanduni@gmail.com', 'sanduni123', 'Sanduni', 'Ekanayake', 'TG/2023/1720', '2', '0712345616'),
('chathura@gmail.com', 'chathura123', 'Chathura', 'Liyanage', 'TG/2023/1760', '2', '0712345617'),
('prasadi@gmail.com', 'prasadi123', 'Prasadi', 'Weerakkody', 'TG/2023/1800', '2', '0712345618'),
('kalana@gmail.com', 'kalana123', 'Kalana', 'Samarawickrama', 'TG/2023/1840', '2', '0712345619'),
('tharushi@gmail.com', 'tharushi123', 'Tharushi', 'Dharmasiri', 'TG/2023/1880', '2', '0712345620'),
('ishan@gmail.com', 'ishan123', 'Ishan', 'Rajapaksha', 'TG/2023/1902', '2', '0712345621'),

-- Level 1 Students (TG/2024/1903 - TG/2024/2226)
('dinuka@gmail.com', 'dinuka123', 'Dinuka', 'Fernando', 'TG/2024/1910', '1', '0712345622'),
('saduni@gmail.com', 'saduni123', 'Saduni', 'Perera', 'TG/2024/1950', '1', '0712345623'),
('yashoda@gmail.com', 'yashoda123', 'Yashoda', 'Silva', 'TG/2024/2000', '1', '0712345624'),
('malith@gmail.com', 'malith123', 'Malith', 'Jayawardena', 'TG/2024/2050', '1', '0712345625'),
('sithara@gmail.com', 'sithara123', 'Sithara', 'Bandara', 'TG/2024/2100', '1', '0712345626'),
('ravindu@gmail.com', 'ravindu123', 'Ravindu', 'Weerasinghe', 'TG/2024/2150', '1', '0712345627'),
('nimeth@gmail.com', 'nimeth123', 'Nimeth', 'Kumara', 'TG/2024/2200', '1', '0712345628'),
('vinuri@gmail.com', 'vinuri123', 'Vinuri', 'Gunawardena', 'TG/2024/2226', '1', '0712345629'),

-- Admin
('admin@gmail.com', 'admin123', 'System', 'Admin', 'ADMIN001', 'Staff', '0771234567');


INSERT INTO level_ranges (level, academic_year, start_number, end_number) VALUES
('1', '2024', 1903, 2226),
('2', '2023', 1677, 1902),
('3', '2022', 1341, 1676),
('4', '2021', 998, 1340);

INSERT INTO events (title, description, event_date, start_time, end_time, organizer_name, contact_email, status, resource_id, image_url)
VALUES (
'✨ Diwali Celebration 2025 ✨','🎓 University of Ruhuna proudly presents 🪔 The Festival of Lights - spreading joy, unity, and brightness all around! 🌟

       ஒளியின் திருநாளை ஒன்றாகக் கொண்டாடுவோம் 🌸💥

       Let the lights shine brighter, the smiles grow wider, and the hearts fill with happiness 💫
       Celebrating this beautiful Festival of Lights with joy, colors, music, and endless memories 🪔🎶🎉
       May this Theepavali bring peace, prosperity, and new beginnings to everyone around 💛
       Feeling blessed to be part of this wonderful celebration with amazing people ✨',

'2026-11-04','06:00 PM','10:00 PM','Level 3','sanduni@gmail.com','PENDING',3,'/images/events/Diwali.jpeg'),
    (
      '💖 ප්‍රේමණීය හුළංකන්ද 2026 💖',
      'ආදරයෙන් හිනැහෙන, ප්‍රේමයෙන් සැනහෙන ප්‍රේම පරිච්ඡේදයක සදාතනික ආදර නවාතැනක් ✨

  සංගීතය, නර්තනය, ආදරයෙන් පිරි මොහොතවල් සමඟ
  ඔබේ විශ්වවිද්‍යාල පවුල සමඟ
  අමතක නොවන රාත්‍රියක් සදහා එක්වන්න! 🌹',
      '2026-06-14',
      '05:00 PM',
      '10:00 PM',
      'University of Ruhuna - Faculty of Technology',
      'events@ruhuna.ac.lk',
      'PENDING',
      2,
      '/images/events/Valentine.jpeg'
        );