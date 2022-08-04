# Translations

### Features

**Translations** offers a simple way to make your translations available to edit for your plugins users.

### Usage:

Imagine having a `test_en.properties` and `test_de.properties` file in your project under `src/main/resources/i18n/`.
Then the following line is all you need:

```kotlin
Translation(plugin, Key.key("test"), Path.of("i18n"), listOf(Locale.ENGLISH, Locale.GERMAN))
```

In your plugins datafolder you will be able to find the `test_en.properties` and `test_de.properties` files
under `/translations/i18n/`.
Those translation files will be registered to the GlobalTranslator, so your users can easily edit translations.
Also, a `test-translations.yml` file will be created in your plugins datafolder under `/translations/`.
Your users can configure which translations they want to enable in this file.
They are also able to create new translations by creating new files in the `/translations/i18n/` folder and adding the
corresponding language code to the config.
