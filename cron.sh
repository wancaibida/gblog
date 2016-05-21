# 30 1 * * * /home/test.sh &>>/tmp/cron_debug.log
sudo -u postgres /usr/bin/pg_dump blog > /backup/blog_$(date -d "today" +"%Y_%m_%d").dump