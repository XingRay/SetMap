# How to

To get a Git project into your build:

**Step 1.** Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

[![](https://www.jitpack.io/v/XingRay/SetMap.svg)](https://www.jitpack.io/#XingRay/SetMap)



```groovy
	dependencies {
	        implementation 'com.github.XingRay:SetMap:0.0.2'
	}
```