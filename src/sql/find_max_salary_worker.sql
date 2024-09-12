WITH MaxSalary AS (
    SELECT MAX(SALARY) AS max_salary
    FROM worker
)
SELECT NAME, SALARY
FROM worker
WHERE SALARY = (SELECT max_salary FROM MaxSalary);


