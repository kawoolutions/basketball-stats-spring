
-- -----------------------------------------------------
-- Schema bbstats
-- -----------------------------------------------------
CREATE SCHEMA bbstats AUTHORIZATION DBA;

-- -----------------------------------------------------
-- Table GeoContexts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.GeoContexts (
  id INT NOT NULL AUTO_INCREMENT,
  parent_id INT NULL DEFAULT NULL,
  name VARCHAR(50) NOT NULL,
  type ENUM('CONTINENT', 'COUNTRY', 'REGION', 'STATE', 'DISTRICT') NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT geocontexts_self_fk
    FOREIGN KEY (parent_id)
    REFERENCES bbstats.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX geocontexts_self_idx ON bbstats.GeoContexts (parent_id ASC);

-- -----------------------------------------------------
-- Table Continents
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Continents (
  id INT NOT NULL,
  iso_code CHAR(2) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT continents_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE UNIQUE INDEX continents_iso_code_uq ON bbstats.Continents (iso_code ASC);

-- -----------------------------------------------------
-- Table Countries
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Countries (
  id INT NOT NULL,
  iso_code CHAR(2) NOT NULL,
  iso_nbr CHAR(3) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT countries_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE UNIQUE INDEX countries_iso_code_uq ON bbstats.Countries (iso_code ASC);
CREATE UNIQUE INDEX countries_iso_nbr_uq ON bbstats.Countries (iso_nbr ASC);
CREATE INDEX countries_geoareas_idx ON bbstats.Countries (id ASC);

-- -----------------------------------------------------
-- Table Regions
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Regions (
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT regions_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table States
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.States (
  id INT NOT NULL,
  country_code CHAR(2) NOT NULL,
  iso_code VARCHAR(5) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT states_countries_fk
    FOREIGN KEY (country_code)
    REFERENCES bbstats.Countries (iso_code)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT states_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE UNIQUE INDEX states_multi_uq ON bbstats.States (country_code ASC, iso_code ASC);
CREATE INDEX states_countries_idx ON bbstats.States (country_code ASC);
CREATE INDEX states_geoareas_idx ON bbstats.States (id ASC);

-- -----------------------------------------------------
-- Table Districts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Districts (
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT districts_geocontexts_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Contacts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Contacts (
  id INT NOT NULL AUTO_INCREMENT,
  type ENUM('P', 'C', 'A') NOT NULL,
  PRIMARY KEY (id));


-- -----------------------------------------------------
-- Table Addresses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Addresses (
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
    REFERENCES bbstats.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX postaddresses_contacts_idx ON bbstats.Addresses (contact_id ASC);

-- -----------------------------------------------------
-- Table PhoneNumbers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.PhoneNumbers (
  contact_id INT NOT NULL,
  type ENUM('MOBILE', 'FIXED', 'FAX') NOT NULL DEFAULT 'MOBILE',
  country_code SMALLINT NOT NULL,
  area_code SMALLINT NOT NULL,
  subscriber_nbr INT NOT NULL,
  PRIMARY KEY (type, contact_id),
  CONSTRAINT phonenumbers_contacts_fk
    FOREIGN KEY (contact_id)
    REFERENCES bbstats.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX phonenumbers_contacts_idx ON bbstats.PhoneNumbers (contact_id ASC);

-- -----------------------------------------------------
-- Table EmailAddresses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.EmailAddresses (
  contact_id INT NOT NULL,
  index INT NOT NULL,
  uri VARCHAR(64) NOT NULL,
  PRIMARY KEY (contact_id, index),
  CONSTRAINT emailaddresses_contacts_fk
    FOREIGN KEY (contact_id)
    REFERENCES bbstats.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX emailaddresses_contacts_idx ON bbstats.EmailAddresses (contact_id ASC);

-- -----------------------------------------------------
-- Table Colors
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Colors (
  name VARCHAR(25) NOT NULL,
  rgb BINARY(3) NOT NULL,
  PRIMARY KEY (name));

CREATE UNIQUE INDEX colors_rgb_uq ON bbstats.Colors (rgb ASC);

-- -----------------------------------------------------
-- Table Clubs
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Clubs (
  id INT NOT NULL,
  district_id INT NOT NULL,
  color_name VARCHAR(25) NULL,
  name VARCHAR(50) NOT NULL,
  code VARCHAR(5) NOT NULL,
  website_url VARCHAR(64) NULL DEFAULT NULL,
  logo MEDIUMBLOB NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT clubs_colors_fk
    FOREIGN KEY (color_name)
    REFERENCES bbstats.Colors (name)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT clubs_contacts_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT clubs_districts_fk
    FOREIGN KEY (district_id)
    REFERENCES bbstats.Districts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE UNIQUE INDEX clubs_multi_uq ON bbstats.Clubs (district_id ASC, code ASC);
CREATE INDEX clubs_colors_idx ON bbstats.Clubs (color_name ASC);
CREATE INDEX clubs_districts_idx ON bbstats.Clubs (district_id ASC);

-- -----------------------------------------------------
-- Table Arenas
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Arenas (
  id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  capacity INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT arenas_contacts_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Tenancies
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Tenancies (
  club_id INT NOT NULL,
  arena_id INT NOT NULL,
  ordinal_nbr SMALLINT NOT NULL,
  PRIMARY KEY (club_id, arena_id),
  CONSTRAINT tenancies_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES bbstats.Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT tenancies_arenas_fk
    FOREIGN KEY (arena_id)
    REFERENCES bbstats.Arenas (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX tenancies_clubs_idx ON bbstats.Tenancies (club_id ASC);
CREATE INDEX tenancies_arenas_idx ON bbstats.Tenancies (arena_id ASC);

-- -----------------------------------------------------
-- Table Persons
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Persons (
  id INT NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  gender ENUM('MALE', 'FEMALE') NOT NULL DEFAULT 'MALE',
  birth_date DATE NULL DEFAULT '1976-03-03',
  is_incognito BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  CONSTRAINT persons_contacts_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.Contacts (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Referees
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Referees (
  id INT NOT NULL,
  license_nbr VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT referees_persons_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX referees_persons_idx ON bbstats.Referees (id ASC);

-- -----------------------------------------------------
-- Table Coaches
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Coaches (
  id INT NOT NULL,
  license_nbr VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT coaches_persons_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Players
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Players (
  id INT NOT NULL,
  registration_nbr VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT players_persons_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table TeamTypes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.TeamTypes (
  code CHAR(4) NOT NULL,
  age_group ENUM('O20', 'O30', 'O35', 'O40', 'O45', 'O50', 'O55', 'O60', 'O65', 'U20', 'U19', 'U18', 'U17', 'U16', 'U15', 'U14', 'U13', 'U12', 'U11', 'U10', 'U09', 'U08') NOT NULL DEFAULT 'O20',
  gender ENUM('MALE', 'FEMALE', 'MIXED') NOT NULL DEFAULT 'MALE',
  PRIMARY KEY (code));

CREATE UNIQUE INDEX teamtypes_multi_uq ON bbstats.TeamTypes (age_group ASC, gender ASC);

-- -----------------------------------------------------
-- Table Teams
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Teams (
  club_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  ordinal_nbr SMALLINT NOT NULL,
  PRIMARY KEY (club_id, team_type_code, ordinal_nbr),
  CONSTRAINT teams_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES bbstats.Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT teams_teamtypes_fk
    FOREIGN KEY (team_type_code)
    REFERENCES bbstats.TeamTypes (code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX teams_clubs_idx ON bbstats.Teams (club_id ASC);
CREATE INDEX teams_teamtypes_idx ON bbstats.Teams (team_type_code ASC);

-- -----------------------------------------------------
-- Table Competitions
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Competitions (
  geo_context_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  type ENUM('REGULAR_SEASON', 'CUP', 'PLAYOFFS', 'RELEGATION', 'QUALIFICATION', 'PREPARATION', 'TOURNAMENT') NOT NULL,
  level SMALLINT NOT NULL DEFAULT 1,
  PRIMARY KEY (geo_context_id, team_type_code, type, level),
  CONSTRAINT competitions_geocontexts_fk
    FOREIGN KEY (geo_context_id)
    REFERENCES bbstats.GeoContexts (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT competitions_teamtypes_fk
    FOREIGN KEY (team_type_code)
    REFERENCES bbstats.TeamTypes (code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX competitions_teamtypes_idx ON bbstats.Competitions (team_type_code ASC);
CREATE INDEX competitions_geocontexts_idx ON bbstats.Competitions (geo_context_id ASC);

-- -----------------------------------------------------
-- Table CompetitionLabels
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.CompetitionLabels (
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
    REFERENCES bbstats.Competitions (geo_context_id , team_type_code , type , level)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table Seasons
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Seasons (
  start_year INT NOT NULL,
  PRIMARY KEY (start_year));

  
-- -----------------------------------------------------
-- Table Rounds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Rounds (
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
    REFERENCES bbstats.Competitions (geo_context_id , team_type_code , type , level)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rounds_seasons_fk
    FOREIGN KEY (season_start_year)
    REFERENCES bbstats.Seasons (start_year)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX rounds_seasons_idx ON bbstats.Rounds (season_start_year ASC);
CREATE INDEX rounds_competitions_idx ON bbstats.Rounds (geo_context_id ASC, team_type_code ASC, competition_type ASC, competition_level ASC);

-- -----------------------------------------------------
-- Table RankingRounds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.RankingRounds (
  id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT rankingrounds_rounds_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.Rounds (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table EliminationRounds
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.EliminationRounds (
  id INT NOT NULL,
  best_of SMALLINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT eliminationrounds_rounds_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.Rounds (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table Rosters
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Rosters (
  id INT NOT NULL AUTO_INCREMENT,
  club_id INT NOT NULL,
  team_type_code CHAR(4) NOT NULL,
  team_ordinal_nbr SMALLINT NOT NULL,
  season_start_year INT NOT NULL,
  primary_jersey_color_name VARCHAR(25) NULL DEFAULT NULL,
  secondary_jersey_color_name VARCHAR(25) NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT rosters_seasons_fk
    FOREIGN KEY (season_start_year)
    REFERENCES bbstats.Seasons (start_year)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rosters_teams_fk
    FOREIGN KEY (club_id , team_type_code , team_ordinal_nbr)
    REFERENCES bbstats.Teams (club_id , team_type_code , ordinal_nbr)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rosters_primary_jersey_colors_fk
    FOREIGN KEY (primary_jersey_color_name)
    REFERENCES bbstats.Colors (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rosters_secondary_jersey_colors_fk
    FOREIGN KEY (secondary_jersey_color_name)
    REFERENCES bbstats.Colors (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE UNIQUE INDEX rosters_multi_uq ON bbstats.Rosters (club_id ASC, team_type_code ASC, team_ordinal_nbr ASC, season_start_year ASC);
CREATE INDEX rosters_seasons_idx ON bbstats.Rosters (season_start_year ASC);
CREATE INDEX rosters_teams_idx ON bbstats.Rosters (club_id ASC, team_type_code ASC, team_ordinal_nbr ASC);
CREATE INDEX rosters_primary_jersey_colors_idx ON bbstats.Rosters (primary_jersey_color_name ASC);
CREATE INDEX rosters_secondary_jersey_colors_idx ON bbstats.Rosters (secondary_jersey_color_name ASC);

-- -----------------------------------------------------
-- Table RefpoolMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.RefpoolMembers (
  referee_id INT NOT NULL,
  club_id INT NOT NULL,
  season_start_year INT NOT NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (referee_id, club_id, season_start_year),
  CONSTRAINT refpoolmembers_referees_fk
    FOREIGN KEY (referee_id)
    REFERENCES bbstats.Referees (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT refpoolmembers_seasons_fk
    FOREIGN KEY (season_start_year)
    REFERENCES bbstats.Seasons (start_year)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT refpoolmembers_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES bbstats.Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX refpoolmembers_referees_idx ON bbstats.RefpoolMembers (referee_id ASC);
CREATE INDEX refpoolmembers_seasons_idx ON bbstats.RefpoolMembers (season_start_year ASC);
CREATE INDEX refpoolmembers_clubs_idx ON bbstats.RefpoolMembers (club_id ASC);

-- -----------------------------------------------------
-- Table StaffMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.StaffMembers (
  coach_id INT NOT NULL,
  roster_id INT NOT NULL,
  role ENUM('HEAD_COACH', 'ASSISTANT_COACH', 'ATHLETIC_TRAINER', 'PHYSICIAN', 'DOCTOR') NULL DEFAULT NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (roster_id, coach_id),
  CONSTRAINT staffmembers_coaches_fk
    FOREIGN KEY (coach_id)
    REFERENCES bbstats.Coaches (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT staffmembers_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES bbstats.Rosters (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX staffmembers_coaches_idx ON bbstats.StaffMembers (coach_id ASC);
CREATE INDEX staffmembers_rosters_idx ON bbstats.StaffMembers (roster_id ASC);

-- -----------------------------------------------------
-- Table TeamMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.TeamMembers (
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  position ENUM('CENTER', 'POWER_FORWARD', 'SMALL_FORWARD', 'SHOOTING_GUARD', 'POINT_GUARD') NULL DEFAULT NULL,
  eligible_to_play_since DATE NULL DEFAULT NULL,
  image_path VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (player_id, roster_id),
  CONSTRAINT teammembers_players_fk
    FOREIGN KEY (player_id)
    REFERENCES bbstats.Players (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT teammembers_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES bbstats.Rosters (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX teammembers_players_idx ON bbstats.TeamMembers (player_id ASC);
CREATE INDEX teammembers_rosters_idx ON bbstats.TeamMembers (roster_id ASC);

-- -----------------------------------------------------
-- Table GroupLabels
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.GroupLabels (
  code VARCHAR(6) NOT NULL,
  name VARCHAR(50) NULL,
  PRIMARY KEY (code));


-- -----------------------------------------------------
-- Table Groups
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Groups (
  round_id INT NOT NULL,
  code VARCHAR(6) NOT NULL,
  official_code VARCHAR(6) NULL DEFAULT NULL,
  max_members TINYINT(2) NULL DEFAULT NULL,
  PRIMARY KEY (round_id, code),
  CONSTRAINT groups_rounds_fk
    FOREIGN KEY (round_id)
    REFERENCES bbstats.Rounds (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT groups_grouplabels_fk
    FOREIGN KEY (code)
    REFERENCES bbstats.GroupLabels (code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX groups_rounds_idx ON bbstats.Groups (round_id ASC);
CREATE INDEX groups_grouplabels_idx ON bbstats.Groups (code ASC);

-- -----------------------------------------------------
-- Table GroupMembers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.GroupMembers (
  roster_id INT NOT NULL,
  round_id INT NOT NULL,
  group_code VARCHAR(6) NOT NULL,
  withdrawn BOOLEAN NOT NULL,
  PRIMARY KEY (roster_id, round_id, group_code),
  CONSTRAINT groupmembers_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES bbstats.Rosters (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT groupmembers_groups_fk
    FOREIGN KEY (round_id , group_code)
    REFERENCES bbstats.Groups (round_id , code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX groupmembers_rosters_idx ON bbstats.GroupMembers (roster_id ASC);
CREATE INDEX groupmembers_groups_idx ON bbstats.GroupMembers (round_id ASC, group_code ASC);

-- -----------------------------------------------------
-- Table Games
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Games (
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
    REFERENCES bbstats.Clubs (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT games_arenas_fk
    FOREIGN KEY (arena_id)
    REFERENCES bbstats.Arenas (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT games_groups_fk
    FOREIGN KEY (round_id , group_code)
    REFERENCES bbstats.Groups (round_id , code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX games_ref_clubs_idx ON bbstats.Games (ref_club_id ASC);
CREATE INDEX games_arenas_idx ON bbstats.Games (arena_id ASC);
CREATE INDEX games_groups_idx ON bbstats.Games (round_id ASC, group_code ASC);

-- -----------------------------------------------------
-- Table Scores
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Scores (
  game_id INT NOT NULL,
  is_home BOOLEAN NOT NULL,
  roster_id INT NOT NULL,
  final_score SMALLINT NULL DEFAULT NULL,
  PRIMARY KEY (game_id, is_home),
  CONSTRAINT scores_games_fk
    FOREIGN KEY (game_id)
    REFERENCES bbstats.Games (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT scores_rosters_fk
    FOREIGN KEY (roster_id)
    REFERENCES bbstats.Rosters (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX scores_games_idx ON bbstats.Scores(game_id ASC);
CREATE INDEX scores_rosters_idx ON bbstats.Scores (roster_id ASC);

-- -----------------------------------------------------
-- Table PlayerStats
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.PlayerStats (
  game_id INT NOT NULL,
  is_home BOOLEAN NOT NULL,
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  jersey_nbr TINYINT NOT NULL,
  has_played BOOLEAN NOT NULL DEFAULT TRUE,
  is_starter BOOLEAN NULL DEFAULT NULL,
  pf TINYINT NOT NULL,
  PRIMARY KEY (game_id, is_home, player_id, roster_id),
  CONSTRAINT playerstats_scores_fk
    FOREIGN KEY (game_id , is_home)
    REFERENCES bbstats.Scores (game_id , is_home)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT playerstats_teammembers_fk
    FOREIGN KEY (player_id , roster_id)
    REFERENCES bbstats.TeamMembers (player_id , roster_id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE UNIQUE INDEX playerstats_multi_uq ON bbstats.PlayerStats (game_id ASC, is_home ASC, jersey_nbr ASC);
CREATE INDEX playerstats_scores_idx ON bbstats.PlayerStats (game_id ASC, is_home ASC);
CREATE INDEX playerstats_teammembers_idx ON bbstats.PlayerStats (player_id ASC, roster_id ASC);

-- -----------------------------------------------------
-- Table Stats
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Stats (
  game_id INT NOT NULL,
  is_home BOOLEAN NOT NULL,
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  period TINYINT NOT NULL,
  tpm TINYINT NOT NULL,
  ftm TINYINT NOT NULL,
  fta TINYINT NOT NULL,
  pts TINYINT NOT NULL,
  PRIMARY KEY (game_id, is_home, period, player_id, roster_id),
  CONSTRAINT stats_playerstats_fk
    FOREIGN KEY (game_id , is_home , player_id , roster_id)
    REFERENCES bbstats.PlayerStats (game_id , is_home , player_id , roster_id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX stats_playerstats_idx ON bbstats.Stats (game_id ASC, is_home ASC, player_id ASC, roster_id ASC);

-- -----------------------------------------------------
-- Table Actions
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Actions (
  game_id INT NOT NULL,
  is_home BOOLEAN NOT NULL,
  player_id INT NOT NULL,
  roster_id INT NOT NULL,
  minute TINYINT NOT NULL,
  nbr TINYINT NOT NULL,
  type ENUM('TWO_POINTER_MADE', 'THREE_POINTER_MADE', 'FREE_THROW_MADE', 'FREE_THROW_MISSED', 'NON_SHOOTING_FOUL', 'SHOOTING_FOUL', 'TECHNICAL_FOUL', 'FLAGRANT_FOUL', 'DISQUALIFYING_FOUL') NOT NULL,
  PRIMARY KEY (game_id, is_home, minute, player_id, roster_id, nbr),
  CONSTRAINT actions_playerstats_fk
    FOREIGN KEY (game_id , is_home , player_id , roster_id)
    REFERENCES bbstats.PlayerStats (game_id , is_home , player_id , roster_id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX actions_playerstats_idx ON bbstats.Actions (game_id ASC, is_home ASC, player_id ASC, roster_id ASC);

-- -----------------------------------------------------
-- Table Assignments
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Assignments (
  referee_id INT NOT NULL,
  club_id INT NOT NULL,
  season_start_year INT NOT NULL,
  game_id INT NOT NULL,
  owner_club_id INT NULL,
  was_absent BOOLEAN NULL DEFAULT FALSE,
  PRIMARY KEY (referee_id, club_id, season_start_year, game_id),
  CONSTRAINT assignments_games_fk
    FOREIGN KEY (game_id)
    REFERENCES bbstats.Games (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT assignments_owner_clubs_fk
    FOREIGN KEY (owner_club_id)
    REFERENCES bbstats.Clubs (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT assignments_refpoolmembers_fk
    FOREIGN KEY (referee_id , club_id , season_start_year)
    REFERENCES bbstats.RefpoolMembers (referee_id , club_id , season_start_year)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX assignments_games_idx ON bbstats.Assignments (game_id ASC);
CREATE INDEX assignments_owner_clubs_idx ON bbstats.Assignments (owner_club_id ASC);
CREATE INDEX assignments_refpoolmembers_idx ON bbstats.Assignments (referee_id ASC, club_id ASC, season_start_year ASC);

-- -----------------------------------------------------
-- Table Managers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Managers (
  person_id INT NOT NULL,
  club_id INT NOT NULL,
  role ENUM('OWNER', 'CHAIRMAN', 'HEAD_OF_DEPT', 'REFEREE_WARDEN', 'WEBMASTER') NOT NULL,
  PRIMARY KEY (person_id, club_id, role),
  CONSTRAINT managers_clubs_fk
    FOREIGN KEY (club_id)
    REFERENCES bbstats.Clubs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT managers_persons_fk
    FOREIGN KEY (person_id)
    REFERENCES bbstats.Persons (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX managers_clubs_idx ON bbstats.Managers (club_id ASC);
CREATE INDEX managers_persons_idx ON bbstats.Managers (person_id ASC);

-- -----------------------------------------------------
-- Table GroupLinks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.GroupLinks (
  parent_round_id INT NOT NULL,
  parent_group_code VARCHAR(6) NOT NULL,
  child_round_id INT NOT NULL,
  child_group_code VARCHAR(6) NOT NULL,
  PRIMARY KEY (parent_round_id, parent_group_code, child_round_id, child_group_code),
  CONSTRAINT grouplinks_parent_groups_fk
    FOREIGN KEY (parent_round_id , parent_group_code)
    REFERENCES bbstats.Groups (round_id , code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT grouplinks_child_groups_fk
    FOREIGN KEY (child_round_id , child_group_code)
    REFERENCES bbstats.Groups (round_id , code)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX grouplinks_child_groups_idx ON bbstats.GroupLinks (child_round_id ASC, child_group_code ASC);
CREATE INDEX grouplinks_parent_groups_idx ON bbstats.GroupLinks (parent_round_id ASC, parent_group_code ASC);

-- -----------------------------------------------------
-- Table Users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Users (
  name VARCHAR(20) NOT NULL,
  person_id INT NULL,
  password VARCHAR(60) NOT NULL,
  new_password VARCHAR(60) NULL DEFAULT NULL,
  salt VARCHAR(30) NULL DEFAULT NULL,
  is_enabled BOOLEAN NOT NULL DEFAULT TRUE,
  locale_code VARCHAR(5) NOT NULL DEFAULT 'de',
  theme_name VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (name),
  CONSTRAINT users_persons_fk
    FOREIGN KEY (person_id)
    REFERENCES bbstats.Persons (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE UNIQUE INDEX users_person_id_uq ON bbstats.Users (person_id ASC);

-- -----------------------------------------------------
-- Table Roles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.Roles (
  id INT NOT NULL,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id));

CREATE UNIQUE INDEX roles_name_uq ON bbstats.Roles (name ASC);

-- -----------------------------------------------------
-- Table UserRoleLinks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.UserRoleLinks (
  user_name VARCHAR(20) NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_name, role_id),
  CONSTRAINT userrolelinks_users_fk
    FOREIGN KEY (user_name)
    REFERENCES bbstats.Users (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT userrolelinks_roles_fk
    FOREIGN KEY (role_id)
    REFERENCES bbstats.Roles (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX userrolelinks_roles_idx ON bbstats.UserRoleLinks (role_id ASC);

-- -----------------------------------------------------
-- Table RoleLinks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.RoleLinks (
  parent_role_id INT NOT NULL,
  child_role_id INT NOT NULL,
  PRIMARY KEY (parent_role_id, child_role_id),
  CONSTRAINT rolelinks_parent_roles_fk
    FOREIGN KEY (parent_role_id)
    REFERENCES bbstats.Roles (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT rolelinks_child_roles_fk
    FOREIGN KEY (child_role_id)
    REFERENCES bbstats.Roles (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX rolelinks_parent_roles_idx ON bbstats.RoleLinks (parent_role_id ASC);
CREATE INDEX rolelinks_child_roles_idx ON bbstats.RoleLinks (child_role_id ASC);

-- -----------------------------------------------------
-- Table AltRoles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.AltRoles (
  name VARCHAR(10) NOT NULL,
  parent_name VARCHAR(10) NULL,
  PRIMARY KEY (name),
  CONSTRAINT altroles_self_fk
    FOREIGN KEY (parent_name)
    REFERENCES bbstats.AltRoles (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX altroles_self_idx ON bbstats.AltRoles (parent_name ASC);


-- -----------------------------------------------------
-- Table AltUsers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bbstats.AltUsers (
  id INT NOT NULL,
  name VARCHAR(20) NOT NULL,
  pass VARCHAR(16) NOT NULL,
  is_enabled BOOLEAN NULL,
  role_name VARCHAR(10) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT altusers_persons_fk
    FOREIGN KEY (id)
    REFERENCES bbstats.Persons (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT altusers_altroles_fk
    FOREIGN KEY (role_name)
    REFERENCES AltRoles (name)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE INDEX altusers_altroles_idx ON bbstats.AltUsers (role_name ASC);

