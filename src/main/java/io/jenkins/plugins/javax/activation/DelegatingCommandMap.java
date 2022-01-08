package io.jenkins.plugins.javax.activation;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;
import java.util.function.Supplier;
import javax.activation.CommandInfo;
import javax.activation.CommandMap;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

@Restricted(NoExternalUse.class)
public class DelegatingCommandMap extends CommandMap {
    private final CommandMap delegate;

    public DelegatingCommandMap(@NonNull CommandMap delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    private static final <T> T runWithContextClassLoader(Supplier<T> supplier) {
        Thread t = Thread.currentThread();
        ClassLoader orig = t.getContextClassLoader();
        t.setContextClassLoader(DelegatingCommandMap.class.getClassLoader());
        try {
            return supplier.get();
        } finally {
            t.setContextClassLoader(orig);
        }
    }

    @Override
    public CommandInfo[] getPreferredCommands(String mimeType) {
        return runWithContextClassLoader(() -> delegate.getPreferredCommands(mimeType));
    }

    @Override
    public CommandInfo[] getPreferredCommands(String mimeType, DataSource ds) {
        return runWithContextClassLoader(() -> delegate.getPreferredCommands(mimeType, ds));
    }

    @Override
    public CommandInfo[] getAllCommands(String mimeType) {
        return runWithContextClassLoader(() -> delegate.getAllCommands(mimeType));
    }

    @Override
    public CommandInfo[] getAllCommands(String mimeType, DataSource ds) {
        return runWithContextClassLoader(() -> delegate.getAllCommands(mimeType, ds));
    }

    @Override
    public CommandInfo getCommand(String mimeType, String cmdName) {
        return runWithContextClassLoader(() -> delegate.getCommand(mimeType, cmdName));
    }

    @Override
    public CommandInfo getCommand(String mimeType, String cmdName, DataSource ds) {
        return runWithContextClassLoader(() -> delegate.getCommand(mimeType, cmdName, ds));
    }

    @Override
    public DataContentHandler createDataContentHandler(String mimeType) {
        return runWithContextClassLoader(() -> delegate.createDataContentHandler(mimeType));
    }

    @Override
    public DataContentHandler createDataContentHandler(String mimeType, DataSource ds) {
        return runWithContextClassLoader(() -> delegate.createDataContentHandler(mimeType, ds));
    }

    @Override
    public String[] getMimeTypes() {
        return runWithContextClassLoader(() -> delegate.getMimeTypes());
    }
}
