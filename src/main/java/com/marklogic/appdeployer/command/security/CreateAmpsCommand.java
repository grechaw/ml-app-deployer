package com.marklogic.appdeployer.command.security;

import java.io.File;

import com.marklogic.appdeployer.command.AbstractResourceCommand;
import com.marklogic.appdeployer.command.CommandContext;
import com.marklogic.rest.mgmt.ResourceManager;
import com.marklogic.rest.mgmt.security.AmpManager;

public class CreateAmpsCommand extends AbstractResourceCommand {

    @Override
    public Integer getExecuteSortOrder() {
        return 30;
    }

    @Override
    protected File getResourcesDir(CommandContext context) {
        return new File(context.getAppConfig().getConfigDir().getSecurityDir(), "amps");
    }

    @Override
    protected ResourceManager getResourceManager(CommandContext context) {
        return new AmpManager(context.getManageClient());
    }

    @Override
    protected String getIdFieldName() {
        return "local-name";
    }

}
