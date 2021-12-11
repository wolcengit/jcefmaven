package me.friwi.jcefmaven.progress;

public interface IProgressHandler {
    /**
     * Handles a progress update from the installation and loading process.
     * @param state The state the installer is now in.
     * @param percent -1 if no percentage available (unpredictable task) or [0f, 100f] if predictable
     */
    void handleProgress(EnumProgress state, float percent);
}
