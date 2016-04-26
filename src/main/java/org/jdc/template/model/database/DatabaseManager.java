package org.jdc.template.model.database;

import android.util.Log;

import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.config.DatabaseConfig;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DatabaseManager extends DatabaseBaseManager {

    public static final int MAIN_VERSION = 2;
    public static final int MAIN_VIEWS_VERSION = 1;

    public static final int OTHER_VERSION = 1;
    public static final int OTHER_VIEWS_VERSION = 1;

    @Inject
    public DatabaseManager(DatabaseConfig databaseConfig) {
        super(databaseConfig);
    }

    public void onUpgrade(AndroidDatabase androidDatabase, int oldVersion, int newVersion) {
        String databaseName = androidDatabase.getName();
        getLogger().i(TAG, "Upgrading database [" + databaseName + "] from version " + oldVersion + " to " + newVersion);
        if (oldVersion < newVersion) {
            // todo implement database migration??
            deleteDatabase(androidDatabase);
            onCleanDatabase(androidDatabase);
        }
    }

    public void initDatabaseConnection() {
        getLogger().i(TAG, "Initializing Database connection: ");
        try {
            getWritableDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open database... attempting to recreate database", e);
            cleanAllDatabases();
            getWritableDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME);
        }
    }
}