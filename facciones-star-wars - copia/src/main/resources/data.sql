CREATE TABLE IF NOT EXISTS factions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

INSERT IGNORE INTO factions (code, name) VALUES
('JED', 'Jedi'),
('SIT', 'Sith'),
('REP', 'Republica Galactica'),
('IMP', 'Imperio Galactico'),
('REB', 'Alianza Rebelde'),
('RES', 'Resistencia'),
('PRI', 'Primera Orden'),
('MAN', 'Mandalorianos'),
('HUT', 'Cartel Hutt'),
('NAB', 'Casa Real de Naboo');
