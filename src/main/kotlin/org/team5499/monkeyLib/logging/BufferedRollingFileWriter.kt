package org.team5499.monkeyLib.logging

import java.util.Map

/**
 * Writer for outputting log entries to rolling log files. Rollover strategies can be defined via {@link Policy
 * policies} and the output can be buffered for improving performance. The path to the log file can contain one or more
 * patterns that will be resolved at runtime.
 */
class BufferedRollingFileWriter(val properties: Map<String, String>) : AbstractFormatPatternWriter(properties) {

    private val path: DynamicPath
    private val policies: Array<Policy>
    private val backups: Int
    private val buffered: Boolean
    private val writingThread: Boolean
    private val charset: Charset

    private var writer: ByteArrayWriter

    /**
     * @param properties
     *            Configuration for writer
     *
     * @throws FileNotFoundException
     *             Log file does not exist or cannot be opened for any other reason
     * @throws IllegalArgumentException
     *             A property has an invalid value or is missing in configuration
     */
    init {
    	path = DynamicPath(getFileName(properties))
    	policies = createPolicies(properties.get("policies"))
    	if(properties.containsKey("backups"))
    		backups = Integer.parseInt(properties.get("backups"))
    	else
    		backups = -1

    	var files = path.getAllFiles()

    	var fileName: String
    	var append: Boolean

    	if (files.size() > 0 && path.isValid(files.get(0))) {
    		fileName = files.get(0).getPath()
    		if (canBeContinued(fileName, policies)) {
    			append = true
    			deleteBackups(files.subList(1, files.size()), backups)
    		} else {
    			fileName = path.resolve()
    			append = false
    			deleteBackups(files, backups)
    		}
    	} else {
    		fileName = path.resolve()
    		append = false
    	}

    	charset = getCharset(properties)
    	buffered = properties.get("buffered").toBoolean()
    	writingThread = properties.get("writingthread").toBoolean()
    	writer = createByteArrayWriter(fileName, append, buffered, false, false)
    }

    override fun write(logEntry: LogEntry) {
    	var theData = render(logEntry).getBytes(charset)
    	if (writingThread) {
    		internalWrite(theData)
    	} else {
    		synchronized (writer) {
    			internalWrite(theData)
    		}
    	}
    }

    override fun flush() {
    	if (writingThread) {
    		internalFlush();
    	} else {
    		synchronized (writer) {
    			internalFlush();
    		}
    	}
    }

    @Override
    public void close() throws IOException {
    	if (writingThread) {
    		internalClose();
    	} else {
    		synchronized (writer) {
    			internalClose();
    		}
    	}
    }

    /**
     * Outputs a passed byte array unsynchronized.
     *
     * @param data
     *            Byte array to output
     * @throws IOException
     *             Writing failed
     */
    private void internalWrite(final byte[] data) throws IOException {
    	if (!canBeContinued(data, policies)) {
    		writer.close();

    		String fileName = path.resolve();
    		deleteBackups(path.getAllFiles(), backups);
    		writer = createByteArrayWriter(fileName, false, buffered, false, false);

    		for (Policy policy : policies) {
    			policy.reset();
    		}
    	}

    	writer.write(data, data.length);
    }

    /**
     * Outputs buffered log entries immediately unsynchronized.
     *
     * @throws IOException
     *             Flushing failed
     */
    private void internalFlush() throws IOException {
    	writer.flush();
    }

    /**
     * Closes the writer unsynchronized.
     *
     * @throws IOException
     *             Closing failed
     */
    private void internalClose() throws IOException {
    	writer.close();
    }

    /**
     * Creates policies from a nullable string.
     *
     * @param property
     *            Nullable string with policies to create
     * @return Created policies
     */
    private static List<Policy> createPolicies(final String property) {
    	if (property == null || property.isEmpty()) {
    		return Collections.<Policy>singletonList(new StartupPolicy(null));
    	} else {
    		ServiceLoader<Policy> loader = new ServiceLoader<Policy>(Policy.class, String.class);
    		List<Policy> policies = new ArrayList<Policy>();
    		for (String entry : property.split(",")) {
    			int separator = entry.indexOf(':');
    			if (separator == -1) {
    				policies.add(loader.create(entry, (String) null));
    			} else {
    				String name = entry.substring(0, separator).trim();
    				String argument = entry.substring(separator + 1).trim();
    				policies.add(loader.create(name, argument));
    			}
    		}
    		return policies;
    	}
    }

    /**
     * Checks if an already existing log file can be continued.
     *
     * @param fileName
     *            Log file
     * @param policies
     *            Policies that should be applied
     * @return {@code true} if the passed log file can be continued, {@code false} if a new log file should be started
     */
    private static boolean canBeContinued(final String fileName, final List<Policy> policies) {
    	boolean result = true;
    	for (Policy policy : policies) {
    		result &= policy.continueExistingFile(fileName);
    	}
    	return result;
    }

    /**
     * Checks if a new log entry can be still written to the current log file.
     *
     * @param data
     *            Log entry
     * @param policies
     *            Policies that should be applied
     * @return {@code true} if the current log file can be continued, {@code false} if a new log file should be started
     */
    private static boolean canBeContinued(final byte[] data, final List<Policy> policies) {
    	boolean result = true;
    	for (Policy policy : policies) {
    		result &= policy.continueCurrentFile(data);
    	}
    	return result;
    }

    /**
     * Deletes old log files.
     *
     * @param files
     *            All existing log files
     * @param count
     *            Number of log files to keep
     */
    private static void deleteBackups(final List<File> files, final int count) {
    	if (count >= 0) {
    		for (int i = files.size() - Math.max(0, files.size() - count); i < files.size(); ++i) {
    			if (!files.get(i).delete()) {
    				InternalLogger.log(Level.WARN, "Failed to delete log file '" + files.get(i).getAbsolutePath() + "'");
    			}
    		}
    	}
    }
}
