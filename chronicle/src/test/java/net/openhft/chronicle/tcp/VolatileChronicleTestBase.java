/*
 * Copyright 2014 Higher Frequency Trading
 * <p/>
 * http://www.higherfrequencytrading.com
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.tcp;


import net.openhft.chronicle.*;
import net.openhft.chronicle.tools.ChronicleTools;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class VolatileChronicleTestBase {
    protected static final Logger LOGGER    = LoggerFactory.getLogger("VolarileChronicleTest");
    protected static final String TMP_DIR   = System.getProperty("java.io.tmpdir");
    protected static final String PREFIX    = "ch-volatile-";
    protected static final int    BASE_PORT = 12000;

    @Rule
    public final TestName testName = new TestName();

    // *************************************************************************
    //
    // *************************************************************************

    protected Chronicle volatileChronicleSink(String host, int port) throws IOException {
        return new ChronicleSink(host, port);
    }

    // *************************************************************************
    //
    // *************************************************************************

    protected synchronized String getIndexedTestPath() {
        final String path = TMP_DIR + "/" + PREFIX + testName.getMethodName();
        ChronicleTools.deleteOnExit(path);

        return path;
    }

    protected synchronized String getIndexedTestPath(String suffix) {
        final String path = TMP_DIR + "/" + PREFIX + testName.getMethodName() + suffix;
        ChronicleTools.deleteOnExit(path);

        return path;
    }

    protected Chronicle indexedChronicleSource(String basePath, int port) throws IOException {
        return new ChronicleSource(new IndexedChronicle(basePath), port);
    }

    protected Chronicle indexedChronicleSource(String basePath, int port, ChronicleConfig config) throws IOException {
        return new ChronicleSource(new IndexedChronicle(basePath, config), port);
    }

    // *************************************************************************
    //
    // *************************************************************************

    protected synchronized String getVanillaTestPath() {
        final String path = TMP_DIR + "/" + PREFIX + testName.getMethodName();
        final File f = new File(path);
        if(f.exists()) {
            f.delete();
        }

        return path;
    }

    protected synchronized String getVanillaTestPath(String suffix) {
        final String path = TMP_DIR + "/" + PREFIX + testName.getMethodName() + suffix;
        final File f = new File(path);
        if(f.exists()) {
            f.delete();
        }

        return path;
    }

    protected ChronicleSource vanillaChronicleSource(String basePath, int port) throws IOException {
        return new ChronicleSource(new VanillaChronicle(basePath), port);
    }

    protected ChronicleSource vanillaChronicleSource(String basePath, int port, VanillaChronicleConfig config) throws IOException {
        return new ChronicleSource(new VanillaChronicle(basePath, config), port);
    }
}
