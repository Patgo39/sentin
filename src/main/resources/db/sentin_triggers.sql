
CREATE OR REPLACE FUNCTION check_expense_boolean()
RETURNS TRIGGER
AS $$
DECLARE
    v_is_debt BOOLEAN;
BEGIN
    IF (TG_OP = 'INSERT') THEN 
        SELECT is_debt INTO v_is_debt 
        FROM expense 
        WHERE id_expense = NEW.id_expense;

        IF (v_is_debt IS NULL) THEN
            RAISE EXCEPTION 'The debt cannot be saved: the expense with id_expense = % does not exist.', NEW.id_expense;
        END IF;


        IF (v_is_debt IS FALSE) THEN 
            RAISE EXCEPTION 'The debt cannot be saved: the expense with id_expense = % has is_debt = FALSE.', NEW.id_expense;
        END IF;
    END IF;

    RETURN NEW; 
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_expense_boolean_trigger
    BEFORE INSERT 
    ON debt 
    FOR EACH ROW 
    EXECUTE FUNCTION check_expense_boolean();


CREATE OR REPLACE FUNCTION check_expense_update_is_debt()
RETURNS TRIGGER
AS $$
DECLARE
    v_has_debt BOOLEAN;
BEGIN
    IF (OLD.is_debt IS TRUE AND NEW.is_debt IS FALSE) THEN
        SELECT EXISTS (
            SELECT 1 FROM debt WHERE id_expense = NEW.id_expense
        ) INTO v_has_debt;

        IF (v_has_debt IS TRUE) THEN
            RAISE EXCEPTION 'Cannot set is_debt = FALSE: expense with id_expense = % already has a record in the debt table.', NEW.id_expense;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_before_update_expense_is_debt
    BEFORE UPDATE OF is_debt
    ON expense
    FOR EACH ROW
    EXECUTE FUNCTION check_expense_update_is_debt();


CREATE OR REPLACE FUNCTION check_tax_classification_code()
RETURNS TRIGGER
AS $$
DECLARE
    tax_code CHAR(3);
BEGIN
    IF (NEW.id_classification IS NULL) THEN
        RETURN NEW;
    END IF;

    SELECT sat_code INTO tax_code
    FROM tax_classification
    WHERE id_classification = NEW.id_classification;

    IF (tax_code IN ('615', '616')) THEN
        RAISE EXCEPTION 'Tax Classification code % is not valid for expenses', tax_code;
    END IF;

    RETURN NEW; 
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_tax_classification_code
    BEFORE INSERT OR UPDATE 
    ON expense
    FOR EACH ROW 
    EXECUTE FUNCTION check_tax_classification_code();

























