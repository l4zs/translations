package de.l4zs.translations

import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.URLClassLoader
import java.util.Locale
import java.util.Properties
import java.util.ResourceBundle

internal fun JavaPlugin.saveResource(resourcePath: String, savePath: String, replace: Boolean = false) {
    getResource(resourcePath)
        ?.readBytes()?.let {
            val file = File(dataFolder, savePath)
            if (!file.exists() || replace) {
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
                file.writeBytes(it)
            }
        }
}

internal fun loadProperties(path: String): Properties {
    val inputStream = File(path).inputStream()
    val properties = Properties()
    properties.load(inputStream)
    inputStream.close()
    return properties
}

internal fun saveProperties(path: String, properties: Properties) {
    val outputStream = File(path).outputStream()
    properties.store(outputStream, null)
    outputStream.close()
}

internal fun resourceBundleFromClassLoader(path: String, bundleName: String, locale: Locale): ResourceBundle {
    val file = File(path)
    val urls = arrayOf(file.toURI().toURL())
    val loader: ClassLoader = URLClassLoader(urls)
    return ResourceBundle.getBundle(bundleName, locale, loader)
}
