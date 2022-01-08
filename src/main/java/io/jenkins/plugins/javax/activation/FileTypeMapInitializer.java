package io.jenkins.plugins.javax.activation;

import hudson.init.InitMilestone;
import hudson.init.Initializer;
import javax.activation.FileTypeMap;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

@Restricted(NoExternalUse.class)
public class FileTypeMapInitializer {

    @Initializer(after = InitMilestone.PLUGINS_PREPARED, before = InitMilestone.PLUGINS_STARTED)
    public static synchronized void init() {
        Thread t = Thread.currentThread();
        ClassLoader orig = t.getContextClassLoader();
        t.setContextClassLoader(FileTypeMapInitializer.class.getClassLoader());
        try {
            // Getting the default file type map fetches a per-thread-context-class-loader default.
            // Setting the default file type map removes the per-thread-context-class-loader file type map.
            FileTypeMap.setDefaultFileTypeMap(new DelegatingFileTypeMap(FileTypeMap.getDefaultFileTypeMap()));
        } finally {
            t.setContextClassLoader(orig);
        }
    }
}
