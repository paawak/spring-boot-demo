package com.swayam.demo.springbootdemo.liquibase;

import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.core.MissingDataChangeGenerator;
import liquibase.structure.DatabaseObject;

public class BatchingMissingDataChangeGenerator extends MissingDataChangeGenerator {

    @Override
    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl outputControl,
	    Database referenceDatabase, Database comparisionDatabase, ChangeGeneratorChain chain) {
	System.out.println("*********** BatchingMissingDataChangeGenerator.fixMissing()");
	return super.fixMissing(missingObject, outputControl, referenceDatabase, comparisionDatabase, chain);
    }

}
