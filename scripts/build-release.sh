set -a
[ -f .env ] && source .env
set +a

VERSION=$(
  cat $DEV_APP/app/build.gradle.kts | grep "versionCode" | awk '{print $NF}'
)

export KEYSTORE_FILE
export KEYSTORE_PASSWORD
export KEY_ALIAS
export KEY_PASSWORD

echo "$KEYSTORE_BASE64" | base64 --decode > "$DEV_APP/app/$KEYSTORE_FILE"
mkdir -p dist
chmod +x $DEV_APP/gradlew

cd $DEV_APP && ./gradlew assembleRelease && cd ..
cp $DEV_APP/app/build/outputs/apk/release/app-release.apk dist/LittleFlight.apk
cp dist/LittleFlight.apk dist/v$VERSION.apk

cd $DEV_APP && ./gradlew bundleRelease && cd ..
cp $DEV_APP/app/build/outputs/bundle/release/app-release.aab dist/LittleFlight.aab
cp dist/LittleFlight.aab dist/v$VERSION.aab

rm -f "$DEV_APP/app/$KEYSTORE_FILE"

