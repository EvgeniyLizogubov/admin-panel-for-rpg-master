DROP TABLE IF EXISTS player_race;
DROP TABLE IF EXISTS player_profession;
DROP TABLE IF EXISTS player;

CREATE TABLE player_race
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    race VARCHAR(20) NOT NULL
);

CREATE TABLE player_profession
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    profession VARCHAR(20) NOT NULL
);

CREATE TABLE player
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(12) NULL UNIQUE,
    title            VARCHAR(30) NULL,
    race_id          INTEGER REFERENCES player_race (id),
    profession_id    INTEGER REFERENCES player_profession (id),
    birthday         DATE        NULL,
    banned           BOOLEAN     NULL,
    experience       INT         NULL,
    level            INT         NULL,
    until_next_level INT         NULL
);

INSERT INTO player_race(race)
VALUES ('HUMAN'),
       ('DWARF'),
       ('ELF'),
       ('GIANT'),
       ('ORC'),
       ('TROLL'),
       ('HOBBIT');

INSERT INTO player_profession(profession)
VALUES ('WARRIOR'),
       ('ROGUE'),
       ('SORCERER'),
       ('CLERIC'),
       ('PALADIN'),
       ('NAZGUL'),
       ('WARLOCK'),
       ('DRUID');

INSERT INTO player(name, title, race_id, profession_id, birthday, banned, experience, level, until_next_level)
VALUES ('Ниус', 'Приходящий Без Шума', 7, 2, '2010-10-12', false, 58347, 33, 1153)
     , ('Никрашш', 'НайтВульф', 5, 7, '2010-02-14', false, 174403, 58, 2597)
     , ('Эззэссэль', 'шипящая', 2, 4, '2006-02-28', true, 804, 3, 196);