package cn.elytra.nightconfig.internal;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ApiStatus.Internal
@NullMarked
public class Utils {

    /// Split the file name into the real name and the extension.
    ///
    /// @return a String pair (2-element array): the 1st element is the file name and the 2nd element is the extension; both non-null.
    public static String[] getFileNameAndExtension(Path path) {
        String s = path.getFileName().toString();
        int i = s.indexOf('.');
        if (i == -1) return new String[]{s, ""};
        String name = s.substring(0, i);
        String extension = s.substring(i + 1);
        return new String[]{name, extension};
    }

    public static <T> T firstNonNullOf(@Nullable T e1, T e2) {
        return e1 != null ? e1 : e2;
    }

    public static void checkNotNull(@Nullable Object o, @Nullable String message) {
        if (o == null) throw new NullPointerException(String.valueOf(message));
    }

    public static void checkState(boolean o, @Nullable String message) {
        if (!o) throw new IllegalStateException(String.valueOf(message));
    }

    public static boolean isBlank(@Nullable CharSequence s) {
        if (s == null || s.length() == 0) return true;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!Character.isWhitespace(ch)) return false;
        }
        return true;
    }

    @SafeVarargs
    public static <T> List<T> newArrayListOf(T... es) {
        return newArrayListOf(Arrays.asList(es));
    }

    public static <T> List<T> newArrayListOf(Collection<T> es) {
        return new ArrayList<>(es);
    }

}
