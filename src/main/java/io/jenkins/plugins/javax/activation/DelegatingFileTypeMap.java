package io.jenkins.plugins.javax.activation;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.io.File;
import java.util.Objects;
import java.util.function.Supplier;
import javax.activation.FileTypeMap;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

@Restricted(NoExternalUse.class)
public class DelegatingFileTypeMap extends FileTypeMap {
    private final FileTypeMap delegate;

    public DelegatingFileTypeMap(@NonNull FileTypeMap delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    private static final <T> T runWithContextClassLoader(Supplier<T> supplier) {
        Thread t = Thread.currentThread();
        ClassLoader orig = t.getContextClassLoader();
        t.setContextClassLoader(DelegatingFileTypeMap.class.getClassLoader());
        try {
            return supplier.get();
        } finally {
            t.setContextClassLoader(orig);
        }
    }

    @Override
    public String getContentType(File file) {
        return runWithContextClassLoader(() -> delegate.getContentType(file));
    }

    @Override
    public String getContentType(String filename) {
        return runWithContextClassLoader(() -> delegate.getContentType(filename));
    }
}
