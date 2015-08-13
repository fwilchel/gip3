# tarea que crea un backup de la base de datos gip
# expresion cron
# 15 12 * * * sh /home/d5a9p6s7/dev/repository/softstudio/gip/gip3/shell/gip-backup.sh
fecha=`date +%Y%m%d"_"%H%M`
echo $fecha start backup   >> /home/d5a9p6s7/cron.log
pg_dump --host localhost --port 5432 --username postgres --role postgres --no-password --format custom --blobs --encoding UTF8 --file /home/d5a9p6s7/dev/repository/softstudio/gip/backup/$fecha.gip.local.backup gip
echo $fecha finish backup   >> /home/d5a9p6s7/cron.log

