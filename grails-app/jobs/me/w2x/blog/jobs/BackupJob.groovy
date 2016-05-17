package me.w2x.blog.jobs

class BackupJob {
    static triggers = {
        cron name: 'myTrigger', cronExpression: "0 30 1 * * ?"
    }

    def description = "Database backup job with Cron Trigger"

    void execute() {

    }
}