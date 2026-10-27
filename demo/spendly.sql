ALTER TABLE expenses
ADD COLUMN IF NOT EXISTS category_id BIGINT;

INSERT INTO categories (name, description)
SELECT MIN(BTRIM(e.category)), NULL
FROM expenses e
WHERE e.category IS NOT NULL
  AND BTRIM(e.category) <> ''
  AND NOT EXISTS (
      SELECT 1
      FROM categories c
      WHERE LOWER(BTRIM(c.name)) = LOWER(BTRIM(e.category))
  )
GROUP BY LOWER(BTRIM(e.category));

UPDATE expenses e
SET category_id = c.id
FROM categories c
WHERE e.category_id IS NULL
  AND e.category IS NOT NULL
  AND LOWER(BTRIM(c.name)) = LOWER(BTRIM(e.category));
  