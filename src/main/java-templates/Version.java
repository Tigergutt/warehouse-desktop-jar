package se.melsom.warhorse;

public final class Version {
    private Version() {}

    public static String getVersion() {
        return "${project.version}";
    }

    public static String getGroupId() {
        return "${project.groupId}";
    }

    public static String getArtifactId() {
        return "${project.artifactId}";
    }
}
