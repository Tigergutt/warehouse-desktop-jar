/**
 * The type Version.
 */
public final class Version {

    private static final String VERSION = "${project.version}";
    private static final String GROUPID = "${project.groupId}";
    private static final String ARTIFACTID = "${project.artifactId}";

    /**
     * Gets version.
     *
     * @return the version
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * Gets group id.
     *
     * @return the group id
     */
    public static String getGroupId() {
        return GROUPID;
    }

    /**
     * Gets artifact id.
     *
     * @return the artifact id
     */
    public static String getArtifactId() {
        return ARTIFACTID;
    }
}
