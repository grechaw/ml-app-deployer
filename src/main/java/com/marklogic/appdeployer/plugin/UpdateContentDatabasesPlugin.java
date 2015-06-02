package com.marklogic.appdeployer.plugin;

import java.io.File;

import com.marklogic.appdeployer.AppConfig;
import com.marklogic.appdeployer.AppPluginContext;
import com.marklogic.rest.mgmt.databases.DatabaseManager;

public class UpdateContentDatabasesPlugin extends AbstractPlugin {

    @Override
    public Integer getSortOrderOnCreate() {
        return 200;
    }

    /**
     * Check for content-database.json; if it exists, then PUT it against the existing content database.
     */
    @Override
    public void onCreate(AppPluginContext context) {
        File f = context.getConfigDir().getContentDatabaseFile();
        if (f.exists()) {
            DatabaseManager dbMgr = new DatabaseManager(context.getManageClient());

            String payload = copyFileToString(f);
            AppConfig appConfig = context.getAppConfig();

            String json = replaceConfigTokens(payload, appConfig, false);
            dbMgr.updateDatabase(appConfig.getContentDatabaseName(), json);

            if (appConfig.isTestPortSet()) {
                json = replaceConfigTokens(payload, appConfig, true);
                dbMgr.updateDatabase(appConfig.getTestContentDatabaseName(), json);
            }
        } else {
            logger.info(format("No content database file found at %s, so not updating the content database",
                    f.getAbsolutePath()));
        }
    }

    @Override
    public void onDelete(AppPluginContext context) {
    }

}