SELECT
    p.ID AS project_id,
    SUM(w.SALARY) * DATE_PART('month', AGE(FINISH_DATE, START_DATE)) AS project_cost
FROM
    project_worker pw
JOIN
    worker w ON pw.WORKER_ID = w.ID
JOIN
    project p ON pw.PROJECT_ID = p.ID
GROUP BY
    p.ID
ORDER BY
    project_cost DESC;