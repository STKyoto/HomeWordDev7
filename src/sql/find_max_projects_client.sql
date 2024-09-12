SELECT CLIENT_ID, NAME, COUNT(*) AS project_count
FROM project
JOIN client ON project.CLIENT_ID = client.ID
GROUP BY CLIENT_ID, NAME
HAVING COUNT(*) = (
    SELECT MAX(project_count)
    FROM (
        SELECT CLIENT_ID, COUNT(*) AS project_count
        FROM project
        GROUP BY CLIENT_ID
    ) AS subquery
);
