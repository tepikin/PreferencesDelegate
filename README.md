# PreferencesDelegate

Lib for easy work with SharedPreferences.

Simple usage
```java
class MyPreferences(context: Context) : PreferencesInterface {
    override val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    /** Preference with name "stringParam", type String and default value = null */
    var stringParam: String? by PreferenceDelegate()

    /** Preference with name "intParam", type Int and default value = -1 */
    var intParam: Int? by PreferenceDelegate(-1)
}
  ```
 
## Add dependencies

**Step 1.** Add the JitPack repository to your build file.
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
**Step 2.** Add the dependency
```gradle
dependencies {
    compile 'com.github.tepikin:PreferencesDelegate:master-SNAPSHOT'
}
```
