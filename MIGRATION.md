# Migration to Spring Cloud Pipelines

- Added Maven wrapper

```bash
$ mvn -N io.takari:maven:wrapper
$ git add .
$ git add -f .mvn
$ git commit -m "Added maven wrapper" 
```

- Updated `manifest.yml`
- Added `sc-pipelines.yml`
    - provided which module is the main one
    - added database as a required service
- Created cloud foundry spaces

```bash
$ cf login -o ... -a ...
$ cf create-space sc-pipelines-test-dddbyexamples-factory
$ cf create-space sc-pipelines-stage-dddbyexamples-factory
$ cf create-space sc-pipelines-prod
```

- Added `<distributionManagement>` section
- Added contract tests (`shortages-prediction-adapters/src/test/groovy/io/dddbyexamples/factory/shortages/prediction/monitoring/persistence/ShortagesDaoTest.groovy`)
- Added stub jar generation in `app-monolith` (in the output stubs jar each module has its own folder)
- Added `<profiles>` for all types of tests for all projects
  - that way we can control the whole project from root 
- Added base class for rollback tests (for `shortages-prediction-adapters`)
- Configured rollback tests via `sc-contract` plugin under `apicompatibility` profile (for `shortages-prediction-adapters`)
- Added `smoke` tests (just pining health initially) (initially only for `shortages-prediction-adapters` but could be added for more)
- Added `e2e` tests (just pining health initially) (initially only for `shortages-prediction-adapters` but could be added for more)



