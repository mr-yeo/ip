package storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests persistence behavior relevant to duplicate tasks.
 */
public class StorageTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    public void load_preservesExistingDuplicateEntries() throws Exception {
        Path taskFile = temporaryDirectory.resolve("tasks.txt");
        Files.writeString(taskFile, "T|0|buy milk\nT|1|buy milk");

        assertEquals(2, new Storage(taskFile.toString()).load().size());
    }
}
