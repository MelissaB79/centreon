-- --------------------------------------------------------

-- 
-- Structure de la table `log_action`
-- 

CREATE TABLE IF NOT EXISTS `log_action` (
  `action_log_id` int(11) NOT NULL auto_increment,
  `action_log_date` int(11) NOT NULL,
  `object_type` varchar(255) NOT NULL,
  `object_id` int(11) NOT NULL,
  `object_name` varchar(255) NOT NULL,
  `action_type` varchar(255) NOT NULL,
  `log_contact_id` int(11) NOT NULL,
  PRIMARY KEY  (`action_log_id`),
  KEY `log_contact_id` (`log_contact_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

-- 
-- Structure de la table `log_action_modification`
-- 

CREATE TABLE IF NOT EXISTS `log_action_modification` (
  `modification_id` int(11) NOT NULL auto_increment,
  `field_name` varchar(255) NOT NULL,
  `field_value` varchar(255) NOT NULL,
  `action_log_id` int(11) NOT NULL,
  PRIMARY KEY  (`modification_id`),
  KEY `action_log_id` (`action_log_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;
