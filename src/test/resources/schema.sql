
-- -----------------------------------------------------
-- Schema bbstatstest
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS bbstatstest;

-- SET SCHEMA bbstatstest;

-- -----------------------------------------------------
-- Table GeoContexts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS GeoContexts (
  id INT NOT NULL,
  parent_id INT NULL DEFAULT NULL,
  name VARCHAR(50) NOT NULL,
  type ENUM('CONTINENT', 'COUNTRY', 'REGION', 'STATE', 'DISTRICT') NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT geocontexts_self_fk
    FOREIGN KEY (parent_id)
    REFERENCES GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Continents
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Continents (
  id INT NOT NULL,
  iso_code CHAR(2) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT continents_iso_code_uq UNIQUE (iso_code ASC),
  CONSTRAINT continents_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Countries
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Countries (
  id INT NOT NULL,
  iso_code CHAR(2) NOT NULL,
  iso_nbr CHAR(3) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT countries_iso_code_uq UNIQUE (iso_code ASC),
  CONSTRAINT countries_iso_nbr_uq UNIQUE (iso_nbr ASC),
  CONSTRAINT countries_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Regions
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Regions (
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT regions_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table States
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS States (
  id INT NOT NULL,
  country_code CHAR(2) NOT NULL,
  iso_code VARCHAR(5) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT states_multi_uq UNIQUE (country_code ASC, iso_code ASC),
  CONSTRAINT states_countries_fk
    FOREIGN KEY (country_code)
    REFERENCES Countries (iso_code)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT states_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Districts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Districts (
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT districts_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Contacts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Contacts (
  id INT NOT NULL AUTO_INCREMENT,
  type ENUM('P', 'C', 'A') NOT NULL,
  PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table Addresses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Addresses (
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
    REFERENCES Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table PhoneNumbers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS PhoneNumbers (
  contact_id INT NOT NULL,
  type ENUM('MOBILE', 'FIXED', 'FAX') NOT NULL DEFAULT 'MOBILE',
  country_code SMALLINT NOT NULL,
  area_code SMALLINT NOT NULL,
  subscriber_nbr INT NOT NULL,
  PRIMARY KEY (type, contact_id),
  CONSTRAINT phonenumbers_contacts_fk
    FOREIGN KEY (contact_id)
    REFERENCES Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table EmailAddresses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS EmailAddresses (
  contact_id INT NOT NULL,
  idx INT NOT NULL,
  uri VARCHAR(64) NOT NULL,
  PRIMARY KEY (contact_id, idx),
  CONSTRAINT emailaddresses_contacts_fk
    FOREIGN KEY (contact_id)
    REFERENCES Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Colors
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Colors (
  name VARCHAR(25) NOT NULL,
  rgb BINARY(3) NOT NULL,
  PRIMARY KEY (name),
  CONSTRAINT colors_rgb_uq UNIQUE (rgb ASC));

-- -----------------------------------------------------
-- Table Clubs
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Clubs (
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
    REFERENCES Colors (name)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT clubs_contacts_fk
    FOREIGN KEY (id)
    REFERENCES Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT clubs_districts_fk
    FOREIGN KEY (district_id)
    REFERENCES Districts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Arenas
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Arenas (
  id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  capacity INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT arenas_contacts_fk
    FOREIGN KEY (id)
    REFERENCES Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Tenancies
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Tenancies (
  club_id INT NOT NULL,
  arena_id INT NOT NULL,
  ordinal_nbr SMALLINT NOT NULL,
  PRIMARY KEY (club_id, arena_id),
  CONSTRAINT tenancies_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT tenancies_arenas_fk
    FOREIGN KEY (arena_id)
    REFERENCES Arenas (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Persons
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Persons (
  id INT NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  gender ENUM('MALE', 'FEMALE') NOT NULL DEFAULT 'MALE',
  birth_date DATE NULL DEFAULT '1976-03-03',
  is_incognito BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  CONSTRAINT persons_contacts_fk
    FOREIGN KEY (id)
    REFERENCES Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Referees
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Referees (
  id INT NOT NULL,
  license_nbr VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT referees_persons_fk
    FOREIGN KEY (id)
    REFERENCES Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Coaches
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Coaches (
  id INT NOT NULL,
  license_nbr VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT coaches_persons_fk
    FOREIGN KEY (id)
    REFERENCES Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Players
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Players (
  id INT NOT NULL,
  registration_nbr VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT players_persons_fk
    FOREIGN KEY (id)
    REFERENCES Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table TeamTypes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS TeamTypes (
  code CHAR(4) NOT NULL,
  age_group ENUM('O20', 'O30', 'O35', 'O40', 'O45', 'O50', 'O55', 'O60', 'O65', 'U20', 'U19', 'U18', 'U17', 'U16', 'U15', 'U14', 'U13', 'U12', 'U11', 'U10', 'U09', 'U08') NOT NULL DEFAULT 'O20',
  gender ENUM('MALE', 'FEMALE', 'MIXED') NOT NULL DEFAULT 'MALE',
  PRIMARY KEY (code),
  CONSTRAINT teamtypes_multi_uq UNIQUE (age_group ASC, gender ASC));

-- -----------------------------------------------------
-- Table Teams
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Teams (
  club_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  ordinal_nbr SMALLINT NOT NULL,
  PRIMARY KEY (club_id, team_type_code, ordinal_nbr),
  CONSTRAINT teams_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT teams_teamtypes_fk
    FOREIGN KEY (team_type_code)
    REFERENCES TeamTypes (code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Competitions
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Competitions (
  geo_context_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  type ENUM('REGULAR_SEASON', 'CUP', 'PLAYOFFS', 'RELEGATION', 'QUALIFICATION', 'PREPARATION', 'TOURNAMENT') NOT NULL,
  level SMALLINT NOT NULL DEFAULT 1,
  PRIMARY KEY (geo_context_id, team_type_code, type, level),
  CONSTRAINT competitions_geocontexts_fk
    FOREIGN KEY (geo_context_id)
    REFERENCES GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT competitions_teamtypes_fk
    FOREIGN KEY (team_type_code)
    REFERENCES TeamTypes (code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table CompetitionLabels
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS CompetitionLabels (
  geo_context_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  competition_type ENUM('REGULAR_SEASON', 'CUP', 'PLAYOFFS', 'RELEGATION', 'QUALIFICATION', 'PREPARATION', 'TOURNAMENT') NOT NULL,
  competition_level SMALLINT NOT NULL,
  season_start_year INT NOT NULL DEFAULT 1966,
  name VARCHAR(50) NOT NULL,
  code VARCHAR(6) NULL DEFAULT NULL,
  PRIMARY KEY (geo_context_id, team_type_code, competition_type, competition_level, season_start_year),
  CONSTRAINT competitionlabels_competitions_fk
    FOREIGN KEY (geo_context_id, team_type_code, competition_type, competition_level)
    REFERENCES Competitions (geo_context_id, team_type_code, type, level)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Seasons
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Seasons (
  start_year INT NOT NULL,
  PRIMARY KEY (start_year));

-- -----------------------------------------------------
-- Table Rounds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Rounds (
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
    FOREIGN KEY (geo_context_id, team_type_code, competition_type, competition_level)
    REFERENCES Competitions (geo_context_id, team_type_code, type, level)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rounds_seasons_fk
    FOREIGN KEY (season_start_year)
    REFERENCES Seasons (start_year)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table RankingRounds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS RankingRounds (
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT rankingrounds_rounds_fk
    FOREIGN KEY (id)
    REFERENCES Rounds (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table EliminationRounds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS EliminationRounds (
  id INT NOT NULL,
  best_of SMALLINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT eliminationrounds_rounds_fk
    FOREIGN KEY (id)
    REFERENCES Rounds (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Rosters
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Rosters (
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
    REFERENCES Seasons (start_year)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rosters_teams_fk
    FOREIGN KEY (club_id, team_type_code, team_ordinal_nbr)
    REFERENCES Teams (club_id, team_type_code, ordinal_nbr)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rosters_primary_jersey_colors_fk
    FOREIGN KEY (primary_jersey_color_name)
    REFERENCES Colors (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rosters_secondary_jersey_colors_fk
    FOREIGN KEY (secondary_jersey_color_name)
    REFERENCES Colors (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table RefpoolMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS RefpoolMembers (
  referee_id INT NOT NULL,
  club_id INT NOT NULL,
  season_start_year INT NOT NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (referee_id, club_id, season_start_year),
  CONSTRAINT refpoolmembers_referees_fk
    FOREIGN KEY (referee_id)
    REFERENCES Referees (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT refpoolmembers_seasons_fk
    FOREIGN KEY (season_start_year)
    REFERENCES Seasons (start_year)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT refpoolmembers_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table StaffMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS StaffMembers (
  coach_id INT NOT NULL,
  roster_id INT NOT NULL,
  role ENUM('HEAD_COACH', 'ASSISTANT_COACH', 'ATHLETIC_TRAINER', 'PHYSICIAN', 'DOCTOR') NULL DEFAULT NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (roster_id, coach_id),
  CONSTRAINT staffmembers_coaches_fk
    FOREIGN KEY (coach_id)
    REFERENCES Coaches (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT staffmembers_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES Rosters (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table TeamMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS TeamMembers (
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  position ENUM('CENTER', 'POWER_FORWARD', 'SMALL_FORWARD', 'SHOOTING_GUARD', 'POINT_GUARD') NULL DEFAULT NULL,
  eligible_to_play_since DATE NULL DEFAULT NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (player_id, roster_id),
  CONSTRAINT teammembers_players_fk
    FOREIGN KEY (player_id)
    REFERENCES Players (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT teammembers_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES Rosters (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table GroupLabels
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS GroupLabels (
  code VARCHAR(6) NOT NULL,
  name VARCHAR(50) NULL,
  PRIMARY KEY (code),
  CONSTRAINT grouplabels_code_uq UNIQUE (code ASC));

-- -----------------------------------------------------
-- Table Groups
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Groups (
  round_id INT NOT NULL,
  code VARCHAR(6) NOT NULL,
  official_code VARCHAR(6) NULL DEFAULT NULL,
  max_members TINYINT NULL DEFAULT NULL,
  PRIMARY KEY (round_id, code),
  CONSTRAINT groups_rounds_fk
    FOREIGN KEY (round_id)
    REFERENCES Rounds (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT groups_grouplabels_fk
    FOREIGN KEY (code)
    REFERENCES GroupLabels (code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table GroupMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS GroupMembers (
  roster_id INT NOT NULL,
  round_id INT NOT NULL,
  group_code VARCHAR(6) NOT NULL,
  withdrawn BOOLEAN NOT NULL,
  PRIMARY KEY (roster_id, round_id, group_code),
  CONSTRAINT groupmembers_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES Rosters (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT groupmembers_groups_fk
    FOREIGN KEY (round_id, group_code)
    REFERENCES Groups (round_id, code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Games
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Games (
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
    REFERENCES Clubs (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT games_arenas_fk
    FOREIGN KEY (arena_id)
    REFERENCES Arenas (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT games_groups_fk
    FOREIGN KEY (round_id, group_code)
    REFERENCES Groups (round_id, code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Scores
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Scores (
  game_id INT NOT NULL,
  home_away ENUM('HOME', 'AWAY') NOT NULL,
  roster_id INT NOT NULL,
  final_score SMALLINT NULL DEFAULT NULL,
  PRIMARY KEY (game_id, home_away),
  CONSTRAINT scores_games_fk
    FOREIGN KEY (game_id)
    REFERENCES Games (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT scores_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES Rosters (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table PlayerStats
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS PlayerStats (
  game_id INT NOT NULL,
  score_home_away ENUM('HOME', 'AWAY') NOT NULL,
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  jersey_nbr TINYINT NOT NULL,
  has_played BOOLEAN NOT NULL DEFAULT TRUE,
  is_starter BOOLEAN NULL DEFAULT NULL,
  pf TINYINT NOT NULL,
  PRIMARY KEY (game_id, score_home_away, player_id, roster_id),
  CONSTRAINT playerstats_multi_uq UNIQUE (game_id ASC, score_home_away ASC, jersey_nbr ASC),
  CONSTRAINT playerstats_scores_fk
    FOREIGN KEY (game_id, score_home_away)
    REFERENCES Scores (game_id, home_away)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT playerstats_teammembers_fk
    FOREIGN KEY (player_id, roster_id)
    REFERENCES TeamMembers (player_id, roster_id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Stats
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Stats (
  game_id INT NOT NULL,
  score_home_away ENUM('HOME', 'AWAY') NOT NULL,
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  "period" TINYINT NOT NULL,
  tpm TINYINT NOT NULL,
  ftm TINYINT NOT NULL,
  fta TINYINT NOT NULL,
  pts TINYINT NOT NULL,
  PRIMARY KEY (game_id, score_home_away, "period", player_id, roster_id),
  CONSTRAINT stats_playerstats_fk
    FOREIGN KEY (game_id, score_home_away, player_id, roster_id)
    REFERENCES PlayerStats (game_id, score_home_away, player_id, roster_id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Assignments
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Assignments (
  referee_id INT NOT NULL,
  club_id INT NOT NULL,
  season_start_year INT NOT NULL,
  game_id INT NOT NULL,
  owner_club_id INT NULL,
  was_absent BOOLEAN NULL DEFAULT FALSE,
  PRIMARY KEY (referee_id, club_id, season_start_year, game_id),
  CONSTRAINT assignments_games_fk
    FOREIGN KEY (game_id)
    REFERENCES Games (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT assignments_owner_clubs_fk
    FOREIGN KEY (owner_club_id)
    REFERENCES Clubs (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT assignments_refpoolmembers_fk
    FOREIGN KEY (referee_id, club_id, season_start_year)
    REFERENCES RefpoolMembers (referee_id, club_id, season_start_year)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Managers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Managers (
  person_id INT NOT NULL,
  club_id INT NOT NULL,
  role ENUM('OWNER', 'CHAIRMAN', 'HEAD_OF_DEPT', 'REFEREE_WARDEN', 'WEBMASTER') NOT NULL,
  PRIMARY KEY (person_id, club_id, role),
  CONSTRAINT managers_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT managers_persons_fk
    FOREIGN KEY (person_id)
    REFERENCES Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table GroupLinks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS GroupLinks (
  parent_round_id INT NOT NULL,
  parent_group_code VARCHAR(6) NOT NULL,
  child_round_id INT NOT NULL,
  child_group_code VARCHAR(6) NOT NULL,
  PRIMARY KEY (parent_round_id, parent_group_code, child_round_id, child_group_code),
  CONSTRAINT grouplinks_parent_groups_fk
    FOREIGN KEY (parent_round_id, parent_group_code)
    REFERENCES Groups (round_id, code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT grouplinks_child_groups_fk
    FOREIGN KEY (child_round_id, child_group_code)
    REFERENCES Groups (round_id, code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Users (
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
    REFERENCES Persons (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Roles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Roles (
  id INT NOT NULL,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT roles_name_uq UNIQUE (name ASC));

-- -----------------------------------------------------
-- Table UserRoleLinks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS UserRoleLinks (
  user_name VARCHAR(20) NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_name, role_id),
  CONSTRAINT userrolelinks_users_fk
    FOREIGN KEY (user_name)
    REFERENCES Users (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT userrolelinks_roles_fk
    FOREIGN KEY (role_id)
    REFERENCES Roles (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table RoleLinks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS RoleLinks (
  parent_role_id INT NOT NULL,
  child_role_id INT NOT NULL,
  PRIMARY KEY (parent_role_id, child_role_id),
  CONSTRAINT rolelinks_parent_roles_fk
    FOREIGN KEY (parent_role_id)
    REFERENCES Roles (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rolelinks_child_roles_fk
    FOREIGN KEY (child_role_id)
    REFERENCES Roles (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
