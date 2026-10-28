-- Initial categories
INSERT INTO categories (name, description)
VALUES
    ('Food', 'Food and groceries'),
    ('Transport', 'Transportation expenses'),
    ('Entertainment', 'Leisure and entertainment')
    ON CONFLICT (name) DO NOTHING;


-- Initial expenses
INSERT INTO expenses (title, amount, category_id, date)
SELECT 'Groceries', 45.50, c.id, DATE '2026-10-20'
FROM categories c
WHERE c.name = 'Food'
  AND NOT EXISTS (
    SELECT 1 FROM expenses e
    WHERE e.title = 'Groceries'
      AND e.category_id = c.id
      AND e.date = DATE '2026-10-20'
);

INSERT INTO expenses (title, amount, category_id, date)
SELECT 'Bus ticket', 12.00, c.id, DATE '2026-10-22'
FROM categories c
WHERE c.name = 'Transport'
  AND NOT EXISTS (
    SELECT 1 FROM expenses e
    WHERE e.title = 'Bus ticket'
      AND e.category_id = c.id
      AND e.date = DATE '2026-10-22'
);

INSERT INTO expenses (title, amount, category_id, date)
SELECT 'Cinema', 35.00, c.id, DATE '2026-10-25'
FROM categories c
WHERE c.name = 'Entertainment'
  AND NOT EXISTS (
    SELECT 1 FROM expenses e
    WHERE e.title = 'Cinema'
      AND e.category_id = c.id
      AND e.date = DATE '2026-10-25'
);