# Upgrading the gradle wrappers

Set `GRADLE_VERSION` to the version of gradle.

Set `GRADLE_VERSION_CHECKSUM` to the "Binary-only (-bin) ZIP Checksum" for that version from https://gradle.org/release-checksums/.

Then run:

```
for dir in . \
           benchmark-overhead \
           examples/distro \
           examples/extension \
           gradle-plugins \
           smoke-tests/fake-backend \
           smoke-tests/grpc \
           smoke-tests/matrix \
           smoke-tests/play \
           smoke-tests/springboot
do
  (cd $dir && ./gradlew wrapper --gradle-version $GRADLE_VERSION \
                  --gradle-distribution-sha256-sum=$GRADLE_VERSION_CHECKSUM)
done
```