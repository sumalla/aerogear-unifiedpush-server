CREATE TABLE AndroidVariant (
  googleKey     VARCHAR(255) NOT NULL,
  projectNumber VARCHAR(255) DEFAULT NULL,
  id            VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Table structure for table category
--

CREATE TABLE category (
  id   BIGINT NOT NULL,
  name VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Table structure for table category_seq
--

CREATE SEQUENCE category_seq AS INTEGER START WITH 100;

-- --------------------------------------------------------

--
-- Table structure for table ChromePackagedAppVariant
--

CREATE TABLE ChromePackagedAppVariant (
  clientId     VARCHAR(255) NOT NULL,
  clientSecret VARCHAR(255) NOT NULL,
  refreshToken VARCHAR(255) NOT NULL,
  id           VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Table structure for table installation
--

CREATE TABLE installation (
  id              VARCHAR(255) NOT NULL,
  alias           VARCHAR(255)  DEFAULT NULL,
  deviceToken     VARCHAR(4096) DEFAULT NULL,
  deviceType      VARCHAR(255)  DEFAULT NULL,
  enabled         BOOLEAN      NOT NULL,
  operatingSystem VARCHAR(255)  DEFAULT NULL,
  osVersion       VARCHAR(255)  DEFAULT NULL,
  platform        VARCHAR(255)  DEFAULT NULL,
  variantID       VARCHAR(255)  DEFAULT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Table structure for table installation_category
--

CREATE TABLE installation_category (
  installation_id VARCHAR(255) NOT NULL,
  category_id     BIGINT       NOT NULL,
  PRIMARY KEY (installation_id, category_id)
);

-- --------------------------------------------------------

--
-- Table structure for table ios_variant
--

CREATE TABLE ios_variant (
  passphrase VARCHAR(255) NOT NULL,
  production BOOLEAN      NOT NULL DEFAULT FALSE,
  id         VARCHAR(255) NOT NULL,
  cert_data  VARCHAR(1000)     NOT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Table structure for table PushApplication
--

CREATE TABLE PushApplication (
  id                VARCHAR(255) NOT NULL,
  description       VARCHAR(255) DEFAULT NULL,
  developer         VARCHAR(255) DEFAULT NULL,
  masterSecret      VARCHAR(255) DEFAULT NULL,
  name              VARCHAR(255) NOT NULL,
  pushApplicationID VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Table structure for table PushMessageInformation
--

CREATE TABLE PushMessageInformation (
  id                VARCHAR(255) NOT NULL,
  clientIdentifier  VARCHAR(255)  DEFAULT NULL,
  ipAddress         VARCHAR(255)  DEFAULT NULL,
  pushApplicationId VARCHAR(255) NOT NULL,
  rawJsonMessage    VARCHAR(4500) DEFAULT NULL,
  submitDate        TIMESTAMP      DEFAULT NULL,
  totalReceivers    BIGINT       NOT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Table structure for table SimplePushVariant
--

CREATE TABLE SimplePushVariant (
  id VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Table structure for table ups_db_changelog
--

CREATE TABLE ups_db_changelog (
  ID            VARCHAR(255) NOT NULL,
  AUTHOR        VARCHAR(255) NOT NULL,
  FILENAME      VARCHAR(255) NOT NULL,
  DATEEXECUTED  TIMESTAMP     NOT NULL,
  ORDEREXECUTED INT          NOT NULL,
  EXECTYPE      VARCHAR(10)  NOT NULL,
  MD5SUM        VARCHAR(35)  DEFAULT NULL,
  DESCRIPTION   VARCHAR(255) DEFAULT NULL,
  COMMENTS      VARCHAR(255) DEFAULT NULL,
  TAG           VARCHAR(255) DEFAULT NULL,
  LIQUIBASE     VARCHAR(20)  DEFAULT NULL
);


-- --------------------------------------------------------

--
-- Table structure for table ups_db_changeloglock
--

CREATE TABLE ups_db_changeloglock (
  ID          INT     NOT NULL,
  LOCKED      BOOLEAN NOT NULL,
  LOCKGRANTED TIMESTAMP     DEFAULT NULL,
  LOCKEDBY    VARCHAR(255) DEFAULT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table Variant
--

CREATE TABLE Variant (
  VARIANT_TYPE VARCHAR(31)  NOT NULL,
  id           VARCHAR(255) NOT NULL,
  description  VARCHAR(255) DEFAULT NULL,
  developer    VARCHAR(255) DEFAULT NULL,
  name         VARCHAR(255) DEFAULT NULL,
  secret       VARCHAR(255) DEFAULT NULL,
  type         INT          DEFAULT NULL,
  variantID    VARCHAR(255) DEFAULT NULL,
  variants_id  VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Table structure for table VariantMetricInformation
--

CREATE TABLE VariantMetricInformation (
  id                     VARCHAR(255) NOT NULL,
  deliveryStatus         BOOLEAN      DEFAULT NULL,
  reason                 VARCHAR(255) DEFAULT NULL,
  receivers              BIGINT       NOT NULL,
  variantID              VARCHAR(255) NOT NULL,
  variantInformations_id VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

