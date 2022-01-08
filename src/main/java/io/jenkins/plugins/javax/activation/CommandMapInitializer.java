package io.jenkins.plugins.javax.activation;

import hudson.init.InitMilestone;
import hudson.init.Initializer;
import javax.activation.CommandMap;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

@Restricted(NoExternalUse.class)
public class CommandMapInitializer {

    @Initializer(after = InitMilestone.PLUGINS_PREPARED, before = InitMilestone.PLUGINS_STARTED)
    public static synchronized void init() {
        Thread t = Thread.currentThread();
        ClassLoader orig = t.getContextClassLoader();
        t.setContextClassLoader(CommandMapInitializer.class.getClassLoader());
        try {
            // Getting the default command map fetches a per-thread-context-class-loader default.
            // Setting the default command map removes the per-thread-context-class-loader command map.
            CommandMap.setDefaultCommandMap(new DelegatingCommandMap(CommandMap.getDefaultCommandMap()));
        } finally {
            t.setContextClassLoader(orig);
        }
    }
}
