package me.friwi.jcefmaven.check;

import me.friwi.jcefmaven.platform.EnumPlatform;
import me.friwi.jcefmaven.platform.UnsupportedPlatformException;
import me.friwi.jcefmaven.version.CefBuildInfo;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CefInstallationChecker {
    private static final Logger LOGGER = Logger.getLogger(CefInstallationChecker.class.getName());

    public static boolean checkInstallation(File installDir) throws UnsupportedPlatformException {
        Objects.requireNonNull(installDir, "installDir cannot be null");
        File buildInfo = new File(installDir, "build_meta.json");
        if(!(new File(installDir, "install.lock").exists()))return false;
        if(!(buildInfo.exists()))return false;
        CefBuildInfo installed;
        try {
            installed = CefBuildInfo.fromFile(buildInfo);
        }catch (IOException e){
            LOGGER.log(Level.WARNING, "Error while parsing existing installation. Reinstalling.", e);
            return false;
        }
        CefBuildInfo required;
        try {
            required = CefBuildInfo.fromClasspath();
        }catch (IOException e){
            LOGGER.log(Level.WARNING, "Error while parsing existing installation. Reinstalling.", e);
            return false;
        }
        //The install is ok when tag and platform match
        return required.getReleaseTag().equals(installed.getReleaseTag())
                && installed.getPlatform().equals(EnumPlatform.getCurrentPlatform().getIdentifier());
    }
}
