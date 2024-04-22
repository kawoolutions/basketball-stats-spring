
-- -----------------------------------------------------
-- Schema bbstatstest
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS bbstatstest;

-- SET SCHEMA bbstatstest;

-- -----------------------------------------------------
-- Table GeoContexts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.GeoContexts (
  id INT NOT NULL,
  parent_id INT NULL DEFAULT NULL,
  name VARCHAR(50) NOT NULL,
  type ENUM('CONTINENT', 'COUNTRY', 'REGION', 'STATE', 'DISTRICT') NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT geocontexts_self_fk
    FOREIGN KEY (parent_id)
    REFERENCES bbstatstest.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX geocontexts_self_idx ON bbstatstest.GeoContexts (parent_id ASC);

-- -----------------------------------------------------
-- Table Continents
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Continents (
  id INT NOT NULL,
  iso_code CHAR(2) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT continents_iso_code_uq UNIQUE (iso_code ASC),
  CONSTRAINT continents_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE UNIQUE INDEX continents_iso_code_uq ON bbstatstest.Continents (iso_code ASC);

-- -----------------------------------------------------
-- Table Countries
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Countries (
  id INT NOT NULL,
  iso_code CHAR(2) NOT NULL,
  iso_nbr CHAR(3) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT countries_iso_code_uq UNIQUE (iso_code ASC),
  CONSTRAINT countries_iso_nbr_uq UNIQUE (iso_nbr ASC),
  CONSTRAINT countries_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE UNIQUE INDEX countries_iso_code_uq ON bbstatstest.Countries (iso_code ASC);
-- CREATE UNIQUE INDEX countries_iso_nbr_uq ON bbstatstest.Countries (iso_nbr ASC);
-- CREATE INDEX countries_geoareas_idx ON bbstatstest.Countries (id ASC);

-- -----------------------------------------------------
-- Table Regions
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Regions (
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT regions_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table States
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.States (
  id INT NOT NULL,
  country_code CHAR(2) NOT NULL,
  iso_code VARCHAR(5) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT states_multi_uq UNIQUE (country_code ASC, iso_code ASC),
  CONSTRAINT states_countries_fk
    FOREIGN KEY (country_code)
    REFERENCES bbstatstest.Countries (iso_code)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT states_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE UNIQUE INDEX states_multi_uq ON bbstatstest.States (country_code ASC, iso_code ASC);
-- CREATE INDEX states_countries_idx ON bbstatstest.States (country_code ASC);
-- CREATE INDEX states_geoareas_idx ON bbstatstest.States (id ASC);

-- -----------------------------------------------------
-- Table Districts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Districts (
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT districts_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Contacts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Contacts (
  id INT NOT NULL AUTO_INCREMENT,
  type ENUM('P', 'C', 'A') NOT NULL,
  PRIMARY KEY (id));


-- -----------------------------------------------------
-- Table Addresses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Addresses (
  contact_id INT NOT NULL,
  country_code CHAR(2) NULL DEFAULT NULL,
  zip_code VARCHAR(10) NULL DEFAULT NULL,
  city_name VARCHAR(100) NULL DEFAULT NULL,
  street_name VARCHAR(100) NULL DEFAULT NULL,
  house_nbr VARCHAR(10) NULL DEFAULT NULL,
  latitude DOUBLE NULL,
  longitude DOUBLE NULL,
  PRIMARY KEY (contact_id),
  CONSTRAINT postaddresses_contacts_fk
    FOREIGN KEY (contact_id)
    REFERENCES bbstatstest.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX postaddresses_contacts_idx ON bbstatstest.Addresses (contact_id ASC);

-- -----------------------------------------------------
-- Table PhoneNumbers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.PhoneNumbers (
  contact_id INT NOT NULL,
  type ENUM('MOBILE', 'FIXED', 'FAX') NOT NULL DEFAULT 'MOBILE',
  country_code SMALLINT NOT NULL,
  area_code SMALLINT NOT NULL,
  subscriber_nbr INT NOT NULL,
  PRIMARY KEY (type, contact_id),
  CONSTRAINT phonenumbers_contacts_fk
    FOREIGN KEY (contact_id)
    REFERENCES bbstatstest.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX phonenumbers_contacts_idx ON bbstatstest.PhoneNumbers (contact_id ASC);

-- -----------------------------------------------------
-- Table EmailAddresses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.EmailAddresses (
  contact_id INT NOT NULL,
  "index" INT NOT NULL,
  uri VARCHAR(64) NOT NULL,
  PRIMARY KEY (contact_id, "index"),
  CONSTRAINT emailaddresses_contacts_fk
    FOREIGN KEY (contact_id)
    REFERENCES bbstatstest.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX emailaddresses_contacts_idx ON bbstatstest.EmailAddresses (contact_id ASC);

-- -----------------------------------------------------
-- Table Colors
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Colors (
  name VARCHAR(25) NOT NULL,
  rgb BINARY(3) NOT NULL,
  PRIMARY KEY (name),
  CONSTRAINT colors_rgb_uq UNIQUE (rgb ASC));

-- CREATE UNIQUE INDEX colors_rgb_uq ON bbstatstest.Colors (rgb ASC);

-- -----------------------------------------------------
-- Table Clubs
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Clubs (
  id INT NOT NULL,
  district_id INT NOT NULL,
  color_name VARCHAR(25) NULL,
  name VARCHAR(50) NOT NULL,
  code VARCHAR(5) NOT NULL,
  website_url VARCHAR(64) NULL DEFAULT NULL,
  logo MEDIUMBLOB NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT clubs_multi_uq UNIQUE (district_id ASC, code ASC),
  CONSTRAINT clubs_colors_fk
    FOREIGN KEY (color_name)
    REFERENCES bbstatstest.Colors (name)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT clubs_contacts_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT clubs_districts_fk
    FOREIGN KEY (district_id)
    REFERENCES bbstatstest.Districts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE UNIQUE INDEX clubs_multi_uq ON bbstatstest.Clubs (district_id ASC, code ASC);
-- CREATE INDEX clubs_colors_idx ON bbstatstest.Clubs (color_name ASC);
-- CREATE INDEX clubs_districts_idx ON bbstatstest.Clubs (district_id ASC);

-- -----------------------------------------------------
-- Table Arenas
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Arenas (
  id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  capacity INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT arenas_contacts_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Tenancies
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Tenancies (
  club_id INT NOT NULL,
  arena_id INT NOT NULL,
  ordinal_nbr SMALLINT NOT NULL,
  PRIMARY KEY (club_id, arena_id),
  CONSTRAINT tenancies_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES bbstatstest.Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT tenancies_arenas_fk
    FOREIGN KEY (arena_id)
    REFERENCES bbstatstest.Arenas (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX tenancies_clubs_idx ON bbstatstest.Tenancies (club_id ASC);
-- CREATE INDEX tenancies_arenas_idx ON bbstatstest.Tenancies (arena_id ASC);

-- -----------------------------------------------------
-- Table Persons
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Persons (
  id INT NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  gender ENUM('MALE', 'FEMALE') NOT NULL DEFAULT 'MALE',
  birth_date DATE NULL DEFAULT '1976-03-03',
  is_incognito BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  CONSTRAINT persons_contacts_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Referees
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Referees (
  id INT NOT NULL,
  license_nbr VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT referees_persons_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX referees_persons_idx ON bbstatstest.Referees (id ASC);

-- -----------------------------------------------------
-- Table Coaches
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Coaches (
  id INT NOT NULL,
  license_nbr VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT coaches_persons_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Players
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Players (
  id INT NOT NULL,
  registration_nbr VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT players_persons_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table TeamTypes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.TeamTypes (
  code CHAR(4) NOT NULL,
  age_group ENUM('O20', 'O30', 'O35', 'O40', 'O45', 'O50', 'O55', 'O60', 'O65', 'U20', 'U19', 'U18', 'U17', 'U16', 'U15', 'U14', 'U13', 'U12', 'U11', 'U10', 'U09', 'U08') NOT NULL DEFAULT 'O20',
  gender ENUM('MALE', 'FEMALE', 'MIXED') NOT NULL DEFAULT 'MALE',
  PRIMARY KEY (code),
  CONSTRAINT teamtypes_multi_uq UNIQUE (age_group ASC, gender ASC));

-- CREATE UNIQUE INDEX teamtypes_multi_uq ON bbstatstest.TeamTypes (age_group ASC, gender ASC);

-- -----------------------------------------------------
-- Table Teams
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Teams (
  club_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  ordinal_nbr SMALLINT NOT NULL,
  PRIMARY KEY (club_id, team_type_code, ordinal_nbr),
  CONSTRAINT teams_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES bbstatstest.Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT teams_teamtypes_fk
    FOREIGN KEY (team_type_code)
    REFERENCES bbstatstest.TeamTypes (code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX teams_clubs_idx ON bbstatstest.Teams (club_id ASC);
-- CREATE INDEX teams_teamtypes_idx ON bbstatstest.Teams (team_type_code ASC);

-- -----------------------------------------------------
-- Table Competitions
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Competitions (
  geo_context_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  type ENUM('REGULAR_SEASON', 'CUP', 'PLAYOFFS', 'RELEGATION', 'QUALIFICATION', 'PREPARATION', 'TOURNAMENT') NOT NULL,
  level SMALLINT NOT NULL DEFAULT 1,
  PRIMARY KEY (geo_context_id, team_type_code, type, level),
  CONSTRAINT competitions_geocontexts_fk
    FOREIGN KEY (geo_context_id)
    REFERENCES bbstatstest.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT competitions_teamtypes_fk
    FOREIGN KEY (team_type_code)
    REFERENCES bbstatstest.TeamTypes (code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX competitions_teamtypes_idx ON bbstatstest.Competitions (team_type_code ASC);
-- CREATE INDEX competitions_geocontexts_idx ON bbstatstest.Competitions (geo_context_id ASC);

-- -----------------------------------------------------
-- Table CompetitionLabels
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.CompetitionLabels (
  geo_context_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  competition_type ENUM('REGULAR_SEASON', 'CUP', 'PLAYOFFS', 'RELEGATION', 'QUALIFICATION', 'PREPARATION', 'TOURNAMENT') NOT NULL,
  competition_level SMALLINT NOT NULL,
  season_start_year INT NOT NULL DEFAULT 1966,
  name VARCHAR(50) NOT NULL,
  code VARCHAR(6) NULL DEFAULT NULL,
  PRIMARY KEY (geo_context_id, team_type_code, competition_type, competition_level, season_start_year),
  CONSTRAINT competitionlabels_competitions_fk
    FOREIGN KEY (geo_context_id , team_type_code , competition_type , competition_level)
    REFERENCES bbstatstest.Competitions (geo_context_id , team_type_code , type , level)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Seasons
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Seasons (
  start_year INT NOT NULL,
  PRIMARY KEY (start_year));


-- -----------------------------------------------------
-- Table Rounds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Rounds (
  id INT NOT NULL AUTO_INCREMENT,
  type ENUM('RANKING_ROUND', 'ELIMINATION_ROUND') NOT NULL DEFAULT 'RANKING_ROUND',
  geo_context_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  competition_type ENUM('REGULAR_SEASON', 'CUP', 'PLAYOFFS', 'RELEGATION', 'QUALIFICATION', 'PREPARATION', 'TOURNAMENT') NOT NULL,
  competition_level SMALLINT NOT NULL,
  season_start_year INT NOT NULL,
  nbr VARCHAR(10) NULL,
  PRIMARY KEY (id),
  CONSTRAINT rounds_competitions_fk
    FOREIGN KEY (geo_context_id , team_type_code , competition_type , competition_level)
    REFERENCES bbstatstest.Competitions (geo_context_id , team_type_code , type , level)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rounds_seasons_fk
    FOREIGN KEY (season_start_year)
    REFERENCES bbstatstest.Seasons (start_year)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX rounds_seasons_idx ON bbstatstest.Rounds (season_start_year ASC);
-- CREATE INDEX rounds_competitions_idx ON bbstatstest.Rounds (geo_context_id ASC, team_type_code ASC, competition_type ASC, competition_level ASC);

-- -----------------------------------------------------
-- Table RankingRounds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.RankingRounds (
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT rankingrounds_rounds_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.Rounds (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table EliminationRounds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.EliminationRounds (
  id INT NOT NULL,
  best_of SMALLINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT eliminationrounds_rounds_fk
    FOREIGN KEY (id)
    REFERENCES bbstatstest.Rounds (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Rosters
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Rosters (
  id INT NOT NULL AUTO_INCREMENT,
  club_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  team_ordinal_nbr SMALLINT NOT NULL,
  season_start_year INT NOT NULL,
  primary_jersey_color_name VARCHAR(25) NULL DEFAULT NULL,
  secondary_jersey_color_name VARCHAR(25) NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT rosters_multi_uq UNIQUE (club_id ASC, team_type_code ASC, team_ordinal_nbr ASC, season_start_year ASC),
  CONSTRAINT rosters_seasons_fk
    FOREIGN KEY (season_start_year)
    REFERENCES bbstatstest.Seasons (start_year)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rosters_teams_fk
    FOREIGN KEY (club_id , team_type_code , team_ordinal_nbr)
    REFERENCES bbstatstest.Teams (club_id , team_type_code , ordinal_nbr)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rosters_primary_jersey_colors_fk
    FOREIGN KEY (primary_jersey_color_name)
    REFERENCES bbstatstest.Colors (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rosters_secondary_jersey_colors_fk
    FOREIGN KEY (secondary_jersey_color_name)
    REFERENCES bbstatstest.Colors (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE UNIQUE INDEX rosters_multi_uq ON bbstatstest.Rosters (club_id ASC, team_type_code ASC, team_ordinal_nbr ASC, season_start_year ASC);
-- CREATE INDEX rosters_seasons_idx ON bbstatstest.Rosters (season_start_year ASC);
-- CREATE INDEX rosters_teams_idx ON bbstatstest.Rosters (club_id ASC, team_type_code ASC, team_ordinal_nbr ASC);
-- CREATE INDEX rosters_primary_jersey_colors_idx ON bbstatstest.Rosters (primary_jersey_color_name ASC);
-- CREATE INDEX rosters_secondary_jersey_colors_idx ON bbstatstest.Rosters (secondary_jersey_color_name ASC);

-- -----------------------------------------------------
-- Table RefpoolMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.RefpoolMembers (
  referee_id INT NOT NULL,
  club_id INT NOT NULL,
  season_start_year INT NOT NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (referee_id, club_id, season_start_year),
  CONSTRAINT refpoolmembers_referees_fk
    FOREIGN KEY (referee_id)
    REFERENCES bbstatstest.Referees (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT refpoolmembers_seasons_fk
    FOREIGN KEY (season_start_year)
    REFERENCES bbstatstest.Seasons (start_year)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT refpoolmembers_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES bbstatstest.Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX refpoolmembers_referees_idx ON bbstatstest.RefpoolMembers (referee_id ASC);
-- CREATE INDEX refpoolmembers_seasons_idx ON bbstatstest.RefpoolMembers (season_start_year ASC);
-- CREATE INDEX refpoolmembers_clubs_idx ON bbstatstest.RefpoolMembers (club_id ASC);

-- -----------------------------------------------------
-- Table StaffMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.StaffMembers (
  coach_id INT NOT NULL,
  roster_id INT NOT NULL,
  role ENUM('HEAD_COACH', 'ASSISTANT_COACH', 'ATHLETIC_TRAINER', 'PHYSICIAN', 'DOCTOR') NULL DEFAULT NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (roster_id, coach_id),
  CONSTRAINT staffmembers_coaches_fk
    FOREIGN KEY (coach_id)
    REFERENCES bbstatstest.Coaches (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT staffmembers_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES bbstatstest.Rosters (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX staffmembers_coaches_idx ON bbstatstest.StaffMembers (coach_id ASC);
-- CREATE INDEX staffmembers_rosters_idx ON bbstatstest.StaffMembers (roster_id ASC);

-- -----------------------------------------------------
-- Table TeamMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.TeamMembers (
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  position ENUM('CENTER', 'POWER_FORWARD', 'SMALL_FORWARD', 'SHOOTING_GUARD', 'POINT_GUARD') NULL DEFAULT NULL,
  eligible_to_play_since DATE NULL DEFAULT NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (player_id, roster_id),
  CONSTRAINT teammembers_players_fk
    FOREIGN KEY (player_id)
    REFERENCES bbstatstest.Players (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT teammembers_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES bbstatstest.Rosters (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX teammembers_players_idx ON bbstatstest.TeamMembers (player_id ASC);
-- CREATE INDEX teammembers_rosters_idx ON bbstatstest.TeamMembers (roster_id ASC);

-- -----------------------------------------------------
-- Table GroupLabels
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.GroupLabels (
  code VARCHAR(6) NOT NULL,
  name VARCHAR(50) NULL,
  PRIMARY KEY (code),
  CONSTRAINT grouplabels_code_uq UNIQUE (code ASC));


-- -----------------------------------------------------
-- Table Groups
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Groups (
  round_id INT NOT NULL,
  code VARCHAR(6) NOT NULL,
  official_code VARCHAR(6) NULL DEFAULT NULL,
  max_members TINYINT NULL DEFAULT NULL,
  PRIMARY KEY (round_id, code),
  CONSTRAINT groups_rounds_fk
    FOREIGN KEY (round_id)
    REFERENCES bbstatstest.Rounds (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT groups_grouplabels_fk
    FOREIGN KEY (code)
    REFERENCES bbstatstest.GroupLabels (code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX groups_rounds_idx ON bbstatstest.Groups (round_id ASC);
-- CREATE INDEX groups_grouplabels_idx ON bbstatstest.Groups (code ASC);

-- -----------------------------------------------------
-- Table GroupMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.GroupMembers (
  roster_id INT NOT NULL,
  round_id INT NOT NULL,
  group_code VARCHAR(6) NOT NULL,
  withdrawn BOOLEAN NOT NULL,
  PRIMARY KEY (roster_id, round_id, group_code),
  CONSTRAINT groupmembers_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES bbstatstest.Rosters (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT groupmembers_groups_fk
    FOREIGN KEY (round_id , group_code)
    REFERENCES bbstatstest.Groups (round_id , code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX groupmembers_rosters_idx ON bbstatstest.GroupMembers (roster_id ASC);
-- CREATE INDEX groupmembers_groups_idx ON bbstatstest.GroupMembers (round_id ASC, group_code ASC);

-- -----------------------------------------------------
-- Table Games
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Games (
  id INT NOT NULL AUTO_INCREMENT,
  round_id INT NOT NULL,
  group_code VARCHAR(6) NOT NULL,
  arena_id INT NULL,
  ref_club_id INT NULL,
  scheduled_tipoff DATETIME NOT NULL,
  actual_tipoff DATETIME NULL DEFAULT NULL,
  matchday_nbr SMALLINT NULL DEFAULT NULL,
  official_nbr VARCHAR(8) NULL DEFAULT NULL,
  attendance INT NULL DEFAULT NULL,
  recap VARCHAR(2000) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT games_ref_clubs_fk
    FOREIGN KEY (ref_club_id)
    REFERENCES bbstatstest.Clubs (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT games_arenas_fk
    FOREIGN KEY (arena_id)
    REFERENCES bbstatstest.Arenas (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT games_groups_fk
    FOREIGN KEY (round_id , group_code)
    REFERENCES bbstatstest.Groups (round_id , code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX games_ref_clubs_idx ON bbstatstest.Games (ref_club_id ASC);
-- CREATE INDEX games_arenas_idx ON bbstatstest.Games (arena_id ASC);
-- CREATE INDEX games_groups_idx ON bbstatstest.Games (round_id ASC, group_code ASC);

-- -----------------------------------------------------
-- Table Scores
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Scores (
  game_id INT NOT NULL,
  is_home BOOLEAN NOT NULL,
  roster_id INT NOT NULL,
  final_score SMALLINT NULL DEFAULT NULL,
  PRIMARY KEY (game_id, is_home),
  CONSTRAINT scores_games_fk
    FOREIGN KEY (game_id)
    REFERENCES bbstatstest.Games (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT scores_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES bbstatstest.Rosters (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX scores_games_idx ON bbstatstest.Scores(game_id ASC);
-- CREATE INDEX scores_rosters_idx ON bbstatstest.Scores (roster_id ASC);

-- -----------------------------------------------------
-- Table PlayerStats
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.PlayerStats (
  game_id INT NOT NULL,
  is_home BOOLEAN NOT NULL,
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  jersey_nbr TINYINT NOT NULL,
  has_played BOOLEAN NOT NULL DEFAULT TRUE,
  is_starter BOOLEAN NULL DEFAULT NULL,
  pf TINYINT NOT NULL,
  PRIMARY KEY (game_id, is_home, player_id, roster_id),
  CONSTRAINT playerstats_multi_uq UNIQUE (game_id ASC, is_home ASC, jersey_nbr ASC),
  CONSTRAINT playerstats_scores_fk
    FOREIGN KEY (game_id , is_home)
    REFERENCES bbstatstest.Scores (game_id , is_home)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT playerstats_teammembers_fk
    FOREIGN KEY (player_id , roster_id)
    REFERENCES bbstatstest.TeamMembers (player_id , roster_id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE UNIQUE INDEX playerstats_multi_uq ON bbstatstest.PlayerStats (game_id ASC, is_home ASC, jersey_nbr ASC);
-- CREATE INDEX playerstats_scores_idx ON bbstatstest.PlayerStats (game_id ASC, is_home ASC);
-- CREATE INDEX playerstats_teammembers_idx ON bbstatstest.PlayerStats (player_id ASC, roster_id ASC);

-- -----------------------------------------------------
-- Table Stats
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Stats (
  game_id INT NOT NULL,
  is_home BOOLEAN NOT NULL,
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  "period" TINYINT NOT NULL,
  tpm TINYINT NOT NULL,
  ftm TINYINT NOT NULL,
  fta TINYINT NOT NULL,
  pts TINYINT NOT NULL,
  PRIMARY KEY (game_id, is_home, "period", player_id, roster_id),
  CONSTRAINT stats_playerstats_fk
    FOREIGN KEY (game_id , is_home , player_id , roster_id)
    REFERENCES bbstatstest.PlayerStats (game_id , is_home , player_id , roster_id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX stats_playerstats_idx ON bbstatstest.Stats (game_id ASC, is_home ASC, player_id ASC, roster_id ASC);

-- -----------------------------------------------------
-- Table Assignments
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Assignments (
  referee_id INT NOT NULL,
  club_id INT NOT NULL,
  season_start_year INT NOT NULL,
  game_id INT NOT NULL,
  owner_club_id INT NULL,
  was_absent BOOLEAN NULL DEFAULT FALSE,
  PRIMARY KEY (referee_id, club_id, season_start_year, game_id),
  CONSTRAINT assignments_games_fk
    FOREIGN KEY (game_id)
    REFERENCES bbstatstest.Games (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT assignments_owner_clubs_fk
    FOREIGN KEY (owner_club_id)
    REFERENCES bbstatstest.Clubs (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT assignments_refpoolmembers_fk
    FOREIGN KEY (referee_id , club_id , season_start_year)
    REFERENCES bbstatstest.RefpoolMembers (referee_id , club_id , season_start_year)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX assignments_games_idx ON bbstatstest.Assignments (game_id ASC);
-- CREATE INDEX assignments_owner_clubs_idx ON bbstatstest.Assignments (owner_club_id ASC);
-- CREATE INDEX assignments_refpoolmembers_idx ON bbstatstest.Assignments (referee_id ASC, club_id ASC, season_start_year ASC);

-- -----------------------------------------------------
-- Table Managers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Managers (
  person_id INT NOT NULL,
  club_id INT NOT NULL,
  role ENUM('OWNER', 'CHAIRMAN', 'HEAD_OF_DEPT', 'REFEREE_WARDEN', 'WEBMASTER') NOT NULL,
  PRIMARY KEY (person_id, club_id, role),
  CONSTRAINT managers_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES bbstatstest.Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT managers_persons_fk
    FOREIGN KEY (person_id)
    REFERENCES bbstatstest.Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- CREATE INDEX managers_clubs_idx ON bbstatstest.Managers (club_id ASC);
-- CREATE INDEX managers_persons_idx ON bbstatstest.Managers (person_id ASC);

-- -----------------------------------------------------
-- Table GroupLinks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.GroupLinks (
  parent_round_id INT NOT NULL,
  parent_group_code VARCHAR(6) NOT NULL,
  child_round_id INT NOT NULL,
  child_group_code VARCHAR(6) NOT NULL,
  PRIMARY KEY (parent_round_id, parent_group_code, child_round_id, child_group_code),
  CONSTRAINT grouplinks_parent_groups_fk
    FOREIGN KEY (parent_round_id , parent_group_code)
    REFERENCES bbstatstest.Groups (round_id , code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT grouplinks_child_groups_fk
    FOREIGN KEY (child_round_id , child_group_code)
    REFERENCES bbstatstest.Groups (round_id , code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX grouplinks_child_groups_idx ON bbstatstest.GroupLinks (child_round_id ASC, child_group_code ASC);
-- CREATE INDEX grouplinks_parent_groups_idx ON bbstatstest.GroupLinks (parent_round_id ASC, parent_group_code ASC);

-- -----------------------------------------------------
-- Table Users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Users (
  name VARCHAR(20) NOT NULL,
  person_id INT NULL,
  password VARCHAR(60) NOT NULL,
  new_password VARCHAR(60) NULL DEFAULT NULL,
  salt VARCHAR(30) NULL DEFAULT NULL,
  is_enabled BOOLEAN NOT NULL DEFAULT TRUE,
  locale_code VARCHAR(5) NOT NULL DEFAULT 'de',
  theme_name VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (name),
  CONSTRAINT users_person_id_uq UNIQUE (person_id ASC),
  CONSTRAINT users_persons_fk
    FOREIGN KEY (person_id)
    REFERENCES bbstatstest.Persons (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE UNIQUE INDEX users_person_id_uq ON bbstatstest.Users (person_id ASC);

-- -----------------------------------------------------
-- Table Roles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.Roles (
  id INT NOT NULL,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT roles_name_uq UNIQUE (name ASC));

-- CREATE UNIQUE INDEX roles_name_uq ON bbstatstest.Roles (name ASC);

-- -----------------------------------------------------
-- Table UserRoleLinks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.UserRoleLinks (
  user_name VARCHAR(20) NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_name, role_id),
  CONSTRAINT userrolelinks_users_fk
    FOREIGN KEY (user_name)
    REFERENCES bbstatstest.Users (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT userrolelinks_roles_fk
    FOREIGN KEY (role_id)
    REFERENCES bbstatstest.Roles (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX userrolelinks_roles_idx ON bbstatstest.UserRoleLinks (role_id ASC);

-- -----------------------------------------------------
-- Table RoleLinks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstatstest.RoleLinks (
  parent_role_id INT NOT NULL,
  child_role_id INT NOT NULL,
  PRIMARY KEY (parent_role_id, child_role_id),
  CONSTRAINT rolelinks_parent_roles_fk
    FOREIGN KEY (parent_role_id)
    REFERENCES bbstatstest.Roles (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rolelinks_child_roles_fk
    FOREIGN KEY (child_role_id)
    REFERENCES bbstatstest.Roles (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- CREATE INDEX rolelinks_parent_roles_idx ON bbstatstest.RoleLinks (parent_role_id ASC);
-- CREATE INDEX rolelinks_child_roles_idx ON bbstatstest.RoleLinks (child_role_id ASC);
