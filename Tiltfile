# Build
custom_build(
  ref = 'dispatcher-service',
  command = './gradlew bootBuildImage --imageName $EXPECTED_REF',
  deps = ['build.gradle', './bin/main'],
  live_update = [
    sync('./bin/main', '/workspace/BOOT-INF/classes')
  ]
)

# Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# Manage
k8s_resource('dispatcher-service', port_forwards=['9003'])