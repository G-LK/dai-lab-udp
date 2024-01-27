# Setup
Nous utilisons Gradle et pas Maven pour nos 2 applications. Pour builder avant de faire le docker build, il suffit de lancer `./gradlew uberJar` dans `docker/image-auditor` et `docker/image-musician`. 

Notes:
1. Nous utilisons Jackson plutôt que Gson
1. Nos dockerfile sont adaptés à la destination finale du `jar` sorti par Gradle et fonctionnels.
1. Le test `validate.sh` passe !
