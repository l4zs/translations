package de.l4zs.translations

import net.kyori.adventure.key.Key
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.util.Locale

class Config(dir: File, name: String, private val key: Key, templateLocales: List<Locale>, private val plugin: JavaPlugin) {

    private val translationsComment = listOf(
        "The enabled translations",
        "The first one will be used as a fallback and therefore should (like really) be complete (all keys set)",
        "",
        "Add as many translations as you want (create a new file for each)",
        "The file must be named like the language code",
        "i.E. ${key.value()}_en.properties for English,",
        "${key.value()}_en_us.properties for American English",
        "${key.value()}_en_uk.properties for British English, etc.",
        "Then add the language code (en, en_us, en_uk, etc.) to this list",
    )
    private val file: File
    private val yml: YamlConfiguration

    var locales: List<Locale>
        get() = yml.getStringList("translations").mapNotNull { Locale.forLanguageTag(it.replace("_", "-")) }
        set(value) {
            yml.set("translations", value.map { it.toLanguageTag().replace("-", "_") })
            yml.setComments("translations", translationsComment)
            save()
        }

    val fallbackLocale: Locale
        get() = locales.firstOrNull() ?: Locale.ENGLISH

    init {
        if (!dir.exists()) {
            dir.mkdirs()
        }
        file = File(dir, "${key.value()}-$name")
        val firstInit: Boolean = !file.exists()
        if (!file.exists()) {
            try {
                plugin.saveResource(name, file.path.substringAfter("${plugin.dataFolder.path}/").replace(key.value() + "/", ""))
            } catch (e: IOException) {
                plugin.componentLogger.error("Could not save default config for ${key.value()}", e)
            }
        }
        yml = YamlConfiguration.loadConfiguration(file)
        if (locales.isEmpty() && firstInit) {
            locales = templateLocales
        }
    }

    private fun save() {
        try {
            yml.save(file)
        } catch (e: IOException) {
            plugin.componentLogger.error("Could not save config for ${key.value()}", e)
        }
    }
}
