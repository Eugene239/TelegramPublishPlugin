TelegramPublishPlugin
==============================
Plugin to upload output artifacts to Telegram chats via bot

Restriction
-----

⚠️ Max artifact size [50 MB][1]

Download
-----

- Add plugin to classpath

```kotlin
 classpath 'io.epavlov.telegram.publish:plugin:1.0.0'
```

- Add Plugin to application

```kotlin
apply(plugin = "io.epavlov.telegram.publish.plugin")
```

- Configure extension:
  Put your `TELEGRAM_BOT_TOKEN` and `TELEGRAM_CHAT_ID` to your configuration

```kotlin
// build.gradle.kts

// add imports
import io.epavlov.telegram.publish.gradle.GradlePublishTask
import io.epavlov.telegram.publish.gradle.TelegramPublishExtension

configure<TelegramPublishExtension> {
    tasks.addAll(
        GradlePublishTask(
            taskName = "assembleDebug",
            outputPath = file("build/outputs/apk/debug"),
            prefix = "debug-"
        ),
        GradlePublishTask(
            taskName = "assembleRelease",
            outputPath = file("build/outputs/apk/release"),
            prefix = "release-",
            fileRegex = ".*\\.aab"
        ),
    )
    botToken.set("$TELEGRAM_BOT_TOKEN")
    chatId.set("$TELEGRAM_CHAT_ID")
    releaseNotesFile.set(file("ReleaseNotes.txt"))
    version.set(appVersionName)
}
```

or

```groovy
// build.gradle

// add import
import io.epavlov.telegram.publish.gradle.GradlePublishTask

telegramPublishConfig {
    taskList.addAll(
            new GradlePublishTask(
                    "assembleDebug",
                    file("build/outputs/apk/debug"),
                    "debug-",
                    ".*\\.apk"
            ),
            new GradlePublishTask(
                    "assembleRelease",
                    file("build/outputs/apk/release"),
                    "release-",
                    ".*\\.apk"
            )
    )
    botToken = "$TELEGRAM_BOT_TOKEN"
    chatId = "$TELEGRAM_CHAT_ID"
    releaseNotes = "Release note text"
    version = "$versionName"
}
```

Release notes
-----

- Set `releaseNotes` string parameter
- Set file to `releaseNotesFile`

First bot message would be version and release notes of the application


Run task
-----

```
$ gradle :app:telegramPublish 
```

--- 

Customization
-----

### Add or remove gradle task

Remove or Add new `GradlePublishTask` class

### Change output artifacts to `.aab`

Change fileRegex field value to `.*\\.aab` or what you need, by default plugin search for `apk` files

### Add flavor build

Add new `GradlePublishTask` with gradle `flavor` task

```kotlin
GradlePublishTask(
    taskName = "assemble${flavor}Debug",
    outputPath = file("$flavor_Output_path"),
    prefix = "${flavor}-",
    fileRegex = ".*\\.aab"
)
```

[1]: https://core.telegram.org/bots/api#senddocument