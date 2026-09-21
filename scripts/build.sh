VERSION=$(
  cat $DEV_APP/app/build.gradle.kts | grep "versionCode" | awk '{print $NF}'
)

mkdir -p dist

cd $DEV_APP && ./gradlew assembleDebug && cd ..
cp $DEV_APP/app/build/outputs/apk/debug/app-debug.apk dist/LittleFlight.apk
cp dist/LittleFlight.apk dist/v$VERSION.debug.apk

