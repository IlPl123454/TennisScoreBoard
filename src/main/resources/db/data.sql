INSERT INTO Players (Name)
VALUES ('Andy Murray'),
       ('Pete Sampras'),
       ('Bjorn Borg'),
       ('Andre Agassi'),
       ('Stefan Edberg'),
       ('John McEnroe'),
       ('Jimmy Connors'),
       ('Ivan Lendl'),
       ('Mats Wilander'),
       ('Boris Becker');

INSERT INTO Matches (Player1_id, Player2_id, Winner_id)
VALUES
    (1, 2, 1),
    (2, 3, 2),
    (3, 1, 3),
    (1, 3, 1),
    (4, 5, 4),
    (5, 6, 5),
    (6, 7, 6),
    (7, 8, 7),
    (8, 4, 4),
    (3, 4, 3),
    (2, 5, 2),
    (6, 1, 1),
    (7, 2, 7),
    (8, 3, 3),
    (4, 6, 6),
    (5, 7, 5),
    (1, 8, 1),
    (2, 4, 2),
    (3, 5, 3),
    (6, 8, 6);