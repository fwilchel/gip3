--
-- Demiurgesoft
-- Copyrigth .MMXV.
--
-- Drop database script
-- @author: Javier Latorre, <javier.latorre@demiurgesoft.co>
-- @version: 1.0
--

-------------------------------------------------------------
-- To disconnect the users:
-------------------------------------------------------------
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'sipt'
AND pid <> pg_backend_pid();

DROP DATABASE sipt;
DROP ROLE sipt;