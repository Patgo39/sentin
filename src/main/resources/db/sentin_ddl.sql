-- Drop schema public cascade;
-- Create schema public;

-- User
CREATE TABLE IF NOT EXISTS sentin_user(
    id_user BIGINT GENERATED ALWAYS AS IDENTITY,
    given_name VARCHAR(20) NOT NULL,
    middle_name VARCHAR(20),
    family_name VARCHAR(60) NOT NULL,
    username VARCHAR(15) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    postal_code CHAR(5) NOT NULL,
    phone_number CHAR(10) NOT NULL,
    rfc CHAR(13) NOT NULL,
    
    CONSTRAINT pk_user PRIMARY KEY (id_user),
    CONSTRAINT uq_username UNIQUE (username),
    CONSTRAINT uq_email UNIQUE (email),
    
    CONSTRAINT ck_given_name_not_empty CHECK (TRIM(given_name) <> ''),
    CONSTRAINT ck_family_name_not_empty CHECK (TRIM(family_name) <> ''),
    CONSTRAINT ck_username_not_empty CHECK (TRIM(username) <> ''),
    CONSTRAINT ck_password_not_empty CHECK (TRIM("password") <> ''),
    CONSTRAINT ck_rfc_longitud CHECK (TRIM(rfc) <> '' AND LENGTH(TRIM(rfc)) = 13),
    CONSTRAINT ck_valid_date CHECK (birth_date <= CURRENT_DATE AND birth_date > '1900-01-01'),
    CONSTRAINT ck_postal_code_formato CHECK (postal_code ~ '^[0-9]{5}$'),
    CONSTRAINT ck_phone_number_formato CHECK (phone_number ~ '^[0-9]{10}$'),
    CONSTRAINT ck_email_formato CHECK (email ~ '^[^@]+@[^@]+\.[^@]+$')
);

-- TAG
CREATE TABLE IF NOT EXISTS tag(
    id_tag BIGINT GENERATED ALWAYS AS IDENTITY,
    id_user BIGINT NULL,
    tag_name VARCHAR(60) NOT NULL,
    
    CONSTRAINT pk_tag PRIMARY KEY (id_tag),
    CONSTRAINT fk_user FOREIGN KEY (id_user)
       REFERENCES sentin_user (id_user) ON DELETE CASCADE,
    CONSTRAINT uq_user_tag_name UNIQUE (id_user, tag_name), 
    CONSTRAINT ck_valid_name CHECK (TRIM(tag_name) <> '')
);

INSERT INTO tag (tag_name) VALUES 
	('Comida'),
	('Transporte'),
	('Vivienda');

CREATE UNIQUE INDEX uq_global_tags ON tag (tag_name) WHERE id_user IS NULL;

-- TAX CLASSIFCATIONS
CREATE TABLE IF NOT EXISTS tax_classifications(
    id_classification BIGINT GENERATED ALWAYS AS IDENTITY,
    sat_code CHAR(3) NOT NULL, 
    name VARCHAR(100) NOT NULL,
    
    CONSTRAINT pk_class PRIMARY KEY (id_classification),
    CONSTRAINT uq_sat_code UNIQUE (sat_code),
    CONSTRAINT ck_valid_sat_code CHECK (sat_code ~ '^[1-9][0-9]{2}$'),
    CONSTRAINT ck_valid_tx_class_name CHECK (TRIM(name) <> '')
);

INSERT INTO tax_classifications (sat_code, name) VALUES
    ('605', 'Sueldos y Salarios e Ingresos Asimilados a Salarios'),
    ('606', 'Arrendamiento'),
    ('607', 'Régimen de Enajenación o Adquisición de Bienes'),
    ('608', 'Demás ingresos'),
    ('610', 'Residentes en el Extranjero sin Establecimiento Permanente en México'),
    ('611', 'Ingresos por Dividendos (socios y accionistas)'),
    ('612', 'Personas Físicas con Actividades Empresariales y Profesionales'),
    ('614', 'Ingresos por Intereses'),
    ('615', 'Régimen de los ingresos por obtención de premios'),
    ('616', 'Sin obligaciones fiscales'),
    ('621', 'Incorporación Fiscal'),
    ('625', 'Régimen de las Actividades Empresariales con ingresos a través de Plataformas Tecnológicas'),
    ('626', 'Régimen Simplificado de Confianza')
ON CONFLICT (sat_code) DO NOTHING;

-- INCOME
CREATE TABLE IF NOT EXISTS income (
    id_income BIGINT GENERATED ALWAYS AS IDENTITY,
    id_user BIGINT NOT NULL,
    id_tag BIGINT NULL,
    id_classification BIGINT NULL,
    description TEXT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    income_date DATE NOT NULL,
    
    CONSTRAINT pk_income PRIMARY KEY (id_income),
    CONSTRAINT fk_income_user FOREIGN KEY (id_user)
        REFERENCES sentin_user (id_user) ON DELETE CASCADE,
    CONSTRAINT fk_income_tag FOREIGN KEY (id_tag)
        REFERENCES tag (id_tag) ON DELETE SET NULL,
    CONSTRAINT fk_expense_tax_classification FOREIGN KEY (id_classification)
        REFERENCES tax_classifications (id_classification) ON DELETE SET NULL,
        
    CONSTRAINT ck_income_description CHECK (TRIM(description) <> ''),
    CONSTRAINT ck_income_amount CHECK (amount > 0),
    CONSTRAINT ck_income_date CHECK (income_date <= CURRENT_DATE)
);

