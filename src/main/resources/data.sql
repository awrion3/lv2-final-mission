INSERT INTO reservation_time(start_at)
VALUES ('10:00');
INSERT INTO reservation_time(start_at)
VALUES ('11:00');
INSERT INTO reservation_time(start_at)
VALUES ('12:00');

INSERT INTO member(name, email, password, role)
VALUES ('admin', 'wooteco@gmail.com', '1234', 'ADMIN');
INSERT INTO member(name, email, password, role)
VALUES ('riwon', 'riwon@gmail.com', '5678', 'MEMBER');

INSERT INTO reservation(date, time_id, member_id)
VALUES ('2025-06-25', 1, 1);
INSERT INTO reservation(date, time_id, member_id)
VALUES ('2025-06-25', 2, 2);
