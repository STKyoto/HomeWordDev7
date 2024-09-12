SELECT
	ID AS project_id,
	DATE_PART('month', AGE(FINISH_DATE, START_DATE)) AS MONTH_COUNT
FROM project
WHERE
    DATE_PART('month', AGE(FINISH_DATE, START_DATE)) = (
        SELECT
            MAX(DATE_PART('month', AGE(FINISH_DATE, START_DATE)))
        FROM
            project
    );