-- CREDIT CARD
CREATE TABLE IF NOT EXISTS credit_card (
    id_card BIGINT GENERATED ALWAYS AS IDENTITY,
    id_user BIGINT NOT NULL,
    card_alias VARCHAR(60) NOT NULL,
    bank VARCHAR(50) NOT NULL,
    last_four_digits CHAR(4) NULL,
    interest_rate NUMERIC(5, 2) NOT NULL,
    cut_off_day SMALLINT NOT NULL,
    payment_due_day SMALLINT NOT NULL,
    credit_limit NUMERIC(10, 2) NOT NULL,
    user_credit_limit NUMERIC(10, 2) NULL,

    CONSTRAINT pk_credit_card PRIMARY KEY (id_card),
    CONSTRAINT fk_credit_card_user FOREIGN KEY (id_user)
        REFERENCES sentin_user (id_user) ON DELETE CASCADE,
    CONSTRAINT uq_user_card_alias UNIQUE (id_user, card_alias),
    
    CONSTRAINT ck_card_alias_not_empty CHECK (TRIM(card_alias) <> ''),
    CONSTRAINT ck_bank_not_empty CHECK (TRIM(bank) <> ''),
    CONSTRAINT ck_credit_limit CHECK (credit_limit > 0),
    CONSTRAINT ck_interest_rate CHECK (interest_rate >= 0), 
    CONSTRAINT ck_user_credit_limit CHECK (user_credit_limit IS NULL OR (user_credit_limit > 0 AND user_credit_limit <= credit_limit)), 
    CONSTRAINT ck_cut_off_day CHECK (cut_off_day BETWEEN 1 AND 31),
    CONSTRAINT ck_payment_due_day CHECK (payment_due_day BETWEEN 1 AND 31),
    CONSTRAINT ck_last_four_digits CHECK (last_four_digits IS NULL OR last_four_digits ~ '^[0-9]{4}$')
);

-- EXPENSE
CREATE TABLE IF NOT EXISTS expense (
    id_expense BIGINT GENERATED ALWAYS AS IDENTITY,
    id_user BIGINT NOT NULL,
    id_tag BIGINT NULL,
    id_classification BIGINT NULL, 
    description TEXT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    expense_date DATE NOT NULL,
    is_debt BOOLEAN NOT NULL DEFAULT FALSE,
    
    CONSTRAINT pk_expense PRIMARY KEY (id_expense),
    CONSTRAINT fk_expense_user FOREIGN KEY (id_user)
        REFERENCES sentin_user (id_user) ON DELETE CASCADE,
    CONSTRAINT fk_expense_tag FOREIGN KEY (id_tag)
        REFERENCES tag (id_tag) ON DELETE SET NULL,
    CONSTRAINT fk_expense_tax_classification FOREIGN KEY (id_classification)
        REFERENCES tax_classifications (id_classification) ON DELETE SET NULL,
        
    CONSTRAINT ck_expense_description CHECK (TRIM(description) <> ''),
    CONSTRAINT ck_expense_amount CHECK (amount > 0),
    CONSTRAINT ck_expense_date CHECK (expense_date <= CURRENT_DATE)
);

-- DEBT
CREATE TABLE IF NOT EXISTS debt (
    id_expense BIGINT NOT NULL,
    id_card BIGINT NULL,
    limit_date DATE NULL,
    -- 0 = Own money, 1 = credit card
    debt_payment_type SMALLINT NOT NULL, 
    interest_free SMALLINT NULL,
    payed BOOLEAN NOT NULL DEFAULT FALSE,
    
    CONSTRAINT pk_debt PRIMARY KEY (id_expense),
    CONSTRAINT fk_debt_expense FOREIGN KEY (id_expense) 
        REFERENCES expense (id_expense) ON DELETE CASCADE,
    CONSTRAINT fk_debt_card FOREIGN KEY (id_card)
        REFERENCES credit_card (id_card) ON DELETE SET NULL,
    
    CONSTRAINT ck_debt_type CHECK (debt_payment_type IN (0, 1)),
    
    CONSTRAINT ck_valid_credit_state CHECK (
        (debt_payment_type = 0 AND id_card IS NULL) OR 
        (debt_payment_type = 1 AND id_card IS NOT NULL)
    ),
    
    CONSTRAINT ck_limit_date CHECK (
        debt_payment_type = 0 OR limit_date IS NOT NULL
    ),
    
    CONSTRAINT ck_valid_interest_free CHECK (
        (debt_payment_type = 0 AND interest_free IS NULL) OR 
        (debt_payment_type = 1 AND interest_free IS NOT NULL AND interest_free >= 1)
    )
);