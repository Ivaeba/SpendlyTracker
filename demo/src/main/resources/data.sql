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
INSERT INTO expenses (title, amount, category_id, date)
SELECT v.title, v.amount, c.id, v.expense_date
FROM categories c
         CROSS JOIN (
    VALUES
        ('Sample expense 2', 10.00::numeric, DATE '2026-10-20'),
        ('Sample expense 3', 25.50::numeric, DATE '2026-10-21'),
        ('Sample expense 4', 7.25::numeric, DATE '2026-10-23'),
        ('Sample expense 5', 42.00::numeric, DATE '2026-10-26')
) AS v(title, amount, expense_date)
WHERE NOT EXISTS (
    SELECT 1
    FROM expenses e
    WHERE e.category_id = c.id
      AND e.title = v.title
      AND e.date = v.expense_date
);